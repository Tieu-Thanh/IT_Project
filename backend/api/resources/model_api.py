import threading
import concurrent.futures

from flask import Blueprint
from flask_restful import Resource, Api, reqparse
from firebase_admin import firestore, auth

from api.models.Model import Model

model_bp = Blueprint('model_bp', __name__)
api = Api(model_bp)
db = firestore.client()


class ModelResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('user_id', type=str, required=True)
        self.parser.add_argument('model_id', type=str)
        self.parser.add_argument('model_name', type=str)

    def post(self):
        args = self.parser.parse_args()
        user_id = args['user_id']
        model_id = args['model_id']
        model_name = args['model_name']

        if not model_name and not model_id:
            return {'message': 'Missing parameters'}, 400

        model = Model(user_id=user_id, model_id=model_id, model_name=model_name)
        db.collection('models').document(model_id).set(model.to_dict())

        # Use a ThreadPoolExecutor with a limited number of threads
        with concurrent.futures.ThreadPoolExecutor(max_workers=5) as executor:
            # Submit the crawl_images method as a task to the thread pool
            executor.submit(model.crawl_images)

        return {'message': 'Model created, images are being crawled asynchronously'}, 201

    def get(self):
        args = self.parser.parse_args()
        user_id = args['user_id']
        # current_user_id = auth.current_user_id()

        # Query Firestore to get models by user_id
        model_ref = db.collection('models').where('user_id', '==', user_id)
        models = model_ref.get()

        # to list
        models_list = [model.to_dict() for model in models]

        return {'models_list': models_list}, 201

    def delete(self):
        args = self.parser.parse_args()
        user_id = args['user_id']
        model_id = args['model_id']

        if model_id:
            model_ref = db.collection('models').document(model_id).delete()
            return {'message': 'Model deleted successfully'}, 201

        else:
            return {'message': 'No model found'}, 404


class ModelDetailResource(Resource):
    def get(self, model_id):
        model_ref = db.collection('models').document(model_id)
        model_doc = model_ref.get()

        if not model_doc.exists:
            return {'message': 'Model does not exist'}, 404

        model_data = model_doc.to_dict()

        # Fetch images
        model_instance = Model(**model_data)
        images_data = model_instance.get_images()

        # Include images in the response
        model_data['images'] = images_data
        return {'model_data': model_instance.to_dict()}, 201

    def delete(self, model_id):
        model_ref = db.collection('models').document(model_id)
        if not model_ref.exists():
            return {'message': 'No model found'}, 404

        model_ref.delete()
        return {'message': 'Model deleted successfully'}, 201


api.add_resource(ModelResource, '/', 'model')
api.add_resource(ModelDetailResource, '/<string:model_id>', endpoint='model-detail')
