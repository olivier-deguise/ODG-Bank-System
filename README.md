# Application ODG Bank System

![example workflow](https://github.com/olivier-deguise/ODG-Bank-System/actions/workflows/pipeline.yml/badge.svg)
![Docker Cloud Build Status](https://img.shields.io/docker/cloud/build/odg18/bank)
[![codecov](https://codecov.io/gh/olivier-deguise/ODG-Bank-System/branch/master/graph/badge.svg?token=MPDJS15838)](https://codecov.io/gh/olivier-deguise/ODG-Bank-System)
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=olivier-deguise_ODG-Bank-System)](https://sonarcloud.io/summary/new_code?id=olivier-deguise_ODG-Bank-System)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


L'application ODG Bank System est une application de test pour démontrer l'utilisation de différentes technologies.
Elle combine les technologies: Java, Springboot, Spring Data JPA, et les bases de données H2 et Postgresql.
De plus, l'application utilise les "GitHub actions" pour lancer un pipeline CI/CD. Plus précisément, les 
"Github actions" vont compiler le code source, lancer les tests unitaires et d'intégration, construire et publier
l'image Docker et la déployer sur le PaaS de Heroku.

L'application est divisée en 3 modules: Bank, employee et client

## Procédure de lancement
Il existe 3 façons de lancer le logiciel:

- **En local sur une BD H2**:   

    À la racine de l'application, faire la commande
`mvn clean install -Plocal` pour construire les jars appropriés. 
Ensuite lancer la banque de la façon suivante : `mvn -f Bank/pom.xml spring-boot:run -Plocal`.
La base de données H2 sera disponble à l'adresse suivante: http://localhost:9900/bank-system/h2-console/. 
Ensuite, toujours à la racine, les exécutables employee et client pourront être utilisé. Par exemple,
la commande `employe --add testuser` pourra être utilisé pour ajouter un client dans la base de données
H2 en mémoire.


- **En local sur une BD Postgresql avec un docker-compose**:
  
    À la racine de l'application, faire la commande
`mvn clean install -Pdockercompose` pour construire les jars appropriés. Ensuite lancer la banque 
de la façon suivante : `docker compose up -d`. PGAdmin pourra être utilisé à l'adresse
suivante http://localhost:5051/. Pour se connecter à la base de données à partir de PGAdmin, créer
un nouveau serveur et dans l'onglet connection remplissez les champs suivants: Host=postgres-bank, port=5432, maintenance database=bank, 
username=odg18, password=password. Ensuite, toujours à la racine, les exécutables employee et 
client pourront être utilisés. Par exemple, la commande `employe --add testuser` pourra être utilisé 
pour ajouter un client dans la base de données Postgresql en local. Pour fermer le docker compose, 
faire la commande `docker compose down`. 


- **Sur la BD de Heroku**:

    À la racine de l'application, faire la commande `mvn clean install -Pheroku` pour 
construire les jars appropriés. Le docker compose pourra être utilisé pour utilisé le pgadmin.
On pourra se connecter à la BD en remplissant les champs suivants: Host=52.71.69.66, port=5432, maintenance database=ddn0otd9vhpaof,
username=lvsftbbwjjwmde, password=(voir fichier de config dans la banque avec profile heroku).
Ensuite, toujours à la racine, les exécutables employee et
client pourront être utilisés. Par exemple, la commande `employe --add testuser` pourra être utilisé
pour ajouter un client dans la base de données Postgresql sur Heroku.

