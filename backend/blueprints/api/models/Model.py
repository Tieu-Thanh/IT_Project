from datetime import datetime
from firebase_admin import firestore, storage


class Model:
    def __init__(self, model_id, user_id, model_name, classes, img_urls, **kwargs):
        self.model_id = model_id
        self.user_id = user_id
        self.model_name = model_name
        self.classes = classes
        self.img_urls = img_urls
        self.status = kwargs.get('status', 0)
        self.accuracy = kwargs.get('accuracy', 0.0)
        self.crawl_number = kwargs.get('crawl_number', 10)  # number of images that to be crawled
        self.created_at = kwargs.get("created_at", datetime.now().strftime("%d/%m/%Y, %H:%M:%S"))

    def to_dict(self):
        return {
            'model_id': self.model_id,
            'user_id': self.user_id,
            'model_name': self.model_name,
            'classes': self.classes,
            'img_urls': self.img_urls,
            'status': self.status,
            'accuracy': self.accuracy,
            'crawl_number': self.crawl_number,
            'created_at': self.created_at,
        }

    @classmethod
    def from_dict(cls, data):
        return cls(
            model_id=data['model_id'],
            user_id=data['user_id'],
            model_name=data['model_name'],
            classes=data['classes'],
            img_urls=data['img_urls'],
            status=data['status'],
            accuracy=data['accuracy'],
            crawl_number=data['crawl_number'],
            created_at=data['created_at'],
        )

    def save_to_db(self):
        db = firestore.client()
        db.collection('models').document(self.model_id).set(self.to_dict())

    def delete_from_db(self):
        db = firestore.client()
        db.collection('models').document(self.model_id).delete()

    def update_status(self, new_status):
        """Update the model's status in Firestore."""
        db = firestore.client()
        self.status = new_status
        model_ref = db.collection('models').document(self.model_id)
        model_ref.update({'status': new_status})

    @staticmethod
    def save_images_to_url(user_id, model_id, images):
        img_urls = []
        for image in images:
            # Upload image to Firebase Storage
            bucket = storage.bucket()
            blob = bucket.blob(f"{user_id}/{model_id}/images/" + image.filename)

            # Create BlobMetadata object and set content type based on filename
            blob.metadata = {'content_type': image.content_type}  # Set type

            # Upload the file to Firebase Storage
            blob.upload_from_string(image.read(), content_type=image.content_type)

            image_url = blob.public_url

            img_urls.append(image_url)

        return img_urls

    @staticmethod
    def get_models_by_user_id(user_id):
        db = firestore.client()
        model_ref = db.collection('models').where('user_id', '==', user_id)
        model_data = [model.to_dict() for model in model_ref.stream()]
        return model_data

    @staticmethod
    def get_model_detail(model_id):
        db = firestore.client()
        model_doc = db.collection('models').document(model_id).get()
        if not model_doc.exists:
            return None

        model_data = model_doc.to_dict()
        return Model.from_dict(model_data)

    def get_video_ref(self, video_id):
        db = firestore.client()
        video_ref = db.collection('models').document(self.model_id).collection('videos').document(video_id)
        return video_ref

    def get_videos(self):
        """
        Retrieve all video documents from the 'videos' sub-collection for this model instance.
        """
        db = firestore.client()
        videos_ref = db.collection('models').document(self.model_id).collection('videos')
        videos_query = videos_ref.get()

        videos = []
        for video in videos_query:
            video_data = video.to_dict()
            videos.append({
                'video_id': video_data.get('video_id'),
                'url': video_data.get('url')
            })

        return videos