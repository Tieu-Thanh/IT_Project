from flask import request
import os
from flask_restful import Resource
from concurrent.futures import ThreadPoolExecutor, as_completed
from blueprints.api.models.Model import Model
from blueprints.api.models.Crawler import Crawler


class ModelResource(Resource):
    # def post(self):
    #     # Extract form data directly from request
    #     model_id = request.form.get('model_id')
    #     user_id = request.form.get('user_id')
    #     model_name = request.form.get('model_name')
    #     classes = request.form.get('classes')
    #     images = request.files.getlist('images')
    #
    #     # Save on Storage and get urls
    #     img_urls = Model.save_images_to_url(user_id, model_name, images)
    #
    #     # Save to firestore
    #     model = Model(
    #         model_id=model_id,
    #         user_id=user_id,
    #         model_name=model_name,
    #         classes=classes,
    #         img_urls=img_urls
    #     )
    #     model.save_to_db()
    #
    #     return {'message': 'Image uploaded successfully', 'url': img_urls}, 201

    def post(self):
        model_id = request.form.get('model_id')
        user_id = request.form.get('user_id')
        model_name = request.form.get('model_name')
        classes = request.form.getlist('classes')  # Assuming 'classes' are sent as a list
        images = request.files.getlist('images')

        # Define the local directory path for saving crawled images
        base_path = "D:\\Workspace\\IT_Project\\backend\\blueprints\\detection\\Images"
        model_path = os.path.join(base_path, user_id, model_name)

        # Use ThreadPoolExecutor to run tasks in parallel
        with ThreadPoolExecutor() as executor:
            # Task 1: Upload images to Firebase Storage
            future_upload = executor.submit(Model.save_images_to_url, user_id, model_name, images)

            # Task 2: Crawl images and save them locally
            # Assuming modifications to Crawler to accept parameters and return list of images directly
            crawler = Crawler()
            future_crawl = executor.submit(crawler.crawl, classes, img_num=5)  # Example parameters
            # Ensure download_images is adapted to work with the result of crawl

            # Wait for both futures to complete
            img_urls = future_upload.result()  # Wait for the upload task to complete
            crawled_images = future_crawl.result()  # Wait for the crawl task to complete
            # Assuming crawled_images is a list of Image objects or similar
            # Now, trigger download of crawled images using the list
            crawler.download_images(crawled_images, download_folder=model_path)

        # Assuming the rest of the logic remains the same for creating a Model instance
        model = Model(
            model_id=model_id,
            user_id=user_id,
            model_name=model_name,
            classes=classes,
            img_urls=img_urls  # This contains URLs from Firebase
        )
        model.save_to_db()

        # Respond with success message and any relevant data
        return {'message': 'Model created and images processed', 'img_urls': img_urls}, 201

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
