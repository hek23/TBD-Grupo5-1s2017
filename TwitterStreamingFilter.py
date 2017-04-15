#*-*coding: utf-8*-*
from __future__ import unicode_literals
import sys
import pymongo
import tweepy
import socket
import requests
import time
import json
from tweepy import OAuthHandler
#Fuente: http://piratefache.ch/twitter-streaming-api-with-tweepy/
#Modificado para Python 2.7 y filtrado
#Este código trata la información obtenida de twitter y la filtra, además de tratar los carcateres especiales
#Autor: Héctor Fuentealba
#Version 1.1
#Correcciones respecto a v1.0:
# Corregido bug que generaba fallo crítico al recibir ciertos carácteres
# Corregido tratamiento de caracteres Unicode
# Corregido error de cálculo en coordenadas geolocalizadas
reload(sys)
sys.setdefaultencoding('utf8')
i=0
#Funcion para insertar un documento con MongoDB
def mongo_insert(doc):
    client = pymongo.Connection()
    db = client.politica
    id=db.tweets.insert(doc)
    return id

#Funcion para calcular el punto central coordenado
#Entrada: Lista de puntos del tipo [[LONG, LAT], [LONG, LAT]] de tamaño indeterminado
#Salida: Par que indica el punto central para ubicación referenciada, del tipo [LAT, LONG]
def get_center_point(points):
    #Se inicializan las variables latitud y longitud
    latitude = 0
    longitude = 0
    #Para cada punto de la lista que define el polígono
    for point in points:
        #Se suman las coordenadas
        latitude = latitude + point[1]
        longitude = longitude + point[0]
    #Se calculan las coordenadas medias.
    latitude = latitude / len(points)
    longitude = longitude / len(points)
    #Se redondean al sexto decimal
    latitude = round (latitude,6)
    longitude = round (longitude,6)
    #Se retornan las coordenadas como par Lat, Long
    return latitude, longitude

def twitterFilter(status):
    reload(sys)
    sys.setdefaultencoding('utf8')
    #Por ahora se fabrica un JSON que se imprime en un archivo de texto.
    #Lo ideal es que este Json se transfiera de inmediato a Mongo
    #El ID del tweet puede ser Int, dado que dejo de tener limite en Python.
    #print status.entities['hashtags']
    #Se enuncia el diccionario que contiene la información del tweet
    info_tweet= {'tweet_id': int(status.id_str),
    #Se agrega el contenido del tweet
    'text': status.text.encode('ascii','ignore'),
    #Se agrega la fecha de publicación
    'created_at': str(status.created_at),
    'tweet_lang' : str(status.lang),
    'user': {
        'user_id' : int(status.user.id),
        'full_username': status.user.name.encode('ascii','ignore'),
        'userscreen': status.user.screen_name.encode('ascii','ignore'),
        'following_count': int(status.user.friends_count),
        'followers_count': int(status.user.followers_count)},
    'place': {
        'country' : "None",
        'country_code' : "None",
        'name_place' : "None",
        'geo_center': {
            'latitude' : "None",
            'longitude': "None"
        }
    },
    'original_id': "None",
    'hashtags': []
    }


    #Se agregan los hashtags

    #Ahora se generan las coordenadas
    #Se privilegiará la localización con GPS
    if (hasattr(status, 'place') and status.place is not None):
        if status.place.country is not None:
            info_tweet['place']['country'] = status.place.country.encode('ascii','ignore')
        else:
            info_tweet['place']['country'] = status.place.country
        if status.place.country_code is not None:
            info_tweet['place']['country_code'] = status.place.country_code.encode('ascii','ignore')
        else:
            info_tweet['place']['country_code'] = status.place.country_code
        if status.place.full_name is not None:
            info_tweet['place']['name_place'] = status.place.full_name.encode('ascii','ignore')
        else:
            info_tweet['place']['name_place'] = status.place.full_name

        if (hasattr(status.place, 'bounding_box') and hasattr(status.place.bounding_box,'coordinates')):
            if (len(status.place.bounding_box.coordinates)>0):
                center_point = get_center_point(status.place.bounding_box.coordinates[0])
                info_tweet['place']['geo_center']['latitude'] = center_point[0]
                info_tweet['place']['geo_center']['longitude'] = center_point[1]
            #else:
                #Place no tiene posición.
        #else
            #No tiene dibujo o coordenadas, por tanto se obtiene el punto (o al menos esa es la idea)

            #Falta busqueda y obtencion del punto
    else:
        #No tiene el lugar definido en el tweet, así que se aplica la ubicación por defecto del usuario
        if status.user.location is not None:
            info_tweet['place']['name_place'] = status.user.location.encode('ascii','ignore')
        else:
            info_tweet['place']['name_place'] = status.user.location
    #Se agregan los hashtags...
    tags = []
    while (len(status.entities['hashtags']) > 0):
            #Agrega los hashtags!
            tags.append(status.entities['hashtags'].pop()['text'].encode('ascii','ignore'))

    info_tweet['hashtags'] = tags
    #Si es retweet...
    if hasattr(status, 'retweeted_status'):
        doc_id = twitterFilter(status.retweeted_status)
        info_tweet['original_id'] = doc_id
    #AQUI SE ENVIA A MONGO!
    #Por mientras, se imprimirá
    info_tweet_JSON = json.dumps(info_tweet)
    #print info_tweet_JSON
    mongo_insert(info_tweet)
    return info_tweet['tweet_id']

class TwitterStreamListener(tweepy.StreamListener):
    """ A listener handles tweets are the received from the stream.
    This is a basic listener that just print s received tweets to stdout.
    """

    def on_status(self, status):
        global i
        twitterFilter(status)
        if (i<1):
            i= i+1
            return True
        else:
            return False
        return True

        #

    # Twitter error list : https://dev.twitter.com/overview/api/response-codes

    def on_error(self, status_code):
        if status_code == 304:
            print "Not Modified"
            print "There was no new data to return."
        elif status_code == 400:
            print "Bad Request"
            print "The request was invalid or cannot be otherwise served"
            print "Please check your Auth Tokens"
        elif status_code == 401:
            print "Missing or incorrect authentication credentials"
        elif status_code == 403:
            print "Forbidden"
            print "The request is understood, but it has been refused or access is not allowed. Limit is maybe reached"
        elif status_code == 404:
            print "Not Found"
            print "The URI requested is invalid or the resource requested, such as a user, does not exists"
        elif status_code == 406:
            print "Not Acceptable"
            print "Returned by the Search API when an invalid format is specified in the request."
        elif status_code == 410:
            print "Gone"
            print "You are trying to access to a resource in the API that has been turned off"
        elif status_code == 420:
            print "Enhance Your Calm"
        elif status_code == 422:
            print "Unprocessable Entity"
            print "Image uploaded to POST account"
        elif status_code == 429:
            print "Too Many Requests"
            print "Your request cannot be served due to the application’s rate limit having been exhausted for the resource"
        elif status_code == 500:
            print "Internal Server Error"
            print "Something in Twitter is broken"
        elif status_code == 502:
            print "Bad Gateway"
            print "Twitter is down or being upgraded."
        elif status_code == 503:
            print "Service Unavailable"
            print "The Twitter servers are up, but overloaded with requests. Try again later"
        elif status_code == 504:
            print "Gateway timeout"
            print "The Twitter servers are up, but the request couldn’t be serviced due to some failure within our stack. Try again later."
        else:
            return True

        return False



if __name__ == '__main__':

    access_token = "2714081630-4mRkgniGe6BJo8eLgH1pIHLkohBixNyLepoB7Fm"
    access_token_secret = "fVLDgfMY9px0U5LBbo8kGIHXgBJ2gom7wFLuHAZF7JMX0"
    consumer_key = "eCIzPizeVfsQAP1jKghF6OInI"
    consumer_secret = "TMVg3dkicdroPmRvMS7Zp2yabEssKC1R5FYaBIXn3TIfQqybkf"

    # Authentication
    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
    auth.secure = True
    auth.set_access_token(access_token, access_token_secret)

    api = tweepy.API(auth, wait_on_rate_limit=True, wait_on_rate_limit_notify=True, retry_count=10, retry_delay=5, retry_errors=5)

    streamListener = TwitterStreamListener()
    myStream = tweepy.Stream(auth=api.auth, listener=streamListener)

    myStream.filter(track=['Tbdtest5'])
