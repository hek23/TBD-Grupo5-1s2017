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

def mongoCountPerCountry(countryName):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"place.country": countryName})
    return count

def mongoCountRetweetPerCountry(countryName):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({"original_id": {$ne:"None"}})
    return count

def mongoCountTweetsConceptCountry(countryName, concept):
    client = MongoClient('localhost', 27017)
    db = client.politica
    count = db.tweets.count({$and: [{"text": {$regex : concept}}, {"place.country" : countryName}]})
    return count

def mongoCountRetweetFromTo(origin, destiny):

def mongoCountRetweetFromToConcept(origin, destiny, concept):
