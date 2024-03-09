from firebase_admin import firestore, storage, messaging
from flask import request
import os
import uuid
from flask_restful import Resource, reqparse
from concurrent.futures import ThreadPoolExecutor, as_completed
from blueprints.api.models.Model import Model
from blueprints.api.models.Crawler import Crawler
import json
from blueprints.detection.yolo import Model_YOLO
from pathlib import Path


def send_notification_to_device(token, title, body):
    # See documentation on defining a message payload at
    # https://firebase.google.com/docs/cloud-messaging/admin/send-messages
    message = messaging.Message(
        notification=messaging.Notification(
            title=title,
            body=body,
        ),
        token=token,
    )

    # Send a message to the device corresponding to the provided
    # registration token with the message payload.
    response = messaging.send(message)
    return response


class ModelResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('model_id', type=str)
        self.parser.add_argument('user_id', type=str)
        self.parser.add_argument('model_name', type=str)
        self.parser.add_argument('classes', type=str, action='append')
        self.parser.add_argument('crawl_number', type=int)
        self.parser.add_argument('token', type=str)

    def post(self):
        args = self.parser.parse_args()
        model_id = args['model_id']
        user_id = args['user_id']
        model_name = args['model_name']
        classes = args['classes']
        crawl_number = args['crawl_number']
        token = args['token']

        # Create Storage folder
        bucket = storage.bucket()
        size = self.folder_size(bucket, f"{user_id}") - 1
        model_id = model_id + str(size)
        blob = bucket.blob(f"{user_id}/{model_id}/.ignore")
        blob.upload_from_string('', content_type='text/plain')

        # Crawl images
        BASE_DIR = os.getenv("BASE_DIR", Path(__file__).resolve().parent.parent.parent)
        img_folder = os.path.join(BASE_DIR, "detection", "Images", f"{user_id}", f"{model_id}")

        crawler = Crawler()
        images = crawler.crawl(classes, crawl_number)
        crawler.download_images(images, download_folder=img_folder)

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
        # model.save_to_db()
        title = f"{model.status}.{model_id} created"
        body = "Your model data has been created successfully, await to train"

        print(send_notification_to_device(token, title, body))
        return {
            'message': 'Model created successfully',
            'model': model.to_dict()
        }, 201
        # Respond with success message and any relevant data

    def folder_size(self, bucket, folder_path) -> int:
        blobs = bucket.list_blobs(prefix=folder_path)
        size = 0
        for _ in blobs:
            size += 1
        return size

    def get(self):
        try:
            user_id = request.args.get('user_id')
            models = Model.get_models_by_user_id(user_id)
            return {'models': models}, 201
        except Exception as e:
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


class ModelVideoResource(Resource):

    def post(self, model_id):
        url = request.form.get('url')
        video_file = request.files.get('video')
        token = request.get('token')

        if not url and not video_file:
            return {"message": "No url provided or video provided"}, 400

        try:
            model = Model.get_model_detail(model_id)  # Get model data

            # upload video if video is provided and return url
            data = model.to_dict()
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
        script_dir = os.path.dirname(os.path.abspath(__file__))
        model_folder = os.path.join(script_dir,
                                    "Images",
                                    f"{model_data['user_id']}",
                                    f"{model_data['model_id']}")
        model_file = os.path.join(model_folder, "train", "weights", "best.pt")

        # Create path
        if not os.path.exists(model_file):
            # os.makedirs(model_file, exist_ok=True)
            return {"error": "Model file does not exists"}, 404

        yolo = Model_YOLO(model_file)
        result = yolo.detect(video_data['url'])

        video_result = os.path.join(model_folder, "detection_result", f"{video_data['video_id']}.mp4")
        result.save(video_result)
        self.uploadVideo("detection_result", video_result, video_data)

        response = send_notification_to_device(token,
                                               f"{4}.{model_data['model_id']} status",
                                               "Video detected successfully")
        return response

    def uploadVideo(self, folder_type, video_file, data):
        bucket = storage.bucket()
        blob = bucket.blob(f"{data['user_id']}/{data['model_id']}/{folder_type}/" + video_file.filename)
        blob.upload_from_file(video_file.stream, content_type=video_file.content_type)
        url = blob.public_url
        return url
