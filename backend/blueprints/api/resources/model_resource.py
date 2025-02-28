import io
import os
import uuid
from pathlib import Path
from types import SimpleNamespace
import shutil

from blueprints.api.models.Crawler import Crawler
from blueprints.api.models.Model import Model
from blueprints.detection.yolo import Model_YOLO
from firebase_admin import storage, messaging
from flask import request
from flask_restful import Resource, reqparse
from firebase_admin import firestore


def removeFile(file_path):
    try:
        shutil.rmtree(f"{file_path}")
        print("Clear image folder")
    except Exception as e:
        print(f"Error: {e}")
def download_file_from_storage(url, local_path):
    try:
        blob_name = url.replace("https://storage.googleapis.com/chuyen-de-nghien-cuu.appspot.com/", "")
        bucket = storage.bucket()

        # create a blob obj
        blob = bucket.blob(blob_name)

        # download image
        blob.download_to_filename(local_path)
        print(f"Successfully downloaded {os.path.basename(local_path)}")
    except Exception as e:
        print(str(e))

def send_notification_to_device(token, title, body,url="",count_result=""):
    # See documentation on defining a message payload at
    # https://firebase.google.com/docs/cloud-messaging/admin/send-messages
    try:

        message = messaging.Message(
            notification=messaging.Notification(
                title=title,
                body=body,
            ),
            token=token,
            data={
                'url': url,
                'count_result': count_result
            }
        )
        response = messaging.send(message)
        return response

    except Exception as e:
        return {"error": e}, 500

class ModelResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('model_id', type=str)
        self.parser.add_argument('user_id', type=str)
        self.parser.add_argument('model_name', type=str)
        self.parser.add_argument('classes', type=str, action='append')
        self.parser.add_argument('crawl_number', type=int)
        self.parser.add_argument('token', type=str)
    def checkExist(self,model_id):
        db = firestore.client()
        model_ref = db.collection('models').document(model_id)
        model_doc = model_ref.get()
        return model_doc.exists
    def post(self):
        try:
            args = self.parser.parse_args()
            model_id = args['model_id']
            user_id = args['user_id']
            model_name = args['model_name']
            classes = args['classes']
            crawl_number = args['crawl_number']
            token = args['token']

            if self.checkExist(model_id):
                return {'message': 'Model already exists'}, 400
            # Create Storage folder
            bucket = storage.bucket()

            blob = bucket.blob(f"{user_id}/{model_id}/.ignore")
            blob.upload_from_string('', content_type='text/plain')

            # Crawl images
            BASE_DIR = os.getenv("BASE_DIR", Path(__file__).resolve().parent.parent.parent)
            img_folder = os.path.join(BASE_DIR, "detection", "Images", f"{user_id}", f"{model_id}")

            # creating a Model instance
            model = Model(
                model_id=model_id,
                user_id=user_id,
                model_name=model_name,
                classes=classes,
                crawl_number=crawl_number,
                status=1,
                img_urls=[]  # This contains URLs from Firebase
            )
            model.save_to_db()
            crawler = Crawler()
            images = crawler.crawl(classes, crawl_number)
            crawler.download_images(images, download_folder=img_folder)
            title = f"{model.status}.{model_id} created"
            body = "Your model data has been created successfully, await to train"
            print(send_notification_to_device(token, title, body))
            return {
                'message': 'Model created successfully',
                'model': model.to_dict()
            }, 201
        except Exception as e:
            return {'message': str(e)}, 500
        # Respond with success message and any relevant data

    def get(self):
        try:
            user_id = request.args.get('user_id')
            models = Model.get_models_by_user_id(user_id)
            return {'models': models}, 201
        except Exception as e:
            print(e)
            return {'message': str(e)}, 500



class ModelDetailResource(Resource):
    def delete(self, model_id):
        try:
            model = Model.get_models_by_id(model_id)
            if model:
                model.delete_from_db()
                return {'message': 'Model deleted successfully'}, 201
            return {'message': 'Model not found'}, 404

        except Exception as e:
            return {'message': str(e)}, 500

    def get(self, model_id):
        try:
            model = Model.get_model_detail(model_id)
            if model:
                return {'model': model.to_dict()}, 201
            return {'message': 'Model not found'}, 404

        except Exception as e:
            return {'message': str(e)}, 500


class ModelImages(Resource):
    def post(self, model_id):
        try:
            images = request.files.getlist('images')
            # Retrieve the model from Firestore
            model = Model.get_model_detail(model_id)
            if model is None:
                return {"message": "Model not found"}, 404

            # Upload images to Firebase Storage and get URLs
            img_urls = Model.save_images_to_url(model.user_id, model.model_id, images)

            # Update the model's img_urls attribute
            model.img_urls += img_urls  # Append new URLs to existing list
            model.save_to_db()

            return {"message": "Images uploaded and model updated successfully",
                    "img_urls": img_urls,
                    "model": model.to_dict()}, 200
        except Exception as e:
            return {"message": str(e)}, 500


class ModelVideoResource(Resource):

    def post(self, model_id):
        url = request.form.get('url')
        video_file = request.files.get('video')
        token = request.form.get('token')

        if not url and not video_file:
            return {"message": "No url provided or video provided"}, 400

        try:
            model = Model.get_model_detail(model_id)  # Get model data

            # upload video if video is provided and return url
            data = model.to_dict()
            print(video_file)
            if video_file:
                url = self.uploadVideo("videos", video_file, data)

            # Save video doc to corresponding model
            video_id = str(uuid.uuid4())
            video_doc = model.get_video_ref(video_id)
            video_doc.set({
                'video_id': video_id,
                'url': url}
            )

            video_data = video_doc.get().to_dict()
            response = self.detect_video(token=token, model_data=model.to_dict(), video_data=video_data)

            return {"message": "Video saved successfully",
                    "video": video_data,
                    "result": response}, 201
        except Exception as e:
            return {"message": str(e)}, 500

    def detect_video(self, token, model_data, video_data):
        # resources/Images/{user_id}/{model_id}
        try:
            BASE_DIR = os.getenv("BASE_DIR", Path(__file__).resolve().parent.parent.parent)
            script_dir = os.path.join(BASE_DIR, "detection")
            model_folder = os.path.join(script_dir,
                                        "Images",
                                        f"{model_data['user_id']}",
                                        f"{model_data['model_id']}")
            model_file = os.path.join(model_folder+"_model", "train", "weights", "best.pt")
            # Create path
            if not os.path.exists(model_file):
                # os.makedirs(model_file, exist_ok=True)
                return {"error": "Model file does not exists"}, 404

            yolo = Model_YOLO(model_file)
            video_path = os.path.join(model_folder+"_model", "detection_result")
            if not os.path.exists(video_path):
                os.makedirs(video_path, exist_ok=True)

            filename_with_extension = video_data['url'].split("/")[-1]
            filename_without_extension, _ = os.path.splitext(filename_with_extension)
            filename = filename_without_extension + '.mp4'
            video_name = os.path.join(video_path, filename)
            download_file_from_storage(video_data['url'], video_name)

            output_video = yolo.count_bytetrack(input_video_path=video_name)

            video_file = self.create_video_file_mock(output_video)

            output_url = self.uploadVideo("detection_result", video_file, model_data)
            removeFile(video_path)

            print("Video result uploaded successfully")

            tittle = f"{4}.{model_data['model_id']} status"
            body = "Video detected successfully"

            print(send_notification_to_device(token,tittle,body,url=output_url,count_result=str(yolo.video_count)))
            return {"message": "Video detected successfully"}
        except Exception as e:
            return {"detect error": e}, 500
    def uploadVideo(self, folder_type, video_file, data):
        try:
            bucket = storage.bucket()

            blob = bucket.blob(f"{data['user_id']}/{data['model_id']}/{folder_type}/" + video_file.filename)
            blob.upload_from_file(video_file.stream, content_type=video_file.content_type)
            url = blob.public_url
            return url
        except Exception as e:
            return {"upload error": e}, 500

    def create_video_file_mock(self, video_path):
        try:
            with open(video_path, 'rb') as file:
                file_content = file.read()

            video_stream = io.BytesIO(file_content)
            video_file_mock = SimpleNamespace(
                stream=video_stream,
                filename=os.path.basename(video_path),
                content_type='video/mp4'  # Cập nhật giá trị này để phản ánh loại nội dung của MP4
            )
            return video_file_mock
        except Exception as e:
            return {"error": str(e)}, 500
    def get(self, model_id):
        """
        Retrieve videos for a given model_id by utilizing the Model class method.
        """
        try:
            model = Model.get_model_detail(model_id)
            if model is None:
                return {"message": "Model not found"}, 404

            videos = model.get_videos()
            if videos:
                return {"model": model.to_dict(), "videos": videos}, 201
            else:
                return {"message": "No videos found for the specified model."}, 404
        except Exception as e:
            return {"message": str(e)}, 500