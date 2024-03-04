import os

from flask import Flask
import firebase_admin
from firebase_admin import credentials, firestore, storage

from blueprints.api.views.model_api import model_api_bp
from blueprints.api.views.user_api import user_api_bp
from blueprints.api.views.yolo_api import yolo_api_bp

app = Flask(__name__)

# Initialize Firebase Admin
cred = credentials.Certificate('key.json')
firebase_admin.initialize_app(cred, {
    'storageBucket': 'chuyen-de-nghien-cuu.appspot.com'
})

# Firestore database client
db = firestore.client()

# Storage bucket
bucket = storage.bucket()

# Register blueprint
app.register_blueprint(model_api_bp, url_prefix='/api/models')
app.register_blueprint(user_api_bp, url_prefix='/api/users')
app.register_blueprint(yolo_api_bp, url_prefix='/api/yolo')


@app.route('/')
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
