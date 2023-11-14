from django.db import models

# Create your models here.

class Generator(models.Model):
    """Model definition for Post."""

    # TODO: Define fields here

    label = models.CharField(max_length=255)

    def __str__(self):
        """Unicode representation of Post."""
        return self.label


class Image(models.Model):
    """Model definition for Image."""

    gen = models.ForeignKey(Generator, on_delete=models.CASCADE, related_name='images')
    image_url = models.URLField()
    image = models.ImageField(upload_to='images', null=True, blank=True)

    def __str__(self):
        """Unicode representation of Image."""
        pass



