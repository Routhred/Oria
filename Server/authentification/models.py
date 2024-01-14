from django.db import models


# Create your models here.

class Trip(models.Model):
    id = models.IntegerField(primary_key=True)
    name = models.TextField()
    description = models.TextField()
    place = models.TextField()
    users = models.TextField()


class Point(models.Model):
    id = models.IntegerField(primary_key=True)
    idTrip = models.IntegerField()
    name = models.TextField()
    description = models.TextField()
    location = models.TextField()
    image = models.TextField()
