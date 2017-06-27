# TBD-Grupo5-1s2017
Proyecto de Taller de Base de Datos, consistente en una aplicación con múltiples tecnologías que permita análisis de información obtenida en Tweeter

## Pasos iniciales

Existen dos maneras para obtener los archivos necesarios:
- Clonar el repositorio (``` git clone https://github.com/hek23/TBD-Grupo5-1s2017.git ``` )

- O descargar los archivos necesarios (solo los .sh).

### Prerequisitos

Para la instalación de herramientas y ejecución de la aplicación, se recomienda:

- Ubnutu 16.04 (64 bits)
- 4 GB Ram
- 15 GB Disco duro
- Archivos .sh

### Instalación

Luego de localizar el archivo en terminal, se debe dar permisos de ejecución con el comando

```
chmod +X InstalacionRequerimientosServer.sh
```

Y luego se debe ejecutar el script con

```
sh InstalacionRequerimientosServer.sh
```

Se debe estar vigilando, dado que solicitará datos como la contraseña de superusuario y opciones de configuracion
Con esto puede ejecutar las aplicaciones que permiten el funcionamiento de la aplicación. Para esto debemos entrar a la carpeta Configuracion

### Creación del esquema de base de datos SQL
Ya con la ejecución de lo anterior debemos crear el esquema. Esto podemos hacerlo ubicandonos en el directorio Configuracion y ejecutando estos comandos
```
#Entramos a MySQL
mysql -u root -p
Password: [Inserta tu password]
source Basededatos.sql
exit
```

### Ejecución del Backend

Primero se debe ejecutar el script llamado TwitterCollector, ubicado en la carpeta "Configuración". En el ejemplo se guarda toda la salida del archivo (impresiones en pantalla) en un archivo log y se ejecuta como proceso en background
```
python Configuracion/TwitterCollector.py > log &
```
Luego, se ejecuta el filtro paralelamente
```
python Configuracion/TwitterFilter.py > logFilter &
```
Con esto ya se están recabando datos.

### Confección de resúmenes SQL y Grafos
Una vez al dia, se deben ejecutar se forma secuencial dos aplicaciones más, las que permiten la generación y el paso de datos en MySQL y Neo4J respectivamente. Esto se hace con lo siguiente:

```
python TwitterMySQL.py
java -jar Neo4J.jar
```
Con esto la aplicación puede funcionar sin problemas. 

# Confección de la aplicacion

## Lenguajes utilizados
* [Python 2.7](https://www.python.org/) - Lenguaje de programación para servicio de Streaming de Twitter.
* [Java](https://www.java.com/en/) - Lenguaje de programación orientado a objetos para paso de MySQL a Neo4J

## Manejadores de librerías/dependencias utilizados
* [PIP](https://pypi.python.org/pypi/pip) - Manager para librerías de Python.

## Frameworks utilizados
* [JavaEE](http://www.oracle.com/technetwork/java/javaee/downloads/index.html) - Framework de Java para aplicaciones Web.

## API Utilizadas
* [GoogleMapsAPI](https://developers.google.com/maps/?hl=es-419) - Consultas geolocalizadas.
* [TwitterAPI](https://dev.twitter.com/rest/public) - Extractor de información (Tweets).

## Servidores utilizadas
* [Glassfish](http://www.oracle.com/technetwork/java/javaee/downloads/index.html) - Servidor de aplicaciones para JavaEE.

## Bases de datos utilizadas
* [MongoDB](https://www.mongodb.com/es) - Motor de base de datos no relacional.
* [MySQL](https://www.mysql.com/) - Motor de base de datos relacional.
* [Neo4J](http://www.neo4j.com/) - Motor de base de datos orientada a grafos.

## Versiones

Las versiones públicas de scripts de instalación y aplicación se liberan utilizando [GitHub](http://github.com/). Para ver las versiones liberadas, [visite los tags de este repositorio](https://github.com/hek23/TBD-Grupo5-1s2017/tags).

## Autores

* **Héctor Fuentealba** - [hek23](https://github.com/hek23)

##Agradecimientos
* A mis amigos de "La Mesa", quienes me apoyaron y levantaron cada vez que todo se veia negro <3 .

## License

Este proyecto está bajo al licencia GPL3.0. Vea el archivo [LICENSE.md](LICENSE.md) para mayor detalle.
