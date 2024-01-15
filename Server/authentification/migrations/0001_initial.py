# Generated by Django 4.2 on 2024-01-12 16:38

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Trip',
            fields=[
                ('id', models.IntegerField(primary_key=True, serialize=False)),
                ('name', models.TextField()),
                ('description', models.TextField()),
                ('place', models.TextField()),
                ('users', models.TextField()),
            ],
        ),
    ]