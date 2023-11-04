from django.db import models
from django.contrib.auth.models import AbstractUser
# Create your models here.

class CustomUser(AbstractUser):
    """Model definition for CustomUser."""

    # TODO: Define fields here

    class Meta:
        """Meta definition for CustomUser."""

        verbose_name = 'CustomUser'
        verbose_name_plural = 'CustomUsers'

    def __str__(self):
        """Unicode representation of CustomUser."""
        return self.username
