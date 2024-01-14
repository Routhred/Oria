from django.utils.http import urlsafe_base64_decode, urlsafe_base64_encode
from django.utils.encoding import force_bytes, force_text
from django.shortcuts import redirect, render
from django.http import HttpResponse
from django.contrib.auth.models import User
from django.contrib.auth import authenticate, login, logout
from django.contrib import messages
from django.core.mail import send_mail, EmailMessage
from application import settings
from django.contrib.sites.shortcuts import get_current_site
from django.template.loader import render_to_string
from .tokens import generateToken
from django.http import JsonResponse
from http import HTTPStatus
from .utils import *
from .models import *


# Create your views here.


def home(request, *args, **kwargs):
    print(f"Request: {request}")
    return render(request, 'authentification/index.html')


def activate(request, uidb64, token):
    try:
        uid = force_text(urlsafe_base64_decode(uidb64))
        my_user = User.objects.get(pk=uid)
    except(TypeError, ValueError, OverflowError, User.DoesNotExist):
        my_user = None

    if my_user is not None and generateToken.check_token(my_user, token):
        my_user.is_active = True
        my_user.save()
        messages.add_message(request, messages.SUCCESS,
                             "You are account is activated you can login by filling the form below.")
        return render(request, "authentification/signin.html", {'messages': messages.get_messages(request)})
    else:
        messages.add_message(request, messages.ERROR, 'Activation failed please try again')
        return render(request, 'authentification/index.html', {'messages': messages.get_messages(request)})


