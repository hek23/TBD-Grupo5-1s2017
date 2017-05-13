#!/usr/bin/env python
# -*- coding: utf-8 -*-
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

def mongoCountPerCountry(countryName):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"place.country": countryName})
    return count

def mongoCountRetweetPerCountry(countryName):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"rt.original_id": {"$ne":"None"}})
    return count

def mongoCountTweetConceptCountry(countryName, concept):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"$and": [{"text": {"$regex" : concept}}, {"place.country" : countryName}]})
    return count

def mongoCountRetweetConceptCountry(countryName, concept):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"$and": [{"text": {"$regex" : concept}}, {"place.country" : countryName}, {"rt.original_id": {"$ne":"None"}}]})
    return count

def mongoCountRetweetFromTo(origin, destiny):
    client = MongoClient('localhost', 27017)
    db = client.politica
    #Primero se sacan todos los retweets originados en el pais "destiny" (destino)
    count = db.tweets.count({"$and":[{"place.country": destiny}, {"rt.original_id": {"$ne":"None"}}, {"rt.origin_country": origin}]})
    return count

def mongoCountRetweetFromToConcept(origin, destiny, concept):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"$and":[{"place.country": destiny}, {"rt.original_id": {"$ne":"None"}}, {"rt.origin_country": origin}, {"text": {"$regex" : concept}}]})
    return count

def getAllCountries():
    #Se crea el conector de SQL
    mysqldatabase = MySQLdb.connect(host="localhost",    # your host, usually localhost
                         user="root",         # your username
                         passwd="root",  # your password
                         db="WW3App")
    #Se crea el cursor para obtener datos
    sqlcursor = mysqldatabase.cursor()
    #Se ejecuta la consulta para obtener las palabras clave
    sqlcursor.execute("SELECT Country.Name FROM Country")
    #Se rescatan dichas palabras desde el cursor.
    wordsSQL = sqlcursor.fetchall()
    #Se procesa el resultado del cursor, para crear el formato solicitado por la librería
    #Se tiene una lista para guardar las palabras
    words=[]
    #Para cada palabra obtenida, se trata y agrega.
    for palabra in wordsSQL:
        words.append(str(palabra[0]))
    #Se retorna la lista de palabras
    return words

def getAllKeywords():
    #Se crea el conector de SQL
    mysqldatabase = MySQLdb.connect(host="localhost",    # your host, usually localhost
                         user="root",         # your username
                         passwd="root",  # your password
                         db="WW3App")
    #Se crea el cursor para obtener datos
    sqlcursor = mysqldatabase.cursor()
    #Se ejecuta la consulta para obtener las palabras clave
    sqlcursor.execute("SELECT Keyword.word FROM Keyword")
    #Se rescatan dichas palabras desde el cursor.
    wordsSQL = sqlcursor.fetchall()
    #Se procesa el resultado del cursor, para crear el formato solicitado por la librería
    #Se tiene una lista para guardar las palabras
    words=[]
    #Para cada palabra obtenida, se trata y agrega.
    for palabra in wordsSQL:
        words.append(str(palabra))
    #Se retorna la lista de palabras
    return words

def makeCountResume():
    #Dado que los paises y palabras son indicadas con numeros, se utilizará el indice en la inserción
    #por sobre la palabra misma, la cual se utilza para las consultas en Mongo.
    conceptos = getAllKeywords()
    paises = getAllCountries()
    #Se inicializan los indices
    keywordId = 1
    countryId = 1
    #Se inicializa la conexión a la base de datos
    #Se crea el conector de SQL
    mysqldatabase = MySQLdb.connect(host="localhost",    # your host, usually localhost
                         user="root",         # your username
                         passwd="root",  # your password
                         db="WW3App")
    #Se crea el cursor para ingresar datos
    sqlcursor = mysqldatabase.cursor()
    for keyword in conceptos:
        countryId = 0
        for pais in paises:
            tweets = mongoCountTweetConceptCountry(pais, keyword)
            retweets = mongoCountRetweetConceptCountry(pais, keyword)
            consulta = "INSERT INTO CountryStat (RetweetsCount, TweetsCount, Country, Keyword) VALUES ("+"'"+str(retweets)+"', '"+str(tweets)+"', '"+str(countryId)+"', '"+ str(keywordId)+"')"
            sqlcursor.execute()
            countryId = countryId + 1
        keywordId = keywordId + 1
    return 0

def makeInfluenceResume():
    #Pendiente
    return 0


makeCountResume()
