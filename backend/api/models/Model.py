from .Image import Image
# from ...Crawler import Crawler
from firebase_admin import firestore
from Crawler import Crawler

db = firestore.client()


class Model:
    def __init__(self, model_id, user_id, model_name):
        self.model_id = model_id
        self.user_id = user_id
        self.model_name = model_name
        self.images = []  # Store image IDs for efficient retrieval

    def add_image(self, image_id):
        self.images.append(image_id)

    def to_dict(self):
        return {
            'model_id': self.model_id,
            'user_id': self.user_id,
            'model_name': self.model_name,
            'images': self.images
        }

    def crawl_images(self):
        crawler = Crawler()
        images_data = crawler.crawl(self.model_name.replace(" ", "+"))
        for image_data in images_data:
            img = image_data.to_dict()
            image_id = img['image_id']
            url = img['url']
            roi_values = img.get('roi_values', [])
            image = Image(image_id, url, roi_values)
            self.add_image(image_id)
            db.collection('models').document(self.model_id).collection('images').document(image_id).set(image.to_dict())
