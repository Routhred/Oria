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


def createPoint(request):
    if request.method == "POST":
        print(f"DEBUG: body {request.POST}")

        trip_id = request.POST.get('trip_id', '')
        name = request.POST.get('name', '')
        description = request.POST.get('description', '')
        location = request.POST.get('location', '')
        image = request.POST.get('image', '')
        print("location = " + location)

        # TODO Verify information

        point_id = 1
        if Point.objects.count() > 0:
            point_id = Point.objects.last().id + 1

        Point.objects.create(
            id=point_id,
            idTrip=trip_id,
            name=name,
            description=description,
            location=location,
            image=image
        )
        return JsonResponse({'ERROR_CODE': '0', 'POINT_ID': point_id}, status=HTTPStatus.CREATED)
    return JsonResponse({'ERROR_CODE': '400', 'POINT_ID': ''}, status=HTTPStatus.BAD_REQUEST)


def importPoint(request):
    if request.method == "POST":
        point_id = request.POST.get('point_id', '')
        point = Point.objects.get(pk=point_id)

        return JsonResponse(
            {
                'ERROR_CODE': '200',
                'POINT': {
                    "id": point_id,
                    "location": point.location,
                    "name": point.name,
                    "description": point.description,
                    "tripCode": point.idTrip,
                    "image": point.image
                }
            }
            , status=HTTPStatus.OK)

    return JsonResponse(
        {
            'ERROR_CODE': '400',
            'POINT': {
                "id": "0",
                "location": "",
                "name": "",
                "description": "",
                "tripCode": "",
                "image": ""
            }
        },
        status=HTTPStatus.BAD_REQUEST
    )


def deletePoint(request):
    if request.method == "POST":
        id = request.POST.get("id", "")
        Point.objects.filter(id=id).delete()
        return JsonResponse({"ERROR_CODE": "0"}, status=HTTPStatus.OK)
    return JsonResponse({"ERROR_CODE": "400"}, status=HTTPStatus.BAD_REQUEST)
