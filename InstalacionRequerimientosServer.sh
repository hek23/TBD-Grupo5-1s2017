cd ~
sudo apt-get update
sudo apt-get -y install git
sudo apt-get -y update
sudo apt-get -y install mysql-server
sudo apt-get -y install python-software-properties
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get -y update
sudo apt-get -y install oracle-java8-installer
cd ~
mkdir scripts
touch ~/scripts/javadir.d
cp ~/.profile ~/.profile_backup
echo 'JAVA_HOME="/usr/lib/jvm/java-8-oracle/jre"'> ~/scripts/javadir.d
cat ~/.profile_backup ~/scripts/javadir.d > ~/.profile
source ~/.profile
wget http://download.java.net/glassfish/4.0/release/glassfish-4.0.zip
sudo apt-get -y install unzip
sudo chown -R $USER /usr/local
unzip glassfish-4.0.zip -d /usr/local
touch ~/scripts/glassfishdir.d
cp ~/.profile ~/.profile_backup
echo 'PATH="/usr/local/glassfish4/glassfish/bin:$PATH"'> ~/scripts/glassfishdir.d
cat ~/.profile_backup ~/scripts/glassfishdir.d > ~/.profile
source ~/.profile
rm glassfish-4.0.zip
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6
echo "deb [ arch=amd64,arm64 ] http://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list
sudo apt-get -y update
sudo apt-get install -y mongodb-org
wget https://services.gradle.org/distributions/gradle-3.4.1-bin.zip
unzip gradle-3.4.1-bin.zip -d /usr/local
cd /usr/local
sudo ln -s gradle-3.4.1 gradle
touch ~/scripts/gradledir.d
cp ~/.profile ~/.profile_backup
echo 'GRADLE_HOME="/usr/local/gradle"'> ~/scripts/gradledir.d
cat ~/.profile_backup ~/scripts/gradledir.d > ~/.profile
source ~/.profile
cp ~/.profile ~/.profile_backup
echo 'PATH="$GRADLE_HOME/bin:$PATH"'> ~/scripts/gradledir.d
cat ~/.profile_backup ~/scripts/gradledir.d > ~/.profile
source ~/.profile
cd ~
rm gradle-3.4.1-bin.zip
sudo apt-get -y install maven
wget -O - http://debian.neo4j.org/neotechnology.gpg.key | sudo apt-key add -
echo 'echo 'deb http://debian.neo4j.org/repo stable/' > /etc/apt/sources.list.d/neo4j.list'> neo4j.sh
sudo sh neo4j.sh
sudo apt-get -y update
sudo apt-get -y install neo4j
sudo service neo4j status
sudo apt-get -y update
sudo apt-get -y install build-essential libssl-dev curl git-core
sudo apt-get -y install python-pip python-dev build-essential
sudo pip install --upgrade pip
sudo apt-get install xz-utils
cd ~
wget https://nodejs.org/dist/v6.10.1/node-v6.10.1-linux-x64.tar.xz
tar -Jxvf node-v6.10.1-linux-x64.tar.xz
mv ~/node-v6.10.1-linux-x64 /usr/local/node-v6.10.1-linux-x64
rm node-v6.10.1-linux-x64.tar.xz
sudo chown -R $USER /usr/local
touch ~/scripts/nodedir.d
cp ~/.profile ~/.profile_backup
echo 'NODE_HOME="/usr/local/node-v6.10.1-linux-x64"'> ~/scripts/javadir.d
cat ~/.profile_backup ~/scripts/javadir.d > ~/.profile
source ~/.profile
cp ~/.profile ~/.profile_backup
echo 'PATH="$NODE_HOME/bin:$PATH"'> ~/scripts/javadir.d
cat ~/.profile_backup ~/scripts/javadir.d > ~/.profile
source ~/.profile
sudo chown -R $USER /usr/local
cd ~
wget http://www-us.apache.org/dist/lucene/java/6.5.0/lucene-6.5.0.zip
unzip lucene-6.5.0.zip
rm lucene-6.5.0.zip
cd ~/lucene-6.5.0/core
mv lucene-core-6.5.0.jar /usr/local/glassfish4/glassfish/lib/lucene-core-6.5.0.jar
cd ~
rm -rf lucene-6.5.0
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.41.zip
unzip mysql-connector-java-5.1.41.zip
rm ~/mysql-connector-java-5.1.41.zip
cd ~/mysql-connector-java-5.1.41
mv mysql-connector-java-5.1.41-bin.jar /usr/local/glassfish4/glassfish/lib/mysql-connector-java-5.1.41-bin.jar
cd ~
rm -rf mysql-connector-java-5.1.41
sudo apt-get -y install libctemplate2v5 liblua5.1-0 python-paramiko python-pysqlite2
sudo apt-get -y -f install
sudo apt-get -y install mysql-workbench
rm -rf ~/scripts
source ~/.profile

#LIBRERIAS PARA PROYECTO, NO ES NECESARIO INSTALAS DE INMEDIATO
#SI NO QUE CUANDO SE TENGA LISTA LA APP O PROYECTO
npm install -g generator-angular-fullstack
npm install npm@latest -g
npm install --global gulp-cli
npm install requirejs
npm install -g yo
npm install angular-ui-router
npm install angular@1.6.3
npm install d3
source ~/.profile

#LIBRERIAS DE PYTHON PARA EJECUCIÖN DE CODIGO STREAM
pip install pymongo tweepy googlemaps

###########################################################PARTE MANUAL#######################################################################

#CONFIGURANDO MYSQL AL INICIO
sudo mysql_secure_installation
##OJO:
#PASS PARA ROOT: root (con minusculas), o la pass que hayas puesto al inicio
#VALIDATE PLUGIN: NO
#CHANGE PASSWORD FOR ROOT..: NO
#REMOVE ANNONYMUS USERS: YES
#DISALLOW LOGIN REMOTELY: NO
#REMOVE TEST DATABASE: YES
#RELOAD PRIVILEGE TABLES: YES

########################################################FIN PARTE MANUAL#####################################################################
