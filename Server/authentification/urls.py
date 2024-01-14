from django.urls import path, include
from authentification import views
from authentification.API import auth, trip, point

urlpatterns = [
    path('', views.home, name='home'),
    path(
        "auth/",
        include(
            [
                path('signup', auth.signup, name='signup'),
                path('signin', auth.signin, name='signin'),
                path('signout', auth.signout, name='signout')
            ]
        )
    ),
    path(
        "trip/",
        include(
            [
                path('create_trip', trip.createTrip, name='create_trip'),
                path('import_trip', trip.importTrip, name='import_trip')
            ]
        )
    ),
    path(
        "point/",
        include(
            [
                path('create_point', point.createPoint, name='create_point'),
                path('import_point', point.importPoint, name='import_point'),
                path('delete_point', point.deletePoint, name='delete_point')
            ]
        )
    ),
    path('activate/<uidb64>/<token>', views.activate, name='activate')
]
