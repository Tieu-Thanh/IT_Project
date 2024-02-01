class Image:
    def __init__(self, image_id, url, query):
        self.image_id = image_id
        self.url = url
        # self.roi_values = roi_values
        self.query = query

    def to_dict(self):
        return {
            'image_id': self.image_id,
            'url': self.url,
            # 'roi_values': self.roi_values,
            'query': self.query
        }
