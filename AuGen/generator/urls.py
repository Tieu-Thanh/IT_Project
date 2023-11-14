from django.urls import path
from generator import views
from rest_framework.urlpatterns import format_suffix_patterns

urlpatterns = [
    path("generators/", view=views.GeneratorList.as_view(), name='generator-list'),
    path("generators/<int:pk>", view=views.GeneratorDetail.as_view(), name='generator-detail'),
]

urlpatterns = format_suffix_patterns(urlpatterns)