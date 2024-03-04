import os

from flask_restful import Resource, reqparse
from firebase_admin import storage
import uuid

bucket = storage.bucket()  # Access the Firebase Storage bucket


def download_image_from_storage(url, local_path):
    try:
        blob_name = url.replace("https://storage.googleapis.com/chuyen-de-nghien-cuu.appspot.com/", "")
        # bucket = storage.bucket()

        # create a blob obj
        blob = bucket.blob(blob_name)

        # download image
        blob.download_to_filename(local_path)
        print(f"Successfully downloaded {os.path.basename(local_path)}")
    except Exception as e:
        print(str(e))


class ImageUpload(Resource):
    def post(self):
        pass
        # try:
        #
        #
        # except Exception as e:
        #     return {'error': str(e)}, 500

    def get(self):
        # Retrieve public URLs of all images in the bucket (modify as needed)
        blobs = bucket.list_blobs()
        urls = [blob.public_url for blob in blobs]
        return {'images': urls}
