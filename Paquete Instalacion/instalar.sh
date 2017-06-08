#!/bin/bash
# Instalar MiaMiFX

mysql -u root < DB.sql
mysql -u root -e "CREATE USER 'danza'@'localhost' IDENTIFIED BY 'danza';"
mysql -u root -e "GRANT ALL PRIVILEGES ON danza . * TO 'danza'@'localhost';"
mysql -u root -e "FLUSH PRIVILEGES;"