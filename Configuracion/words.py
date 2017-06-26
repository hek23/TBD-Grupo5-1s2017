import MySQLdb
from datetime import datetime
import random
mysqldatabase = MySQLdb.connect(host="localhost",    # your host, usually localhost
                         user="root",         # your username
                         passwd="root",  # your password
                         db="WW3App")
sqlcursor = mysqldatabase.cursor()
#Se crea el cursor para ingresar datos
'''palabras = open("words.dat", "r")
contadorConcepto = 1
for concepto in palabras.readlines():
    concepto = concepto.strip()
    sqlcursor.execute("""INSERT INTO Keyword (word, creator) VALUES (%s, %s)""", (concepto, "1"))
    mysqldatabase.commit()
    
    archivo = open(concepto, "r")
    for linea in archivo.readlines():
        linea = linea.strip()
        sqlcursor.execute("""INSERT INTO Sinonimos (sinonimo, concepto) VALUES (%s, %s)""", (linea, contadorConcepto))
        mysqldatabase.commit()
        print 1
    contadorConcepto = contadorConcepto + 1


#Ahora se llena la tabla de stats de paises
for idConcepto in range (3,9):
    for dia in range(1,32):
        for pais in range (1,240):
            fecha= datetime(2017,5,dia).date().isoformat()
            #Se calcula un numero aleatorio entero entre 20 y 500
            retweets = random.randint(20,500)
            tweets = random.randint(50,1000)
            sqlcursor.execute("""INSERT INTO CountryStat (RetweetsCount, TweetsCount, Country, Keyword, Date) VALUES (%s, %s, %s, %s, %s)""", (retweets, tweets, pais, idConcepto, fecha))
            mysqldatabase.commit()
'''

sqlcursor.execute("SELECT * FROM WW3App.CountryResume")
paises=sqlcursor.fetchall()
#Se pasa a lista
paisesfiltrados=[]
for consulta in paises:
    pais = []
    pais.append(consulta[0])
    pais.append(int(consulta[1]))
    pais.append(int(consulta[2]))
    paisesfiltrados.append(pais)

contadorOrigen = 0
while contadorOrigen<len(paisesfiltrados):
    while paisesfiltrados[contadorOrigen][1]>50 and random.randint(1,11)>5:
        print "while"
        #Se elige un pais aleatorio
        idDestino = random.randint(0,238)
        destino = paisesfiltrados[idDestino]
        if(destino[2]>0 and paisesfiltrados[contadorOrigen][1]>destino[2]):
            print "if"
            rts = random.randint(1,100) * destino[2] /100
            paisesfiltrados[idDestino][2] = destino[2] - rts
            paisesfiltrados[contadorOrigen][1] = paisesfiltrados[contadorOrigen][1] - rts
            sqlcursor.execute("""INSERT INTO Influence (origin, destiny, tweetsqty) VALUES (%s, %s, %s)""", (str(contadorOrigen+1), str(idDestino+1), str(rts)))
            mysqldatabase.commit()
    contadorOrigen = contadorOrigen + 1

