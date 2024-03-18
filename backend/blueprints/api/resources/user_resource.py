from firebase_admin import storage, firestore
from flask import request
from flask_restful import Resource, reqparse


class UserResource(Resource):
    def __init__(self):
        self.db = firestore.client()
        self.parser = reqparse.RequestParser()
        self.parser.add_argument('user_id', type=str, required=True)
        # self.parser.add_argument('email', type=str, required=True)

    def post(self):
        args = self.parser.parse_args()
        user_id = args['user_id']
        # email = args['email']

        # create User profile on Firestore
        user_ref = self.db.collection('users').document(user_id)
        user_ref.set({
            'user_id': user_id,
            # 'email': email
        })

        # create User folder on Storage
        bucket = storage.bucket()
        blob = bucket.blob(f"{user_id}/.ignore")
        blob.upload_from_string('', content_type='text/plain')

        return {'message': 'User successfully added',
                'user_data': user_ref.get().to_dict(),
                'storage_folder': blob.public_url
                }, 201


class UserProfileResource(Resource):
    '''
    update user profile details
    '''
    pass