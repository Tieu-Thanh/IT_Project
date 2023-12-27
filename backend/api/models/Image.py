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