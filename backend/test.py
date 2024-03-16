from firebase_admin import storage, credentials
import firebase_admin
# from google.cloud import storage
# Initialize Firebase Admin SDK with appropriate credentials
# Ensure that you have set up the Firebase Admin SDK with the appropriate service account credentials
# For example:
cred = credentials.Certificate('key.json')
firebase_admin.initialize_app(cred, {'storageBucket': 'chuyen-de-nghien-cuu.appspot.com'})


# # Initialize the Blob object representing the file
blob = storage.bucket().blob('thinh/A/images/aaaa.png')

# Specify the local file path where you want to download the file
local_file_path = 'downloaded_file.png'

# Download the file to the specified local file path
blob.download_to_filename(local_file_path)

print(f"File downloaded to {local_file_path}")
