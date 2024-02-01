# List Images for a Model:
# Endpoint: /api/model/<obj_id>/images
# Method: GET
# Description: Retrieve a list of images associated with a specific model.
from email import parser

# Get Details of a Specific Image:
# Endpoint: /api/model/<obj_id>/images/<image_id>
# Method: GET
# Description: Retrieve details of a specific image within a model.

# Delete an Image from a Model:
# Endpoint: /api/model/<obj_id>/images/<image_id>
# Method: DELETE
# Description: Delete a specific image from a model.

from flask import Blueprint, request, jsonify
from flask_restful import Resource, Api, reqparse
from firebase_admin import firestore
from api.models.Image import Image  # Assuming you have an Image class

image_bp = Blueprint('image_bp', __name__)
api = Api(image_bp)
db = firestore.client()


class ImageListResource(Resource):
    def get(self, model_id):
        # Retrieve a list of images for the given model
        images_ref = db.collection('models').document(model_id).collection('images')
        images_data = [image.to_dict() for image in images_ref.stream()]
        return {'images': images_data}, 200


class ImageDetailResource(Resource):
    def get(self, model_id, image_id):
        # Retrieve details of a specific image within a model
        image_instance = Image.get_image_by_id(model_id, image_id)
        if image_instance is None:
            return {'message': 'Image does not exist'}, 404

        return {'image_data': image_instance.to_dict()}, 200

    def put(self, model_id, image_id):
        # Update the roi_value of a specific image
        data = request.get_json()
        # print(data)

        roi_values = data['roi_values']
        image_instance = Image.get_image_by_id(model_id, image_id)

        if image_instance is None:
            return {'message': 'Image does not exist'}, 404

        # Use the update_roi_values method to update roi_values
        image_instance.update_roi_values(model_id, roi_values)

        # Save the updated image to Firestore
        image_instance.save_to_db(model_id)

        return {'message': 'Image updated successfully', 'updated_image_data': image_instance.to_dict()}, 201

    def delete(self, model_id, image_id):
        # Delete a specific image from a model
        image_instance = Image.get_image_by_id(model_id, image_id)

        if image_instance is None:
            return {'message': 'Image does not exist'}, 404

        # Delete the image from Firestore
        image_instance.delete_from_db(model_id)
        return {'message': 'Image deleted successfully'}, 201


# Define the API endpoints
api.add_resource(ImageListResource, '/<obj_id>/images', endpoint='image_list')
api.add_resource(ImageDetailResource, '/<obj_id>/images/<image_id>', endpoint='image_detail')
