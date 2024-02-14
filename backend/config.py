import os
from firebase_admin import credentials, firestore
import firebase_admin
from decouple import config as env_config


class Config:
    DEBUG = False
    TESTING = False
    CSRF_ENABLED = True
    SECRET_KEY = env_config('SECRET_KEY', default='0ur_$ecret_K3y')

    @staticmethod
    def init_firebase():
        # Initialize Firebase Admin SDK
        key_path = env_config('FIREBASE_KEY_PATH', default='key.json')
        cred = credentials.Certificate(key_path)
        firebase_admin.initialize_app(cred, {'storageBucket': app_config.FIREBASE_STORAGE_BUCKET})
        return firestore.client()

    # Firebase configuration
    FIREBASE_API_KEY = env_config('FIREBASE_API_KEY', default='AIzaSyBLwxPmvV-fDQEBzwHm30WjaOOtOH7vaY8')
    FIREBASE_AUTH_DOMAIN = env_config('FIREBASE_AUTH_DOMAIN', default='chuyen-de-nghien-cuu.firebaseapp.com')
    FIREBASE_PROJECT_ID = env_config('FIREBASE_PROJECT_ID', default='chuyen-de-nghien-cuu')
    FIREBASE_STORAGE_BUCKET = env_config('FIREBASE_STORAGE_BUCKET', default='chuyen-de-nghien-cuu.appspot.com')
    FIREBASE_MESSAGING_SENDER_ID = env_config('FIREBASE_MESSAGING_SENDER_ID', default='220669649977')
    FIREBASE_APP_ID = env_config('FIREBASE_APP_ID', default='1:220669649977:web:38ad854f120cc6b306d9e8')
    FIREBASE_MEASUREMENT_ID = env_config('FIREBASE_MEASUREMENT_ID', default='G-4ZYD9VVTQS')


class DevelopmentConfig(Config):
    DEBUG = True
    # Add development-specific configurations if needed


class TestingConfig(Config):
    TESTING = True
    # Add testing-specific configurations if needed


class ProductionConfig(Config):
    # Add production-specific configurations if needed
    pass


# Determine the current environment and use the appropriate configuration
config_env = os.environ.get('FLASK_ENV', 'development')
if config_env == 'production':
    app_config = ProductionConfig()
elif config_env == 'testing':
    app_config = TestingConfig()
else:
    app_config = DevelopmentConfig()

# Initialize Firebase for the application
app_config.FIRESTORE = app_config.init_firebase()
