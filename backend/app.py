from flask import Flask
from config import app_config
from blueprints.api import api_blueprint

app = Flask(__name__)
app.config.from_object(app_config)

# Register the API blueprint
app.register_blueprint(api_blueprint, url_prefix='/api')


@app.route('/')
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
