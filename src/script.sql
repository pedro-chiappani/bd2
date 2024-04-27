CREATE USER 'dati'@'localhost' IDENTIFIED BY 'dati';

CREATE DATABASE bd2_tours_1;

GRANT ALL PRIVILEGES ON bd2_tours_1.* TO 'dati'@'localhost';

FLUSH PRIVILEGES;