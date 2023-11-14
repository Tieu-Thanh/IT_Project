from rest_framework import serializers
from .models import Generator, Image

class GeneratorSerializer(serializers.ModelSerializer):

    class Meta:
        model = Generator
        fields = ('pk', 'label',)

    