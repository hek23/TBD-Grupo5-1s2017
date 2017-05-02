sudo apt-get autoremove -y mongo
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6
echo "deb [ arch=amd64,arm64 ] http://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list
sudo apt-get -y update
sudo apt-get install -y mongodb-org
#SERVIDOR NGINX PARA REDIRECCION Y PUERTOS
sudo apt-get update
sudo apt-get install -y nginx
sudo ufw allow 'Nginx HTTP'
sudo systemctl stop nginx
sudo systemctl start nginx
sudo apt-get -y install python-pip python-dev build-essential
sudo pip install --upgrade pip
#LIBRERIAS DE PYTHON PARA EJECUCIÖN DE CODIGO STREAM
pip install pymongo tweepy googlemaps
