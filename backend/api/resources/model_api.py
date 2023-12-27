import threading

from flask import Blueprint
from flask_restful import Resource, Api, reqparse
from firebase_admin import firestore

from api.models.Model import Model

model_bp = Blueprint('model_bp', __name__)
api = Api(model_bp)
db = firestore.client()


class ModelResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('user_id', type=str, required=True)
        self.parser.add_argument('model_id', type=str, required=True)
        self.parser.add_argument('model_name', type=str, required=True)

    def post(self):
        args = self.parser.parse_args()
        user_id = args['user_id']
        model_id = args['model_id']
        model_name = args['model_name']

        model = Model(user_id=user_id, model_id=model_id, model_name=model_name)
        db.collection('models').document(model_id).set(model.to_dict())

        thread = threading.Thread(target=model.crawl_images)
        thread.start()

        return {'message': 'Model created, images are being crawled asynchronously'}, 201


api.add_resource(ModelResource, '/models', 'model')
