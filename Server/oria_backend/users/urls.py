from django.urls import path
from . import views

urlpatterns = [
    path('signup/', views.signup, name='signup'),
    # Ajoutez ici les URL pour le login si vous ne voulez pas utiliser les vues par défaut de Django
]
