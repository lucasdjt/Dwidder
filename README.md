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
javac -cp ".;../../../../lib/servlet-api.jar;../../../../lib" ../src/utils/*.java -d .
javac -cp ".;../../../../lib/servlet-api.jar;../../../../lib" ../src/modele/*/*.java -d .
javac -cp ".;../../../../lib/servlet-api.jar;../../../../lib" ../src/controleur/*.java -d .

- dans le répertoire BIN : 
.\startup.bat

### LINUX
- dans Annexe/SQL : 
psql -h psqlserv -U lucasdejesusteixeiraetu but2
\i setup.sql

- dans classes :
./remove.sh
./compile.sh

- dans le répertoire BIN : 
./startup.sh
./catalina.sh run
killall java

### Points techniques qui ont été difficile à garder pour le README.md

- LocalDateTime, Interval et Duration
- sendRedirect qd c une servlet /element/*
- Gestion des messages et conversations

### OPTIMISATION

GroupeServlet : "groupe/*" :
- GroupeServlet "/groupes/*" doGet ==> /groupe
- ChgGrpServlet "/chgGroupe/*" doGet doPost ==> /groupe/edit
- DeleteGroupeServlet "/delGroupe" doGet ==> /groupe/delete
- AddGroupeServlet "/addGroupe" doGet doPost ==> /groupe/add
- Memberservlets "/member" doGet doPost ==> /groupe/members

FavoriServlet "/favori/*" :
- AddFavoriServlet "/addFavori" doGet ==> /favori/add
- FavorisServlet "/favoris" doGet ==> /favori

ReactionServlet "/reaction/*" :
- AddLikeServlet "/addReaction" doGet ==> /reaction/add
- LikeServlet "/reactions/*" doGet ==> /reaction

AbonnementServlet "/follow/*" :
- AddFollowServlet "/addFollow" doGet ==> /follow/add
- FollowerServlet "/followers/*" doGet ==> /follow/followers
- FollowServlet "/follows/*" doGet ==> /follow/follow

PostServlet : "post/*" :
DeletePostServlet "/delPost/*" doGet ==> /post/delete
PostServlet "/posts/*" doGet doPost ==> /post && /post/add

MessageServlet : "messages/*"
MessageServlet "/messages/*" doGet doPost ==> /message