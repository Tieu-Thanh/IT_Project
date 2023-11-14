from .models import CustomUser
from rest_framework import serializers
from django.db import transaction
from dj_rest_auth.registration.serializers import RegisterSerializer

class CustomUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = CustomUser
        fields = ('id', 'username', 'email', 'date_of_birth')


class CustomRegisterSerializer(RegisterSerializer):
    date_of_birth = serializers.DateField(allow_null=True, required=False)
    username = serializers.CharField(required=True)

    # Define transaction.atomic to rollback the save operation in case of error
    @transaction.atomic
    def save(self, request):
        user = super().save(request)
        user.date_of_birth = self.data.get('date_of_birth')
        user.username = self.validated_data.get('username')
        user.save()
        return user
    

class CustomUserDetailsSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = CustomUser
        fields=(
            'pk',
            'username',
            'email',
            'date_of_birth',
        )
        read_only_fields = ('pk', 'email', 'username',)