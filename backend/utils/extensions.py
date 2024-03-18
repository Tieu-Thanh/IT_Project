import firebase_admin
from firebase_admin import credentials, firestore, storage

from config import Config

# from config import Config

# Initialize Firebase Admin
cred = credentials.Certificate(Config.FIREBASE_CREDENTIALS)
firebase_app = firebase_admin.initialize_app(cred, {
    'storageBucket': Config.FIREBASE_STORAGE_BUCKET
})

# Firestore database client
db = firestore.client()

# Storage bucket
bucket = storage.bucket()
