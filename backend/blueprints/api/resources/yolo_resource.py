import os
from firebase_admin import storage
from flask import request
from flask_restful import Resource, reqparse

from blueprints.api.models.Model import Model


# example import

from blueprints.detection.yolo import Model_YOLO


# helper functions
def download_image_from_storage(url, local_path):
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


class YoloResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        # json parameters
        self.parser.add_argument('model_id', type=str, required=True)

    def post(self):
        args = self.parser.parse_args()
        # get parameters
        model_id = args['model_id']

        # data-handling logic
        model = Model.get_model_detail(model_id)
        user_id = model.user_id

        # Get path
        # Dynamically get the directory of the current script
        script_dir = os.path.dirname(os.path.abspath(__file__))
        # Construct the img_folder path relative to the script location
        img_folder = os.path.join(script_dir, "Images", f"{user_id}", f"{model_id}")

        # Check dir exists
        if not os.path.exists(img_folder):
            os.makedirs(img_folder, exist_ok=True)

        # Get images of model
        urls = model.img_urls
        if len(urls) != 0:
            for url in urls:
                filename_with_extension = url.split("/")[-1]
                filename_without_extension, _ = os.path.splitext(filename_with_extension)
                filename = filename_without_extension + '.jpg'

                local_path = os.path.join(img_folder, filename)
                download_image_from_storage(url, local_path)

        # Train Model YOLO
        yolo = Model_YOLO()
        yolo.train(model.classes, input_folder=img_folder,extension = '.jpg')

