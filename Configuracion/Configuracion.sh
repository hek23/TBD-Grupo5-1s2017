#!/bin/bash
sudo service mongod restart
python TwitterCollector.py > LogCollector.log &
python TwitterFilter.py > LogFilter.log &
cp TwitterMySQLResume.py /home/$USER/TwitterMySQLResume.py
mysql --user='root' --password='root' < BasesdeDatos.sql
chmod a+x WW3SQLResume.sh
sudo cp WW3SQLResume.sh /etc/cron.daily/WW3SQLResume.sh
sudo chmod a+x /etc/cron.daily/WW3SQLResume.sh
