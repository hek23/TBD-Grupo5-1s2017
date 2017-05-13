#!/usr/bin/env python
# -*- coding: utf-8 -*-
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
import googlemaps
import MySQLdb
#############################################################################
#CARGA DE ELEMENTOS DE SISTEMA Y ENCODING
reload(sys)
sys.setdefaultencoding('utf8')
#############################################################################
#VARIABLES GLOBALES
#Tiempo al inicio de consultas
time_start = datetime.datetime.now()
#Tiempo al de ultima consulta a GMaps
time_last_gmaps = None
#Acceso a API de googleMaps
gmaps = googlemaps.Client(key='AIzaSyCXqoIj7e-cqQkWBR-KpP_AIAf34sUWNs4')
#Limite de consultas diarias para la API DE GOOGLE
API_LIMIT = 2200
#Consultas que quedan disponibles para realizarse durante el dia
API_REMAINING = 2200
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
    client = MongoClient('localhost', 27017)
    db = client.cola
    #Se extraen limit documentos
    queryResult= db.tweets.find_one()
    #Se saca el documento, se añade a la lista y luego se elimina de mongo usando su id único
    db.tweets.remove({"_id": queryResult['_id']})
    return queryResult
def googlemapsask(localizacion):
    global gmaps
    global API_LIMIT
    global API_REMAINING
    global time_last_gmaps
    if (API_REMAINING < 1):
        #No se puede consultar a menos que sea otro dia.
        if (datetime.datetime.now().day != time_last_gmaps.day):
            API_REMAINING = API_LIMIT
            googlemapsask(localizacion)
        else:
            return None
    elif ((localizacion is None) or not(localizacion.isalpha())):
        return None
    else:
        #Se consulta por los lugares del usuario
        try:
            geocode = gmaps.geocode(localizacion.encode('utf-8'))
            time_last_gmaps = datetime.datetime.now()
            API_REMAINING = API_REMAINING - 1
            if len(geocode)<1:
                return None
            else:
                #Lat y long
                coord = (geocode[0]['geometry']['location']['lat'], geocode[0]['geometry']['location']['lng'])
                #Code Country
                country_code = geocode[0]['address_components'][len(geocode[0]['address_components'])-1]['short_name']
                #Country full
                db = MySQLdb.connect(host="localhost",    # your host, usually localhost
                                     user="root",         # your username
                                     passwd="root",  # your password
                                     db="WW3App")        # name of the data base
                sqlCursor = db.cursor()
                query = "SELECT * FROM WW3App.Country WHERE Country.Code = " + "'" + country_code + "'";
                sqlCursor.execute(query)
                res = sqlCursor.fetchall()
                if (len(res)>0):
                    country_name = res[0][0]
                    db.close()
                    place = {
                        'country' : country_name,
                        'country_code' : country_code,
                        'name_place' : localizacion,
                        'geo_center': {
                            'latitude' : coord[0],
                            'longitude': coord[1]
                        }
                        }
                    return place
                else:
                    db.close()
                    return None
        except:
            return None


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
    #Si es retweet o respuesta, se agrega el id del original o anterior y el pais de origen del mismo
    'rt':{
        'original_id': "None",
        'origin_country': "None"
    },
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
        #else:
            #Si es nulo, no hay información y se descarta
            #return None
        #Si el código de país no es nulo, hay información
        if statusJSON['place']['country_code'] is not None:
            info_tweet['place']['country_code'] = statusJSON['place']['country_code'].encode('ascii','ignore')
        #else:
            #Si es nulo, se descarta el tweet
        #    return None
        #Si el nombre del lugar no es nulo, entonces se agrega.
        if statusJSON['place']['full_name'] is not None:
            info_tweet['place']['name_place'] = statusJSON['place']['full_name'].encode('ascii','ignore')
        #else:
            #Si es nulo, se descarta
        #    return None
        #Si el lugar tiene polígono (el cual define al lugar), se analiza
        if (('bounding_box' in statusJSON['place']) and ('coordinates' in statusJSON['place']['bounding_box'])):
            #Si el largo es mayor a 0, se busca el punto central del polígono
            if (len(statusJSON['place']['bounding_box']['coordinates'])>0):
                center_point = get_center_point(statusJSON['place']['bounding_box']['coordinates'][0])
                info_tweet['place']['geo_center']['latitude'] = center_point[0]
                info_tweet['place']['geo_center']['longitude'] = center_point[1]
            #else:
                #Si no, se descarta
            #    return None

    else:
        #Si no hay info, se procede a buscarla.
        print statusJSON['id']
        lugar = googlemapsask(statusJSON['user']['location'])
        #Si no se encuentra, entonces se deshecha
        if lugar is None:
            return None
        else:
            info_tweet['place'] = lugar
    #Se agregan los hashtags...
    tags = []
    while (len(statusJSON['entities']['hashtags']) > 0):
        tags.append(statusJSON['entities']['hashtags'].pop()['text'].encode('ascii','ignore'))
    info_tweet['hashtags'] = tags
    if ('retweeted_status' in statusJSON):
        info_tweet['rt']['original_id'] = statusJSON['retweeted_status']['id']
        #Se revisa la ubicación del retweet
        info_tweet['rt']['origin_country'] = googlemapsask(statusJSON['retweeted_status']['user']['location'])['country']
    mongo_prod_insert(info_tweet)
    return 0

while True:
    client = MongoClient("mongodb://127.0.0.1:27017")
    #El procedimiento se ejecuta mientras hayan documentos que procesar
    #"
    while (client.cola.tweets.count() > 0):
        documento = mongo_queue_load()
        twitterFilter(documento)
        print "Quedan ", client.cola.tweets.count(), "Documentos"
