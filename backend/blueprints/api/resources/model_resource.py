from firebase_admin import firestore
from flask import request
import os
from flask_restful import Resource,reqparse
from concurrent.futures import ThreadPoolExecutor, as_completed
from blueprints.api.models.Model import Model
from blueprints.api.models.Crawler import Crawler


class ModelResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('model_id', type=str)
        self.parser.add_argument('user_id', type=str)
        self.parser.add_argument('model_name', type=str)
        self.parser.add_argument('classes', type=str, action='append')
        self.parser.add_argument('crawl_number', type=int)

    def post(self):
        args = self.parser.parse_args()
        model_id = args['model_id']
        user_id = args['user_id']
        model_name = args['model_name']
        classes = args['classes']
        crawl_number = args['crawl_number']
        # images = request.files.getlist('images')

        # # Define the local directory path for saving crawled images
        # base_path = "D:\\Workspace\\IT_Project\\backend\\blueprints\\detection\\Images"
        # model_path = os.path.join(base_path, user_id, model_name)
        #
        # # Use ThreadPoolExecutor to run tasks in parallel
        # with ThreadPoolExecutor() as executor:
        #     # Task 1: Upload images to Firebase Storage
        #     future_upload = executor.submit(Model.save_images_to_url, user_id, model_name, images)
        #
        #     # Task 2: Crawl images and save them locally
        #     # Assuming modifications to Crawler to accept parameters and return list of images directly
        #     crawler = Crawler()
        #     future_crawl = executor.submit(crawler.crawl, classes, img_num=crawl_number)  # Example parameters
        #     # Ensure download_images is adapted to work with the result of crawl
        #
        #     # Wait for both futures to complete
        #     img_urls = future_upload.result()  # Wait for the upload task to complete
        #     crawled_images = future_crawl.result()  # Wait for the crawl task to complete
        #     # Assuming crawled_images is a list of Image objects or similar
        #     # Now, trigger download of crawled images using the list
        #     crawler.download_images(crawled_images, download_folder=model_path)

        # Crawl images
        crawler = Crawler()
        images = crawler.crawl(classes, crawl_number)
        HOME = os.getcwd()
        img_folder = os.path.join(HOME, "blueprints", "detection", "Images")
        # print(HOME)
        crawler.download_images(images, download_folder=img_folder)

        # creating a Model instance
        model = Model(
            model_id=model_id,
            user_id=user_id,
            model_name=model_name,
            classes=classes,
            crawl_number=crawl_number,
            img_urls=[]  # This contains URLs from Firebase
        )
        model.save_to_db()

        # Respond with success message and any relevant data
        return {'message': 'Model created and images processed', 'model': model.to_dict()}, 201

    def get(self):
        try:
            user_id = request.get_json()['user_id']
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
        img_urls = Model.save_images_to_url(model.user_id, model.model_name, images)

        # Update the model's img_urls attribute
        model.img_urls += img_urls  # Append new URLs to existing list
        model.save_to_db()

        return {"message": "Images uploaded and model updated successfully",
                "img_urls": img_urls,
                "model": model.to_dict()}, 200


class ModelVideoResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('video_id', type=str, required=True)
        self.parser.add_argument('url', type=str, required=True)

    def post(self, model_id):
        args = self.parser.parse_args()
        video_id = args['video_id']
        url = args['url']

        if not url:
            return {"message": "No url provided"}, 400

        try:
            db = firestore.client()
            model_ref = db.collection('models').document(model_id)

            if not model_ref.get().exists:
                return {"message": "Model not found"}, 404

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
