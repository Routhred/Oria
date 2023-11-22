from django.shortcuts import render, redirect
from .forms import SignUpForm
from django.contrib.auth import authenticate, login

def signup(request):
    if request.method == 'POST':
        form = SignUpForm(request.POST)
        if form.is_valid():
            form.save()
            username = form.cleaned_data.get('username')
            raw_password = form.cleaned_data.get('password1')
            user = authenticate(username=username, password=raw_password)
            login(request, user)
            return redirect('home')  # Redirigez vers la page d'accueil après l'inscription
    else:
        form = SignUpForm()
    return render(request, 'signup.html', {'form': form})
