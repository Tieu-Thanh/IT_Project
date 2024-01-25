class User:
    def __init__(self, uid, display_name=None, phone_number=None, photo_url=None):
        self.uid = uid
        self.display_name = display_name
        self.phone_number = phone_number
        self.photo_url = photo_url

    def to_dict(self):
        return {
            'uid': self.uid,
            'display_name': self.display_name,
            'phone_number': self.phone_number,
            'photo_url': self.photo_url
        }