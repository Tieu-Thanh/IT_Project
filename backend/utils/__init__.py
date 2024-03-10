from celery import Celery
from config import Config

celery = Celery('IT_Project')

# Use the configuration from the Config class directly
celery.config_from_object(Config)
