from flask import Blueprint
from flask_restful import Api
from blueprints.api.resources.user_resource import UserResource

user_api_bp = Blueprint('user_api', __name__)
api = Api(user_api_bp)

api.add_resource(UserResource, '/', endpoint='user')
