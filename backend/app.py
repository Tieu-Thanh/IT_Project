import os

from flask import Flask
import firebase_admin
from firebase_admin import credentials, firestore, storage
from utils.extensions import db, bucket
from config import Config
from utils.celery_utils import make_celery
from blueprints.api.views.model_api import model_api_bp
from blueprints.api.views.user_api import user_api_bp
from blueprints.api.views.yolo_api import yolo_api_bp
from celery import Celery


def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)

    celery = make_celery(app)

    # Register blueprint
    app.register_blueprint(model_api_bp, url_prefix='/api/models')
    app.register_blueprint(user_api_bp, url_prefix='/api/users')
    app.register_blueprint(yolo_api_bp, url_prefix='/api/yolo')

    @app.route('/')
    def hello_world():
        return 'Hello, World!'


    return app, celery


app, celery = create_app()

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)

