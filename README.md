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
\i create.sh
\i error.sh
\i select.sh
\i delete.sh

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

## CONSIGNES

Ajout d'un bouton de gestion d'un groupe avec : 
- Ajouter/Supprimer un membre du groupe
- Changer l'admin du groupe
- Pouvoir changer les infos ou supprimer le groupe
- Permettre la gestion des posts du groupe pour l'admin

Ajout de la gestion des posts pour un user :
- Permettre la gestion de ses abonnements (+ Affichage "Ajouter/Supprimer")
- Gestion des posts (à durée limité et non)
- Permettre le delete de ses posts

Gestion des affichages :
- Permettre la gestion de ses likes (+ Affichage Like)
- Permettre la gestion de ses favoris (+ Affiche "Retirer Favori")
- Vérifier tous les pages pour le user

DAO/DTO :
Vérifier si les "SELECT *" sont obligatoires dans le DAO

Gestion Admin :
- Avoir une page extra nommé "ADMIN" permettant de modifier toute la base de données à sa guise.

Rectifier les classes :
- Simplifier les requêtes
- Optimiser la base de données

Base de données :
- Créer un utilisateur bidon avant l'admin
- Configurer 2 scripts : Un script pour remplir au minimum / un script pour remplir au maximum

API :
- Mettre en parallèle un Service Web permettant en GET de récupérer en JSON les fils du membre identifié, les messages postés sur ces fils. (/api/...)

Amélioration de l'interface avec BootStrap

Réalisez en Markdown une documentation de votre application contenant au minimum :
- La description générale de l’application
- Le MCD et le MDL réalisés
- Les requêtes pertinentes de la partie1 avec leurs significations
- L’arborescence globale de l’application
- La liste des entrées du/des controleurs avec leur fonctionalité
- Les points techniques difficiles et comment ils ont été réglés.