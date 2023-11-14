from django.db import models
from .managers import CustomUserManager
from django.contrib.auth.models import AbstractUser
# Create your models here.

class CustomUser(AbstractUser):
    # Add your custom fields here
    date_of_birth = models.DateField(null=True, blank=True)

    # Use the custom manager for this model
    objects = CustomUserManager()

    # Specify the username field
    USERNAME_FIELD = 'username'
    # Add any other required fields for the UserManager
    REQUIRED_FIELDS = []  # if email is a required field
