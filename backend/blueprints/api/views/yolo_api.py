from flask import Blueprint
from flask_restful import Api
from blueprints.api.resources.yolo_resource import YoloResource

yolo_api_bp = Blueprint('yolo_api', __name__)
api = Api(yolo_api_bp)

api.add_resource(YoloResource, '/', endpoint='yolo')
