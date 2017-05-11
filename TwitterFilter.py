#*-*coding: utf-8*-*
from __future__ import unicode_literals
from datetime import datetime
import datetime
import sys
import pymongo
from pymongo import MongoClient
import socket
import requests
import time
import httplib
import json
#############################################################################
#CARGA DE ELEMENTOS DE SISTEMA Y ENCODING
reload(sys)
sys.setdefaultencoding('utf8')
#############################################################################
#VARIABLES GLOBALES
#Tiempo al inicio
time_start = datetime.datetime.now()
#Tiempo al inicio de la aplicacion
tiempo_inicio_serv = datetime.datetime.now()
#############################################################################
#############################################################################
##FUNCIONES GLOBALES ########################################################
#############################################################################
def mongo_prod_insert(doc):
    #Se abre la conexion Mongo
    client = MongoClient()
    db = client.politica
    #Se procede con la inserción Solo si tiene locación.
    if (doc is None):
        return 0
    else:
        db.tweets.insert(doc)
        #Se retorna el id del documento insertado
        return 1

def mongo_queue_load():
    print "Cliente de carga"
    client = MongoClient()
    db = client.cola
    print "Db de cola"
    #Se extraen limit documentos
    docs= []
    queryResult= db.tweets.find().limit(1)
    print "query ok"
    for documento in queryResult:
        #Se saca el documento, se añade a la lista y luego se elimina de mongo usando su id único
        #documento= db.tweets.find_one()
        docs.append(documento)
        print "doc añadido"
        db.tweets.remove({"_id": documento['_id']})
    return docs

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

def twitterFilter(statusJSON):
    #Se enuncia el diccionario que contiene la información del tweet
    info_tweet= {'tweet_id': int(statusJSON['id_str']),
    #Se agrega el contenido del tweet
    'text': str(statusJSON['text'].encode('ascii','ignore')),
    #Se agrega la fecha de publicación
    'created_at': str(statusJSON['created_at']),
    #Se agrega el lenguaje del tweet
    'tweet_lang' : str(statusJSON['lang']),
    #Se agrega la información de usuario
    'user': {
        'user_id' : int(statusJSON['user']['id']),
        'userscreen' : str(statusJSON['user']['screen_name'].encode('ascii','ignore')),
        'full_username' : str(statusJSON['user']['name'].encode('ascii','ignore')),
        'following_count' : int(statusJSON['user']['friends_count']),
        'followers_count' : int(statusJSON['user']['followers_count'])},
    #Se agrega al información del lugar
    'place': {
        'country' : "None",
        'country_code' : "None",
        'name_place' : "None",
        'geo_center': {
            'latitude' : 0,
            'longitude': 0
        }
    },
    #Si es retweet o respuesta, se agrega el id del original o anterior
    'original_id': "None",
    #Se agregan los hashtags que posee el tweet
    'hashtags': []
    #Se indica el tipo de referencia (?)
    }
    #NOTA: Se tiene una posibilidad de ubicar datos no presentes en el tweet,
    #Pero no se utiliza por ahora, dada la limitación de datos.
    #Ahora se agregan los datos no básicos o no directos, como información del lugar.
    #Si esta la llave "place" en statusJSON y su valor no es nulo
    if (('place' in statusJSON) and (statusJSON['place'] is not None)):
        #Entonces implica que posiblemente hay información del lugar.
        #Si el pais no es nulo, hay información
        if statusJSON['place']['country'] is not None:
            info_tweet['place']['country'] = statusJSON['place']['country'].encode('ascii','ignore')
        else:
            #Si es nulo, no hay información y se descarta
            return None
        #Si el código de país no es nulo, hay información
        if statusJSON['place']['country_code'] is not None:
            info_tweet['place']['country_code'] = statusJSON['place']['country_code'].encode('ascii','ignore')
        else:
            #Si es nulo, se descarta el tweet
            return None
        #Si el nombre del lugar no es nulo, entonces se agrega.
        if statusJSON['place']['full_name'] is not None:
            info_tweet['place']['name_place'] = statusJSON['place']['full_name'].encode('ascii','ignore')
        else:
            #Si es nulo, se descarta
            return None
        #Si el lugar tiene polígono (el cual define al lugar), se analiza
        if (('bounding_box' in statusJSON['place']) and ('coordinates' in statusJSON['place']['bounding_box'])):
            #Si el largo es mayor a 0, se busca el punto central del polígono
            if (len(statusJSON['place']['bounding_box']['coordinates'])>0):
                center_point = get_center_point(statusJSON['place']['bounding_box']['coordinates'][0])
                info_tweet['place']['geo_center']['latitude'] = center_point[0]
                info_tweet['place']['geo_center']['longitude'] = center_point[1]
            else:
                #Si no, se descarta
                return None
        #Se agregan los hashtags...
        tags = []
        while (len(statusJSON['entities']['hashtags']) > 0):
            tags.append(statusJSON['entities']['hashtags'].pop()['text'].encode('ascii','ignore'))
        info_tweet['hashtags'] = tags
        #FALTA VER EL TEMA DE RESPONSE (Proyectado a futuro. NO ahora)
        print info_tweet
        mongo_prod_insert(info_tweet)
        return 0

while True:
    client = MongoClient()
    print "Se crea cliente"
    #El procedimiento se ejecuta mientras hayan documentos que procesar
    #print "Quedan ", client.cola.tweets.count(), "Documentos"
    print "Intento de carga"
    documentos = mongo_queue_load()
    print "Carga lista"
    for doc in documentos:
        twitterFilter(doc)
