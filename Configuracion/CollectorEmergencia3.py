#*-*coding: utf-8*-*
from __future__ import unicode_literals
from datetime import datetime
import datetime
import sys
import pymongo
from pymongo import MongoClient
import tweepy
import socket
import requests
import time
import httplib
from httplib import IncompleteRead
import json
from tweepy import OAuthHandler
import MySQLdb
#SCRIPT DE INSERCION DE DATOS A BASE DE DATOS EN COLA
#DESDE TWITTER PARA SISTEMA DE ANALISIS. VERSION 2
#Fuente: http://piratefache.ch/twitter-streaming-api-with-tweepy/
#Modificado para Python 2.7 y filtrado
#Este código trata la información obtenida de twitter e inserta a mongoDB para su posterior tratamiento
#Autor: Héctor Fuentealba
#Version 2.0
#Requisitos:
#-Python 2.X.Y (se recomienda 2.7.12)
#-PIP 9.X
#-Googlemaps Python Library
#-PyMongo v3.x
#-Tweepy v3.x
#_MySQL Connector
#Correcciones respecto a v1.0:
# Corregido bug que generaba fallo crítico al recibir ciertos carácteres
# Corregido tratamiento de caracteres Unicode
# Corregido error de cálculo en coordenadas geolocalizadas
#Correcciones respecto a v1.1:
# Se generan consultas automaticas a googleMaps en el caso de no tener locación
# Se limitan las consultas a GoogleMaps por el numero free de ellas en un limite de Tiempo
# No se insertan los tweets sin geolocalizacion
#Correcciones respecto a v1.2:
#Se corrige error de lectura incompleta.
#Correcciones respecto a v1.3:
#SE REHACE CÓDIGO. SE ESPECIFICA VERSION 2

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
#Funcion para obtener palabras de MySQL
def get_words():
    #Se crea el conector de SQL
    mysqldatabase = MySQLdb.connect(host="localhost",    # your host, usually localhost
                         user="root",         # your username
                         passwd="root",  # your password
                         db="WW3App")
    #Se crea el cursor para obtener datos
    sqlcursor = mysqldatabase.cursor()
    #Se ejecuta la consulta para obtener las palabras clave
    sqlcursor.execute("SELECT Sinonimos.sinonimo FROM Sinonimos where concepto = 3")
    #Se rescatan dichas palabras desde el cursor.
    wordsSQL = sqlcursor.fetchall()
    #Se procesa el resultado del cursor, para crear el formato solicitado por la librería
    #Se tiene una lista para guardar las palabras
    words=[]
    #Para cada palabra obtenida, se trata y agrega.
    for palabra in wordsSQL:
        words.append(str(palabra[0]))
    #Ahora se añaden los sinonimos
    sqlcursor.execute
    #Se retorna la lista de palabras
    return words
#Funcion para insertar un documento con MongoDB
#Entrada: diccionario o documento con formato JSON

def mongo_queue_insert(doc):
    #Se abre la conexion Mongo
    client = MongoClient('localhost', 27017)
    db = client.cola
    #Se procede con la inserción Solo si tiene locación.
    db.Concepto3.insert(doc)
    return 0
    #Se retorna el id del documento insertado

class TwitterStreamListener(tweepy.StreamListener):
    """ A listener handles tweets are the received from the stream.
    This is a basic listener that just prints received tweets to stdout.
    """

    def on_status(self, status):
        #Como puede haber una violacion de limite
        #Se inserta el tweet a la base de datos a encolar
        mongo_queue_insert(status._json)
        print "Se inserta doc id: ", status._json['id']
        #Si es un RT se buscan todos los RT del original y se guarda el original (Hasta 100)
        if 'retweeted_status' in status._json:
            print "Documento tiene retweets. Num: ", status._json['id']
            rts =api.retweets(int(status._json['retweeted_status']['id']))
            print "tiene ", len(rts)
            for rt in rts:
                print "Insertando doc"
                mongo_queue_insert(rt._json)
        return True

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

    access_token = "851544787475935233-J2oXwlAFxV1z2Sumaf4U5rVwTvA2AGU"
    access_token_secret = "lKNMme6n7Saxqnr8kneci9s3VQpdOxIVlz8VRoYwAHfda"
    consumer_key = "LqQeTjG2SM6ruy400IQ9U2TwP"
    consumer_secret = "iTD5jtDCzvLtd6SbAtMO40Aw236wm792CVILLyihPvV4RshuL6"

    # Authentication
    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
    auth.secure = True
    auth.set_access_token(access_token, access_token_secret)

    api = tweepy.API(auth, wait_on_rate_limit=True, wait_on_rate_limit_notify=True, retry_count=5, retry_delay=5, retry_errors=5)

    streamListener = TwitterStreamListener()

    #IncompleteReadException. Solución extraida de:
    #http://stackoverflow.com/questions/28717249/error-while-fetching-tweets-with-tweepy
    #Puede generar pérdida de información. Esta solución es momentánea mientras se implementa el stack con Kafka.
    while True:
        try:
            # Connect/reconnect the stream
        	myStream = tweepy.Stream(auth=api.auth, listener=streamListener, parser=tweepy.parsers.JSONParser())
            # DON'T run this approach async or you'll just create a ton of streams!
        	myStream.filter(track=get_words())
    	except KeyboardInterrupt:
            # Or however you want to exit this loop
        	myStream.disconnect()
        	break
        except:
            # Oh well, reconnect and keep tracking
            #print "FALL"
            continue
