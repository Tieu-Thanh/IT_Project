from firebase_admin import firestore, storage, messaging
from flask import request
import os
import uuid
from flask_restful import Resource, reqparse
from concurrent.futures import ThreadPoolExecutor, as_completed
from blueprints.api.models.Model import Model
from blueprints.api.models.Crawler import Crawler
import json

class ModelResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('model_id', type=str)
        self.parser.add_argument('user_id', type=str)
        self.parser.add_argument('model_name', type=str)
        self.parser.add_argument('classes', type=str, action='append')
        self.parser.add_argument('crawl_number', type=int)
        self.parser.add_argument('accuracy', type=float)
        self.parser.add_argument('status', type=str, default='newly created')
        self.parser.add_argument('token', type=str)
    def post(self):
        args = self.parser.parse_args()
        model_id = args['model_id']
        user_id = args['user_id']
        model_name = args['model_name']
        classes = args['classes']
        crawl_number = args['crawl_number']
        accuracy = args['accuracy']
        status = args['status']
        token = args['token']
        # Create Storage folder
        bucket = storage.bucket()
        size = self.folder_size(bucket, f"{user_id}") - 1
        model_id = model_id + str(size)
        blob = bucket.blob(f"{user_id}/{model_id}/.ignore")
        blob.upload_from_string('', content_type='text/plain')

        # Crawl images
        script_dir = os.path.dirname(os.path.abspath(__file__))  # Dynamically get the directory of the current script
        img_folder = os.path.join(script_dir, "Images", f"{user_id}", f"{model_id}")

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
            status=status,
            accuracy=accuracy,
            img_urls=[]  # This contains URLs from Firebase
        )
        model.save_to_db()
        title = f"Model {model_id} created"
        body = "Your model data has been created successfully, await to train"

        print(self.send_notification_to_device(token,title, body))
        return {'message': 'Model created successfully',
                'model': model.to_dict()}, 201
        # Respond with success message and any relevant data
    def folder_size(self,bucket, folder_path)->int:
        blobs = bucket.list_blobs(prefix= folder_path)
        size = 0
        for _ in blobs:
            size += 1
        return size
    def get(self):
        try:
            # user_id = request.get_json()['user_id']

            user_id = request.args.get('user_id')
            models = Model.get_models_by_user_id(user_id)
            return {'models': models}, 201
        except Exception as e:
            return {'message': str(e)}, 500

    def send_notification_to_device(self, token, title, body):
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
    # def __init__(self):
    #     self.parser = reqparse.RequestParser()
    #     # self.parser.add_argument('video_id', type=str, required=True)
    #     self.parser.add_argument('url', type=str, store_missing=False)

    def post(self, model_id):
        # args = self.parser.parse_args()
        # video_id = args['video_id']
        url = request.form.get('url')
        video_file = request.files.get('video')

        if not url and not video_file:
            return {"message": "No url provided or video provided"}, 400

        try:
            db = firestore.client()
            model_ref = db.collection('models').document(model_id)
            model_doc = model_ref.get()
            if not model_doc.exists:
                return {"message": "Model not found"}, 404

            video_id = str(uuid.uuid4())

            # upload video if video is provided
            data = model_doc.to_dict()
            if video_file:
                bucket = storage.bucket()
                blob = bucket.blob(f"{data['user_id']}/{data['model_id']}/videos/"+video_file.filename)
                blob.upload_from_file(video_file.stream, content_type=video_file.content_type)

                url = blob.public_url

            video_doc = model_ref.collection('videos').document(video_id)
            video_doc.set({
                'video_id': video_id,
                'url': url}
            )

            video_data = video_doc.get().to_dict()
            return {"message": "Video saved successfully",
                    "video": video_data}, 201
        except Exception as e:
            return {"message": str(e)}, 500
