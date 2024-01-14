from enum import Enum
import re
import random

# ====================================================================================================
#
#                               Function for the verification field process
#
# ====================================================================================================
class Field(Enum):
    USERNAME = 1
    PASSWORD = 2
    EMAIL = 3


def verificationField(type, field):
    if type == Field.USERNAME:
        return isValidUsername(username=field)
    if type == Field.PASSWORD:
        return isValidPassword(password=field)
    if type == Field.EMAIL:
        return isValidEmail(email=field)


def isValidPassword(password):
    if len(password) < 8:
        return False
    return True


def isValidEmail(email):
    return not re.search(r"([a-zA-Z0-9])+@([a-zA-Z])+\.([a-zA-Z])+", email) is None


def isValidUsername(username):
    if len(username) > 15 or len(username) < 3:
        return False
    return username.isalpha()


# ====================================================================================================
#
#                       Token gestion
#
# ====================================================================================================


def genToken(username):
    begin_token = str(hash(username))[:10]
    end_token = random.randint(1000000000, 9999999999)
    return begin_token + str(end_token)


def isTokenValid(username, token):
    begin_token = str(hash(username))[:10]
    token = token[:10]
    return token == begin_token
