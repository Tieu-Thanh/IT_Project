# resources.py
from flask import request, jsonify, Blueprint
from flask_restful import Resource, Api
from firebase_admin import auth, firestore

# from api.models import User
user_bp = Blueprint('user_bp', __name__)
api = Api(user_bp)


class UserDetailResource(Resource):

    def get(self, user_id):

        # Retrieve user data from Firestore
        user_doc = firestore.client().collection('users').document(user_id).get()
        if not user_doc.exists:
            return {"error": "User not found"}, 404

        user_data = user_doc.to_dict()
        return jsonify(user_data), 201

    def put(self, user_id):
        try:
            data = request.get_json()

            # Update user data in Firestore
            user_ref = firestore.client().collection('users').document(user_id)
            user_ref.set(data, merge=True)

            return {"message": "User data updated successfully"}, 200
        except Exception as e:
            return {"error": str(e)}, 400


api.add_resource(UserDetailResource, '/info/<string:user_id>/', endpoint='user_detail')
