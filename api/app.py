from flask import Flask
from config import app_config

app = Flask(__name__)
app.config.from_object(app_config)


# Add other application configurations or extensions here

@app.route('/')
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(debug=True)
