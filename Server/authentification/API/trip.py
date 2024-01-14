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


def createTrip(request):
    if request.method == "POST":
        print(f"DEBUG: body {request.POST}")
        name = request.POST.get('name', '')
        description = request.POST.get('description', '')
        place = request.POST.get('place', '')
        user = request.POST.get('username', '') + ';'

        # get trip id
        trip_id = 1
        if Trip.objects.count() > 0:
            last_id = (Trip.objects.last()).id
            trip_id = last_id + 1

        # TODO verification field
        Trip.objects.create(
            id=trip_id,
            name=name,
            description=description,
            place=place,
            users=user
        )
        return JsonResponse({'ERROR_CODE': '0', 'TRIP_ID': trip_id}, status=HTTPStatus.CREATED)

    return JsonResponse({'ERROR_CODE': '400', 'TRIP_ID': ''}, status=HTTPStatus.BAD_REQUEST)


def importTrip(request):
    if request.method == "POST":
        print(request.method)
        trip_id = request.POST.get('trip_id', '')
        username = request.POST.get('username', '')
        trip = Trip.objects.get(pk=trip_id)

        points = Point.objects.filter(idTrip=trip_id)
        points_id = []
        print(points)
        for point in points:
            points_id.append(str(point.id))
        print(points_id)
        users = trip.users
        if not username in users:
            users = users + username + ';'
        trip.users = users
        trip.save()

        return JsonResponse(
            {
                'ERROR_CODE': '0',
                'TRIP': {
                    "id": trip_id,
                    "location": trip.place,
                    "name": trip.name,
                    "description": trip.description,
                    "points": ""
                },
                'POINTS': points_id
            }
            , status=HTTPStatus.OK)

    return JsonResponse(
        {
            'ERROR_CODE': '400',
            'TRIP': {
                "id": "0",
                "location": "",
                "name": "",
                "description": "",
                "points": ""
            },
            'POINTS': []
        }
        , status=HTTPStatus.BAD_REQUEST)
