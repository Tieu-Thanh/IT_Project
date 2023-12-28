from firebase_admin import firestore

db = firestore.client()


class Image:
    def __init__(self, image_id, url, roi_values):
        self.image_id = image_id
        self.url = url
        self.roi_values = roi_values

    def to_dict(self):
        return {
            'image_id': self.image_id,
            'url': self.url,
            'roi_values': self.roi_values
        }

    @classmethod
    def from_dict(cls, image_dict):
        return cls(
            image_id=image_dict['image_id'],
            url=image_dict['url'],
            roi_values=image_dict['roi_values']
        )

    def save_to_db(self, model_id):
        image_ref = db.collection('models').document(model_id).collection('images').document(self.image_id)
        image_ref.set(self.to_dict())

    def delete_from_db(self, model_id):
        image_ref = db.collection('models').document(model_id).collection('images').document(self.image_id)
        image_ref.delete()

    @classmethod
    def get_image_by_id(cls, model_id, image_id):
        image_doc = db.collection('models').document(model_id).collection('images').document(image_id).get()
        if not image_doc.exists:
            return None
        return cls.from_dict(image_doc.to_dict())

    def update_roi_values(self, model_id, roi_values):
        try:
            # Validate roi_values (optional, depending on your requirements)
            if not isinstance(roi_values, list) or not all(isinstance(val, int) for val in roi_values):
                raise ValueError("Invalid roi_values format")

            # Retrieve current roi_values
            image_doc_ref = db.collection('models').document(model_id).collection('images').document(self.image_id)

            # Update the 'roi_values' array field
            image_doc_ref.update({'roi_values': roi_values})

            self.roi_values = roi_values

            return {'message': 'roi_values updated successfully'}, 200

        except Exception as e:
            return {'error': str(e)}, 400
