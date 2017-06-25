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

def esBisiesto(year):
    return year % 4 == 0 and year % 100 != 0 or year % 400 == 0

def fechaString():
    #Primero se obtiene el dia anterior al actual para el resumen
    fecha=datetime.date.today()
    dia = fecha.day -1
    mes = fecha.month
    ano = fecha.year
    if dia<1:
        #Entonces es el dia del mes anterior
        mes = mes -1
        if (mes == 0):
            mes = mes +12
            ano = ano -1
        #Ahora se ve que dia queda
        if mes in [1,3,5,7,8,10,12]:
            mes = mes + 31
        elif (mes == 2):
            if (esBisiesto(ano)):
                mes = mes + 29
            else:
                mes = mes+28
        else:
            mes = mes + 30
        
    if (mes == 1):
        mes = "Jan"
    elif (mes == 2):
        mes = "Feb"
    elif (mes == 3):
        mes = "Mar"
    elif (mes == 4):
        mes = "Abr"
    elif (mes == 5):
        mes = "May"
    elif (mes == 6):
        mes = "Jun"
    elif (mes == 7):
        mes = "Jul"
    elif (mes == 8):
        mes = "Aug"
    elif (mes == 9):
        mes = "Sep"
    elif (mes == 10):
        mes = "Oct"
    elif (mes == 11):
        mes = "Nov"
    else:
        mes = "Dic"
    fecha = mes + " " + str(dia)
    return fecha
    
def mongoCountPerCountry(countryCode):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"place.country_code": countryCode})
    return count

def mongoCountRetweetPerCountry(countryCode):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"$and": [{"rt.original_id": {"$ne":"None"}}, {"place.country_code": countryCode}]})
    return count

def mongoCountTweetConceptCountry(countryCode, concept):
    client = MongoClient('localhost', 27017)
    #Se extraen y cuentan los tweets (no retweets) que hablan de un concepto y son de un pais determinado
    db = client.politica
    fecha = fechaString()
    count = db.tweets.count({"$and": [{"place.country_code" : str(countryCode)}, {"rt.original_id": "None"}, {"text":{"$regex": str(concept)}}, {"created_at":{"$regex": fecha}}]})
    return count

def mongoCountRetweetConceptCountry(countryCode, concept):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = 0
    fecha = fechaString()
    retweets = db.tweets.find({"$and": [{"place.country_code" : str(countryCode)}, {"rt.original_id": {"$ne":"None"}},{"created_at":{"$regex": fecha}}]})
    #Se debe ver si el tweet original habla del concepto
    for retweet in retweets:
        if getOriginalTweet(retweet['tweet_id'], concept):
            count = count + 1
    return count

def mongoCountRetweetFromTo(originCode, destinyCode):
    client = MongoClient('localhost', 27017)
    db = client.politica
    #Primero se sacan todos los retweets originados en el pais "destiny" (destino)
    count = db.tweets.count({"$and":[{"place.country_code": destinyCode}, {"rt.original_id": {"$ne":"None"}}, {"rt.origin_countryCode": originCode}]})
    return count

def mongoCountRetweetFromToConcept(originCode, destinyCode, concept):
    #CORREGIR
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"$and":[{"place.country_code": destinyCode}, {"rt.original_id": {"$ne":"None"}}, {"rt.origin_countryCode": origin}, {"text": {"$regex" : concept}}]})
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
    sqlcursor.execute("SELECT Country.Code FROM Country")
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
    sqlcursor.execute("SELECT Keyword.word FROM Keyword ORDER BY Keyword.idKeyword")
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
        countryId = 1
        for pais in paises:
            tweets = mongoCountTweetConceptCountry(pais, keyword)
            retweets = mongoCountRetweetConceptCountry(pais, keyword)
            sqlcursor.execute("""INSERT INTO CountryStat (RetweetsCount, TweetsCount, Country, Keyword) VALUES (%s, %s, %s, %s)""", (retweets, tweets, countryId, keywordId))
            mysqldatabase.commit()
            countryId = countryId + 1
        keywordId = keywordId + 1
    return 0

def makeInfluenceResume():
    #Se inicializa la conexión a la base de datos
    #Se crea el conector de SQL
    mysqldatabase = MySQLdb.connect(host="localhost",    # your host, usually localhost
                         user="root",         # your username
                         passwd="root",  # your password
                         db="WW3App")
    #Se crea el cursor para ingresar datos
    #Se obtienen los codigos de paises
    sqlcursor = mysqldatabase.cursor()
    sqlcursor.execute("SELECT Country.Code FROM Country")
    codes = sqlcursor.fetchall()
    codigos=[]
    for i in codes:
        codigos.append(i[0])
    idOrigin = 1
    for origin in codigos:
        idDestiny = 1
        for destiny in codigos:
            tweets = mongoCountRetweetFromTo(origin, destiny)
            if(1>tweets):
                print "No hay influencia entre ", origin, destiny
            else:
                sqlcursor.execute("""REPLACE INTO Influence (origin, destiny, tweetsqty) VALUES (%s, %s, %s)""",(idOrigin, idDestiny, tweets))
                mysqldatabase.commit()
            idDestiny = idDestiny + 1
        idOrigin = idOrigin + 1


def getOriginalTweet(tweetid, concept):
    global client
    db = client.politica
    tdb = db.tweets.find_one({"tweet_id": tweetid})
    if tdb is None:
        #implica que no está el tweet original
        return False
    elif ((tdb['text']).find(concept) != -1):
        return True
    elif not(str(tdb['rt']['original_id']) is None):
        return getOriginalTweet(tdb['rt']['original_id'], concept)
    else:
        tweet = db.tweets.find({"$and": [ {"text" : {"$regex" : concept}}, {"tweet_id" : tweetid}]})
        if tweet[0] is not None:
            return True
        else:
            return False

client = MongoClient('localhost', 27017)
makeCountResume()
makeInfluenceResume()