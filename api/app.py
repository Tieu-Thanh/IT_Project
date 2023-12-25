from flask import Flask
from config import app_config
from resources.auth_api import auth_bp
from resources.user_api import user_bp
app = Flask(__name__)
app.config.from_object(app_config)


# Blueprint registration
app.register_blueprint(auth_bp, url_prefix='/api/auth')
app.register_blueprint(user_bp, url_prefix='/api/user')


@app.route('/')
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(debug=True)
