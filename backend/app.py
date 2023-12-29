

from flask import Flask
from config import app_config
from api.resources.auth_api import auth_bp
from api.resources.user_api import user_bp
from api.resources.model_api import model_bp
from api.resources.image_api import image_bp
import sys
import os

print(sys.path)
print(os.getcwd())
app = Flask(__name__)
app.config.from_object(app_config)

# Blueprint registration
app.register_blueprint(auth_bp, url_prefix='/api/auth')
app.register_blueprint(user_bp, url_prefix='/api/user')
app.register_blueprint(model_bp, url_prefix='/api/model')
app.register_blueprint(image_bp, url_prefix='/api/model')


@app.route('/')
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
