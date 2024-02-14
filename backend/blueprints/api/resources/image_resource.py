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

            # Validate file type (optional)
            if not allowed_file(file.filename):
                return {'error': 'Unsupported file type'}, 400

            # Generate unique filename (optional)
            filename = secure_filename(file.filename)

            # Create a BlobMetadata object (set content type based on filename)
            blob = bucket.blob(filename)
            blob.metadata = {'content_type': file.content_type}

            # Upload the file to Firebase Storage
            blob.upload_from_string(file.read(), content_type=file.content_type)

            # Return success message and image URL
            return {'message': 'Image uploaded successfully',
                    'url': blob.public_url}, 201

        except Exception as e:
            return {'error': str(e)}, 500

    def get(self):
        # Retrieve public URLs of all images in the bucket (modify as needed)
        blobs = bucket.list_blobs()
        urls = [blob.public_url for blob in blobs]
        return {'images': urls}


# Define allowed file types (optional)
def allowed_file(filename):
    ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}
    return '.' in filename and \
        filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


def secure_filename(filename):
    _, ext = os.path.splitext(filename)
    return f'{uuid.uuid4()}{ext}'
