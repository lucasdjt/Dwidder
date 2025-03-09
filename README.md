# UND

## Introduction

Projet réalisé dans le cadre de la **SAE 4.02.01 - Développement d'une application**

## Description

Ce projet est un réseau social où les utilisateurs d'un même domaine peuvent s'inscrire, se connecter, publier des messages, commenter et interagir avec d'autres utilisateurs. 

L'objectif est de créer une plateforme conviviale où chacun peuvent communiquer de leurs passions communes et échanger avec d'autres membres dans le même thème.

## Fonctionnalités principales

- **Inscription/Connexion** : Les utilisateurs peuvent créer un compte et se connecter pour accéder à leurs informations personnelles.
- **Fil d'actualités** : Affichage des publications des utilisateurs suivis dans un flux chronologique.
- **Publications** : Les utilisateurs peuvent publier des textes, des photos ou des vidéos.
- **Commentaires** : Possibilité de commenter sur les publications d'autres utilisateurs.
- **Profil utilisateur** : Chaque utilisateur possède un profil personnalisé où il peut gérer ses informations et ses publications.

## Accès au site

Rien de plus simple, vous avez juste à accéder au lien qui est disponible sur mon Portfolio ou ci-dessous :  \
[Lien indisponible]

## Technos

- J2EE
- JDBC
- Java
- PostgreSQL
- Bootstrap
- JSP
- Architecture MVC
- Tomcat
- JSON

---

**__Auteur :__** Lucas De Jesus Teixeira

**Tout droits réservés**

## COMMANDES POUR MOI 

### WINDOWS
- dans Annexe/SQL : 
pg_ctl -D "C:\Program Files\PostgreSQL\17\data" restart
psql -U postgres -d reseau_social

- dans classes : 
javac -cp ".;../../../../lib/servlet-api.jar;../../../../lib" ../src/modele/*/*.java -d .
javac -cp ".;../../../../lib/servlet-api.jar;../../../../lib" ../src/controleur/*.java -d .

- dans le répertoire BIN : 
.\startup.bat

### LINUX
- dans Annexe/SQL : 
psql -h psqlserv -U lucasdejesusteixeiraetu but2
\i create.sh
\i error.sh
\i select.sh
\i delete.sh

- dans classes : 
javac ../src/modele/*/*.java -d .
javac ../src/controleur/*.java -d .

- dans le répertoire BIN : 
./startup.sh
./catalina.sh run
killall java

## ETAPES

Réalisation des différents JSP pour créer un affichage sans les éléments du modèles et voir où les servlet vont se placer à chaque fois:
- Ajout du JSP "Page principale"
- Ajout du JSP "Connexion"
- Ajout du JSP "Création de Compte"
- Ajout du JSP "Posts"
- Ajout du JSP "Profil"
- Ajout du JSP "Messages privés"
- Ajout du JSP "Paramètres"
- Ajout du CSS pour l'affichage de tous les JSP
V1.0

Ajout des servlets :
- ServletConnexion
- ServletCreatCompte
- ServletPosts
- ServletProfil
- ServletMessages
- ServletParamètres
- ServletPrincipale
V2.0

Ajout du back-end Servlet :
- Connexion de la Servlet entre Connexion <--> ServletConnexion
- Connexion de la Servlet entre Création de Compte <--> ServletCreatCompte
- Connexion de la Servlet entre Posts <--> ServletPosts
- Connexion de la Servlet entre Profil <--> ServletProfil
- Connexion de la Servlet entre Messages privés <--> ServletMessages
- Connexion de la Servlet entre Paramètres <--> ServletParamètres
- Connexion de la Servlet entre Page Principale <--> ServletPrincipale

Adaptation des Servlets <--> JSP
V3.0

Amélioration avec bootstrap
Ajouts bonus
V4.0