import os
from pathlib import Path
from firebase_admin import storage
from flask import request
from flask_restful import Resource, reqparse

from blueprints.api.models.Model import Model

from blueprints.detection.yolo import Model_YOLO
from utils.tasks import train_yolo_model
from .model_resource import send_notification_to_device,download_file_from_storage,removeFile

# helper functions



class YoloResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        # json parameters
        self.parser.add_argument('model_id', type=str, required=True)
        self.parser.add_argument('token', type=str, required=True)

    def post(self):
        try:
            args = self.parser.parse_args()
            # get parameters
            model_id = args['model_id']
            token = args['token']
            # data-handling logic
            model = Model.get_model_detail(model_id)
            user_id = model.user_id

            # Get path
            BASE_DIR = os.getenv("BASE_DIR", Path(__file__).resolve().parent.parent.parent)
            img_folder = os.path.join(BASE_DIR, "detection", "Images", f"{user_id}", f"{model_id}")

            # Check dir exists
            if not os.path.exists(img_folder):
                os.makedirs(img_folder, exist_ok=True)
            print(img_folder)
            # Get images of model
            urls = model.img_urls
            if len(urls) != 0:
                for url in urls:
                    filename_with_extension = url.split("/")[-1]
                    filename_without_extension, _ = os.path.splitext(filename_with_extension)
                    filename = filename_without_extension + '.jpg'

                    local_path = os.path.join(img_folder, filename)
                    download_file_from_storage(url, local_path)

            # Train Model YOLO
            model.update_status(2)
            yolo = Model_YOLO()
            model_folder = f"{img_folder}_model"
            yolo.train(model.classes, input_folder=img_folder, extension='.jpg', save_dir=model_folder)
            # removeFile(img_folder)
            # removeFile(img_folder + '_labeled')
            model.update_status(3)
            send_notification_to_device(token, f"{model.status}.{model_id} status", "Model complete!")
            return {"message": "Model train successful",
                    "model_folder": model_folder}, 201
        except Exception as e:
            return {"message": str(e)}, 500



