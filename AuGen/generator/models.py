from django.db import models
from user.models import CustomUser
# Create your models here.

class Generator(models.Model):
    """Model definition for Post."""

    # TODO: Define fields here
    user = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
    label = models.CharField(max_length=255)

    class Meta:
        """Meta definition for Post."""

        verbose_name = 'Post'
        verbose_name_plural = 'Posts'

    def __str__(self):
        """Unicode representation of Post."""
        return self.label


class Image(models.Model):
    """Model definition for Image."""

    # TODO: Define fields here
    gen = models.ForeignKey(Generator, on_delete=models.CASCADE, related_name='images')
    image_url = models.URLField()
    image = models.ImageField(upload_to='images', null=True, blank=True)

    class Meta:
        """Meta definition for Image."""

        verbose_name = 'Image'
        verbose_name_plural = 'Images'

    def __str__(self):
        """Unicode representation of Image."""
        pass



