from .Image import Image
# from ...Crawler import Crawler
from firebase_admin import firestore
from Crawler import Crawler

db = firestore.client()


class Object:
    def __init__(self, obj_id, user_id, obj_name):
        self.obj_id = obj_id
        self.user_id = user_id
        self.obj_name = obj_name

    def to_dict(self):
        return {
            'obj_id': self.obj_id,
            'user_id': self.user_id,
            'obj_name': self.obj_name,
            'images': self.get_images()
        }

    def save_to_firestore(self):
        db.collection('objects').document(self.obj_id).set(self.to_dict())

    def get_images(self):
        # Fetch 'images' documents
        images_ref = db.collection('objects').document(self.obj_id).collection('images')
        images_data = [image.to_dict() for image in images_ref.stream()]
        # self.images = images_data

        return images_data

    def crawl_images(self):
        crawler = Crawler()
        images_data = crawler.crawl(self.obj_name.replace(" ", "+"))
        for image_data in images_data:
            img = image_data.to_dict()
            image_id = img['image_id']
            url = img['url']
            roi_values = img.get('roi_values', [])
            image = Image(image_id, url, roi_values)
            # self.add_image(image_id)
            db.collection('objects').document(self.obj_id).collection('images').document(image_id).set(image.to_dict())

    @staticmethod
    def get_objects_by_user_id(user_id):
        objects_ref = db.collection('objects').where('user_id', '==', user_id)
        objects_data = [obj.to_dict() for obj in objects_ref.stream()]
        return objects_data

    def get_object_details(self):
        object_ref = db.collection('objects').document(self.obj_id)
        object_doc = object_ref.get()

        if not object_doc.exists:
            return None  # or raise an exception, depending on your requirements

        object_data = object_doc.to_dict()
        images = self.get_images()

        # Include images in the response
        object_data['images'] = images

        return object_data
