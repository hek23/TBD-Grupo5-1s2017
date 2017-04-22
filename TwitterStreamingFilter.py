#*-*coding: utf-8*-*
from __future__ import unicode_literals
import googlemaps
from datetime import datetime
import datetime
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
#Version 1.2
#Correcciones respecto a v1.0:
# Corregido bug que generaba fallo crítico al recibir ciertos carácteres
# Corregido tratamiento de caracteres Unicode
# Corregido error de cálculo en coordenadas geolocalizadas
#Correcciones respecto a v1.1:
# Se generan consultas automaticas a googleMaps en el caso de no tener locación
# Se limitan las consultas a GoogleMaps por el numero free de ellas en un limite de Tiempo
# No se insertan los tweets sin geolocalizacion

#############################################################################
#CARGA DE ELEMENTOS DE SISTEMA Y ENCODING
reload(sys)
sys.setdefaultencoding('utf8')
#############################################################################
#VARIABLES GLOBALES
#Objeto que se conecta con GoogleMaps
gmaps = googlemaps.Client(key='AIzaSyCXqoIj7e-cqQkWBR-KpP_AIAf34sUWNs4')
#Objetos de respaldo. Lista de keys válidas
gmaps_backup = []
#Contador de consultas a Api de Gmaps, para delimitarlas
contador_maps = 0
#Tiempo al inicio
time_start = datetime.datetime.now()
#Limite de la API
API_LIMIT = 2500
#Limite Critico despues de pago
API_CRITICAL_LIMIT = 102500
#Contadores para métricas de medicion
#Cuenta los tweets que tienen lugar asociado
has_place = 0
#Cuenta los tweets de los cuales se debe sacar info del usuario
info_user = 0
#Cuenta los tweets que no poseen ubicación generable
no_place = 0
#Contador de tweets extraidos
i = 0
#############################################################################
#Funcion para escribir metrica en archivo
def metrica():
    global i
    global has_place
    global info_user
    global no_place
    global contador_maps
    archivo = open('/home/hek23/metrica.dat', 'w')
    support_string = "Se realizaron " + str(i) + "consultas a Twitter"
    archivo.writelines(support_string)
    support_string = "Se realizaron " + srt(contador_maps) + "consultas a GoogleMaps"
    archivo.writelines(support_string)
    support_string = str(no_place) + " tweets no poseen informacion geolocalizable"
    archivo.writelines(support_string)
    support_string = str(has_place) + " tweets si poseen informacion del lugar de origen"
    archivo.writelines(support_string)
    support_string = "En " + str(info_user) + " tweets se debio utilizar la info del usuario para localizarlo"
    archivo.writelines(support_string)
    archivo.close()

#Funcion para obtener palabras de archivo
def get_words():
    #Apertura de archivo
    archivo = open('words.dat', 'r')
    words = []
    #Se filtran las palabras y genera la lista.
    for linea in archivo:
        minilista = linea.split(' ')
        for palabra in minilista:
            words.append(palabra.strip())
    #Se cierra el archivo
    archivo.close()
    #Se retorna la lista de palabras
    return words
#Funcion para insertar un documento con MongoDB
#Entrada: diccionario o documento con formato JSON
def mongo_insert(doc):
    #Se abre la conexión a Mongo
    client = pymongo.MongoClient('localhost',27017)
    print "Conectando a Mongo"
    db = client.politica
    #Se procede con la inserción Solo si tiene locación.
    if (doc['place']['geo_center']['latitude'] == 0) and (doc['place']['geo_center']['longitude'] == 0):
        #Por ahora se Cuenta
        global no_place
        no_place = no_place + 1
    #    return 0
    #else :
    id=db.tweets.insert(doc)
        #Se retorna el id del documento insertado
    return id
#Funcion para calcular el punto central coordenado
#Entrada: Lista de puntos del tipo [[LONG, LAT], [LONG, LAT]] de tamaño indeterminado
#Salida: Par que indica el punto central para ubicación referenciada, del tipo [LAT, LONG]
def get_center_point(points):
    #Se inicializan las variables latitud y longitud
    print "Calculando centro"
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

#Función que obtiene el diccionario con la información de la locación
#de la consulta de Google. Refiere a la información administrativa/politica y la locación
#en la latitud y longitud
def get_location_info(location):
    # Consulta por la dirección
    geocode_result = gmaps.geocode(location)
    global contador_maps
    contador_maps = contador_maps + 1
    print "En get_location_info"
    #Si no hay un solo resultado, implica que no hay una ubicacion especifica
    if (len(geocode_result)<2):
        return [0,0]
    else:
        geocode_points=geocode_result[0]['geometry']['location']
    # Ahora se retornan los puntos como par [Lat][Long]
    country_info = geocode_result[0]['address_components']
    return geocode_points['lat'], geocode_points['lng'], country_info[len(country_info)-1]

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
        'userscreen': status.user.screen_name.encode('ascii','ignore'),
        'full_username': status.user.name.encode('ascii','ignore'),
        'following_count': int(status.user.friends_count),
        'followers_count': int(status.user.followers_count)},
    'place': {
        'country' : "None",
        'country_code' : "None",
        'name_place' : "None",
        'geo_center': {
            'latitude' : 0,
            'longitude': 0
        }
    },
    'original_id': "None",
    'hashtags': []
    }


    #Se agregan los hashtags

    #Ahora se generan las coordenadas
    #Se privilegiará la localización con GPS
    if (hasattr(status, 'place') and status.place is not None):
        ####################################################################################
        #Se agrega contador para métrica
        global has_place
        has_place = has_place + 1
        print "Hay GPS"
        ####################################################################################
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
            print "Revisando caja"
            if (len(status.place.bounding_box.coordinates)>0):
                print "Si hay caja"
                center_point = get_center_point(status.place.bounding_box.coordinates[0])
                info_tweet['place']['geo_center']['latitude'] = center_point[0]
                info_tweet['place']['geo_center']['longitude'] = center_point[1]
            #else:
                #Place no tiene posición.
        #else
    else:
        #No tiene el lugar definido en el tweet, así que se aplica la ubicación por defecto del usuario
        ###################################################################################################
        #Se agrega al contador para definición de métrica
        global info_user
        info_user = info_user + 1
        print "Sacando info del user"
        ###################################################################################################
        if status.user.location is not None:
            info_tweet['place']['name_place'] = status.user.location.encode('ascii','ignore')
            #Ahora se extrae la info del lugar
            #Si es la consulta anterior a la 2500, entonces se realiza
            #Por ser medición metrica, se aceptará la cantidad crítica diaria
            global contador_maps
            global API_CRITICAL_LIMIT
            if (contador_maps < API_LIMIT):
                print "Aun no cumple API"
                place_info = get_location_info(info_tweet['place']['name_place'])
                info_tweet['place']['geo_center']['latitude'] = place_info[0]
                info_tweet['place']['geo_center']['longitude'] = place_info[1]
                #Luego, el pais y código del mismo
                info_tweet['place']['country'] = place_info[2]['long_name']
                info_tweet['place']['country_code'] = place_info[2]['short_name']
        else:
            info_tweet['place']['name_place'] = status.user.location
    #Se agregan los hashtags...
    tags = []
    print "Agregando tags"
    while (len(status.entities['hashtags']) > 0):
        #Agrega los hashtags!
        tags.append(status.entities['hashtags'].pop()['text'].encode('ascii','ignore'))
    info_tweet['hashtags'] = tags
    #Si es retweet...
    if hasattr(status, 'retweeted_status'):
        print "recursivo!"
        doc_id = twitterFilter(status.retweeted_status)
        info_tweet['original_id'] = doc_id
    #AQUI SE ENVIA A MONGO!
    #Por mientras, se imprimirá
    info_tweet_JSON = json.dumps(info_tweet)
    #print info_tweet_JSON
    mongo_id = mongo_insert(info_tweet)
    #write_tweet_file(info_tweet_JSON, mongo_id)
    return info_tweet['tweet_id']

class TwitterStreamListener(tweepy.StreamListener):

    def on_status(self, status):
        tiempo = datetime.datetime.now()
        global i
        global contador_maps
        global time_start
        if (tiempo.day != time_start.day):
            #Se resetea la api
            contador_maps = 0
            time_start = tiempo
        while (contador_maps < API_CRITICAL_LIMIT):
            twitterFilter(status)
            i = i+1
            return True
        metrica()
        return False
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
            print "ERROR"

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

    myStream.filter(track=['Trump'], stall_warnings=True) #podria ser util poner async=True
