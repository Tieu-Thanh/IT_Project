import threading
import concurrent.futures

from flask import Blueprint
from flask_restful import Resource, Api, reqparse
from firebase_admin import firestore, auth

from api.models.Object import Object

object_bp = Blueprint('object_bp', __name__)
api = Api(object_bp)
db = firestore.client()


class ObjectResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('user_id', type=str, required=True)
        self.parser.add_argument('obj_id', type=str)
        self.parser.add_argument('obj_name', type=str)

    def post(self):
        args = self.parser.parse_args()
        user_id = args['user_id']
        obj_id = args['obj_id']
        obj_name = args['obj_name']

        if not obj_name and not obj_id:
            return {'message': 'Missing parameters'}, 400

        obj = Object(user_id=user_id, obj_id=obj_id, obj_name=obj_name)
        obj.save_to_firestore()

        # Use a ThreadPoolExecutor with a limited number of threads
        with concurrent.futures.ThreadPoolExecutor(max_workers=5) as executor:
            # Submit the crawl_images method as a task to the thread pool
            executor.submit(obj.crawl_images)

        return {'message': 'Object created, images are being crawled asynchronously'}, 201

    def get(self):
        args = self.parser.parse_args()
        user_id = args['user_id']

        object_list = Object.get_objects_by_user_id(user_id)

        return {'objects_list': object_list}, 201

    def delete(self):
        args = self.parser.parse_args()
        # user_id = args['user_id']
        obj_id = args['obj_id']

        if obj_id:
            obj_ref = db.collection('objects').document(obj_id).delete()
            return {'message': 'Object deleted successfully'}, 201

        else:
            return {'message': 'No object found'}, 404


class ObjectDetailResource(Resource):
    def __init__(self):
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('roi_values', type=list)

    def get(self, obj_id):
        # Create an instance of Object
        object_instance = Object(obj_id, None, None)

        # Get details of a specific object
        object_data = object_instance.get_object_details()

        if not object_data:
            return {'message': 'Object does not exist'}, 404

        return {'object_data': object_instance.to_dict()}, 201

    def delete(self, obj_id):
        object_ref = db.collection('objects').document(obj_id)
        if not object_ref.exists():
            return {'message': 'No object found'}, 404

        object_ref.delete()
        return {'message': 'Object deleted successfully'}, 201
    #
    # def put(self, obj_id):
    #     # Update roi_values for a specific object
    #     args = self.parser.parse_args()
    #     roi_values = args.get('roi_values', [])
    #
    #     # Create an instance of Object
    #     object_instance = Object(obj_id, None, None)
    #
    #     # Update roi_values
    #     object_instance.update_roi_values(roi_values)
    #
    #     return {'message': 'Roi values updated successfully'}, 20


api.add_resource(ObjectResource, '/', 'objects')
api.add_resource(ObjectDetailResource, '/<string:obj_id>', endpoint='object-detail')
