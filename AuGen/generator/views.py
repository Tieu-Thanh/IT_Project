from django.shortcuts import render
from .models import Generator, Image

from .serializers import GeneratorSerializer

from rest_framework.generics import ListCreateAPIView, RetrieveUpdateDestroyAPIView
from rest_framework.permissions import IsAuthenticated
# Create your views here.


class GeneratorList(ListCreateAPIView):
    permission_classes = [IsAuthenticated]
    queryset = Generator.objects.all()
    serializer_class = GeneratorSerializer

    def perform_create(self, serializer):
        serializer.save()

class GeneratorDetail(RetrieveUpdateDestroyAPIView):
    permission_classes = [IsAuthenticated]
    queryset = Generator.objects.all()
    serializer_class = GeneratorSerializer