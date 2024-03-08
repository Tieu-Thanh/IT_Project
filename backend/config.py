# Configuration settings for your Flask app
class Config:
    CELERY_BROKER_URL = 'redis://localhost:6379/0'
    CELERY_RESULT_BACKEND = 'redis://localhost:6379/0'
    FIREBASE_CREDENTIALS = 'key.json'
    FIREBASE_STORAGE_BUCKET = 'chuyen-de-nghien-cuu.appspot.com'
