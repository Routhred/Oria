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
from authentification.tokens import generateToken
from django.http import JsonResponse
from http import HTTPStatus
from authentification.utils import *
from authentification.models import *



def signup(request):
    print(f"DEBUG: signup {request.method}")
    if request.method == "POST":
        print(f"DEBUG: body {request.POST}")
        username = request.POST.get('username', '')
        firstname = request.POST.get('firstname', '')
        lastname = request.POST.get('lastname', '')
        email = request.POST.get('email', '')
        password = request.POST.get('password', '')
        confirmpwd = request.POST.get('confirmpwd', '')

        # Verify intput
        if not verificationField(Field.USERNAME, username) or User.objects.filter(username=username).exists():
            return JsonResponse({'ERROR_CODE': '201', 'TOKEN': ''}, status=HTTPStatus.BAD_REQUEST)
        if not verificationField(Field.USERNAME, firstname):
            return JsonResponse({'ERROR_CODE': '203', 'TOKEN': ''}, status=HTTPStatus.BAD_REQUEST)
        if not verificationField(Field.USERNAME, lastname):
            return JsonResponse({'ERROR_CODE': '203', 'TOKEN': ''}, status=HTTPStatus.BAD_REQUEST)
        if not verificationField(Field.EMAIL, email):
            return JsonResponse({'ERROR_CODE': '204', 'TOKEN': ''}, status=HTTPStatus.BAD_REQUEST)
        if not verificationField(Field.PASSWORD, password):
            return JsonResponse({'ERROR_CODE': '202', 'TOKEN': ''}, status=HTTPStatus.BAD_REQUEST)
        if not password == confirmpwd:
            return JsonResponse({'ERROR_CODE': '104', 'TOKEN': ''}, status=HTTPStatus.BAD_REQUEST)
        print("Input verified")
        # Creation of the user in the database
        my_user = User.objects.create_user(username, email, password)
        my_user.first_name = firstname
        my_user.last_name = lastname
        my_user.is_active = False
        my_user.save()
        print("User saved")
        # send email when account has been created successfully
        subject = "Welcome to django-application donaldPro"
        message = "Welcome " + my_user.first_name + " " + my_user.last_name + "\n thank for chosing Dprogrammeur website for test login.\n To order login you need to comfirm your email account.\n thanks\n\n\n donald programmeur"

        from_email = settings.EMAIL_HOST_USER
        to_list = [my_user.email]
        send_mail(subject, message, from_email, to_list, fail_silently=False)

        # send the confirmation email
        current_site = get_current_site(request)
        email_subject = "confirm your email for Oria Login!"
        message_confirm = render_to_string("emailConfimation.html", {
            'name': my_user.first_name,
            'domain': current_site.domain,
            'uid': urlsafe_base64_encode(force_bytes(my_user.pk)),
            'token': generateToken.make_token(my_user)
        })

        email = EmailMessage(
            email_subject,
            message_confirm,
            settings.EMAIL_HOST_USER,
            [my_user.email]
        )

        email.fail_silently = False
        email.send()

        print("Email sent")

        token = genToken(username)
        print(f"Token generated: {token}")

        return JsonResponse({'ERROR_CODE': '0', 'TOKEN': token}, status=HTTPStatus.CREATED)
    return JsonResponse({'ERROR_CODE': '400', 'TOKEN': ''}, status=HTTPStatus.BAD_REQUEST)


def signin(request):
    if request.method == "POST":
        username = request.POST['username']
        password = request.POST['password']

        # Get all trips for the user
        trips_id = []
        trips = Trip.objects.filter()
        for trip in trips:
            if username in trip.users:
                trips_id.append(str(trip.id))

        if not verificationField(Field.PASSWORD, password):
            return JsonResponse({'ERROR_CODE': '202', 'TRIPS': []}, status=HTTPStatus.UNAUTHORIZED)

        if not verificationField(Field.USERNAME, username):
            return JsonResponse({'ERROR_CODE': '201', 'TRIPS': []}, status=HTTPStatus.UNAUTHORIZED)

        user = authenticate(username=username, password=password)
        my_user = User.objects.get(username=username)

        if user is not None:
            login(request, user)
            return JsonResponse({'ERROR_CODE': '0', 'TRIPS': trips_id}, status=HTTPStatus.OK)
        elif not my_user.is_active:
            return JsonResponse({'ERROR_CODE': '102', 'TRIPS': []}, status=HTTPStatus.UNAUTHORIZED)
        else:
            return JsonResponse({'ERROR_CODE': '103', 'TRIPS': []}, status=HTTPStatus.UNAUTHORIZED)

    return JsonResponse({'ERROR_CODE': '400', 'TRIPS': []}, status=HTTPStatus.BAD_REQUEST)


def signout(request):
    logout(request)
    JsonResponse({'ERROR_CODE': '400'}, status=HTTPStatus.BAD_REQUEST)
    return redirect('home')