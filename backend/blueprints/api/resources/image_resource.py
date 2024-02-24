import os

from flask_restful import Resource, reqparse
from firebase_admin import storage
import uuid

bucket = storage.bucket()  # Access the Firebase Storage bucket


class ImageUpload(Resource):
    def post(self):
        try:
            # Define and parse request arguments
            parser = reqparse.RequestParser()
            parser.add_argument('image', type=reqparse.FileStorage, required=True, location='files')
            args = parser.parse_args()

            # Get the uploaded image file
            file = args['image']

        except Exception as e:
            return {'error': str(e)}, 500

    def get(self):
        # Retrieve public URLs of all images in the bucket (modify as needed)
        blobs = bucket.list_blobs()
        urls = [blob.public_url for blob in blobs]
        return {'images': urls}
