from flask import Blueprint
from flask_restful import Api
# Import the resources to register them with the API
from .resources.image_resource import ImageUpload

api_blueprint = Blueprint('api', __name__)
api = Api(api_blueprint)

# Register resources with the API
api.add_resource(ImageUpload, '/image-upload')
