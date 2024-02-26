from flask import Blueprint
from flask_restful import Api

from blueprints.api.resources.model_resource import ModelResource, ModelDetailResource, ModelImages, ModelVideoResource

model_api_bp = Blueprint('model_api', __name__)
api = Api(model_api_bp)

# Register resources with the API
api.add_resource(ModelResource, '/', endpoint='models')
api.add_resource(ModelDetailResource, '/<string:model_id>', endpoint='model_detail')
api.add_resource(ModelImages, '/<string:model_id>/images', endpoint='model_images')
api.add_resource(ModelVideoResource, '/<string:model_id>/videos', endpoint='model_videos')
