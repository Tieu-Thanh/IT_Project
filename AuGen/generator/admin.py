from django.contrib import admin
from .models import Generator, Image
# Register your models here.

admin.site.register(Generator, Image)