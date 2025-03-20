# DWIDDER - Réseau Social en JEE

![Image de DWIDDER](img/DwidderBanner.png)

## Informations
- **GitHub** : [https://github.com/lucasdjt](https://github.com/lucasdjt)
- **Vidéo de présentation du projet** : [Lien YouTube](https://youtu.be/yf-XB1rcDR0)
- **Email** : lucasdjtpro@gmail.com
- **LINKEDIN** : [lucasdjt](https://www.linkedin.com/in/lucasdjt/)
- **Nom** : Lucas DE JESUS TEIXEIRA

## SOMMAIRE
1. [Description du projet](#description-du-projet)
2. [Fonctionnalités](#fonctionnalités)
3. [Technologies Utilisées](#technologies-utilisées)
4. [Installation et configuration](#installation-et-configuration)
5. [Guide du projet](#guide-du-projet)
6. [Structure du projet](#structure-du-projet)
7. [Base de données](#base-de-données)
8. [Points techniques difficiles](#points-techniques-difficiles)
9. [Crédits](#crédits)

---

## Description du Projet

> L'objectif initial de ce SAE est de développer une "petite application web de réseau social", inspirée d'applications comme Discord, TikTok ou WhatsApp. Cette application devra permettre aux utilisateurs de publier des messages dans différents fils de discussion et de consulter ceux des autres.

Lorsqu'il est question de fils publics, deux réseaux sociaux viennent immédiatement à l'esprit : Twitter (désormais appelé X) et Facebook, deux leaders dans ce domaine.  
**Dwidder** est un réseau social développé en JavaEE dans le cadre de la **SAE401 - Développement d'une application**. L'objectif est de combiner les fonctionnalités de Twitter et Facebook en une seule application. (Bien sûr, compte tenu du temps imparti, ce projet ne prétend pas rivaliser avec ces géants).  

L'application permettra notamment d'afficher des fils publics et privés, de commenter les publications des autres utilisateurs et de consulter leurs profils.  
En résumé, de nombreuses fonctionnalités seront proposées. Cependant, plutôt que de vous fier uniquement à ce texte, je vous encourage à tester l'application par vous-même.
Pratique > Théorique.

---

## Fonctionnalités

- **Authentification** : Il est possible de créer un compte et de s'authentifier.  
- **Fils** : On peut créer un fil public, visible par tous les utilisateurs, ou un fil privé (groupe). Les utilisateurs peuvent répondre aux publications dans ces fils ainsi qu'aux réponses associées.  
- **Messages privés** : Une fonction de chat privé permet de discuter en tête-à-tête avec une autre personne.  
- **Interactions avec les posts** : Les utilisateurs peuvent consulter les réactions à leurs publications, les supprimer ou même effacer leurs propres posts. Il est également possible d'ajouter une image à une publication ou de définir une durée de vie pour celle-ci.  
- **Abonnements** : Les utilisateurs peuvent s'abonner à d'autres comptes ou être suivis, ce qui permet de gagner en popularité au sein du réseau social.  
- **Profil** : Chaque utilisateur peut consulter son propre profil ou celui d'autres membres, ainsi que les publications qu'ils ont mises en favori.  
- **Administration** : L'administrateur dispose d'un accès aux logs du réseau social, lui permettant de suivre les actions des utilisateurs et de supprimer des comptes si nécessaire.  
- **API** : Une API REST est intégrée dans l'application, offrant des possibilités d'utilisation externe.

---

## Technologies Utilisées

- **Front-end** : HTML, CSS, JSP, Bootstrap  
- **Back-end** : JEE, JDBC, PostgreSQL, API REST  
- **Serveur** : Apache Tomcat  
- **Architecture** : MVC  

---

## Installation et Configuration

### Pré-requis

- **Java JDK 8+**  
- **Apache Tomcat v10+**  
- **PostgreSQL**  

---

### Installation

1. **Cloner le projet**  
   Cloner le dossier `Dwidder` dans le répertoire `webapps` de votre Tomcat.  
   ```bash
   git clone https://github.com/lucasdjt/Dwidder.git
   cd Dwidder
   ```

2. **Configurer la base de données**  
   - Lancez votre serveur PostgreSQL et exécutez le fichier `setup.sql` (situé dans le répertoire `WEB-INF`).  
   - Ajustez les fichiers `config.prop` situés dans `WEB-INF/classes/utils/` et `WEB-INF/src/utils/` et la méthode `configProp du fichier DS.java` pour réussir la connexion avec votre base de données.
   - Vous avez un disposition une classe Test.java dans `WEB-INF/classes/utils/` pour vérifier que tout est bien configuré. (Ajoutez dans votre CLASSPATH `tomcat/lib/postgres.jar` pour que la classe n'échoue pas)

3. **Déployer sur Tomcat**   
   - Compilez les fichiers avec les scripts de configuration fournis (`remove` et `compile`) situés dans `WEB-INF/classes`.
   - Démarrez le serveur Tomcat. [Utilisez `startup` ou `catalina run` dans `tomcat/bin`]  
   - Accédez au lien [http://localhost:8080/Dwidder](http://localhost:8080/Dwidder) et découvrez mon application !

---

### Utiliser l'API REST

L'application propose une API REST avec les fonctionnalités suivantes (en supposant un hôte par défaut : `localhost:8080`) :

- **Récupérer tous les posts publics**  
   ```bash
   curl -i -X GET http://localhost:8080/Dwidder/api/post
   ```

- **Récupérer un post spécifique**  
   ```bash
   curl -i -X GET http://localhost:8080/Dwidder/api/post/{pidDuPost} -u identifiant:mdp
   ```

- **Récupérer la liste de vos posts**  
   ```bash
   curl -i -X GET http://localhost:8080/Dwidder/api/user -u identifiant:mdp
   ```

- **Récupérer la liste des posts d'un groupe** (uniquement si l'utilisateur appartient au groupe)  
   ```bash
   curl -i -X GET http://localhost:8080/Dwidder/api/group/{gidDuGroupe} -u identifiant:mdp
   ```

## Guide du Projet

Vous pouvez trouver le guide du projet expliquant comment utiliser le projet depuis ce lien :

[Guide du projet](./GuideDuProjet.md)

---

## Structure du Projet

### Arborescence du Projet

```bash
|____.gitignore
|____index.html        # Page de redirection de / vers /connexion
|____README.md         # README du projet
|____.vscode/
|____Annexe/           # Contient tous les éléments annexes du projet
|____css/
| |____style.css
|____img/
| |____logo.ico        # Favicon du site
| |____pdp.png         # Photo de profil par défaut
| |____TexteLogo.png   # Logo Dwidder
| |____MCD.png         # MCD du projet
|____imgStock/         # Stockage des images téléversées sur le site
|____META-INF/
| |____context.xml
|____WEB-INF/
| |____classes/
| | |____compile.bat   # Script de compilation pour Windows
| | |____compile.sh    # Script de compilation pour Linux
| | |____remove.bat    # Script de suppression pour Windows
| | |____remove.sh     # Script de suppression pour Linux
| | |____utils/
| | | |____config.prop # Fichier de configuration pour la connexion BDD
| |____setup.sql       # Script de configuration d'une BDD normale de départ
| |____setupBigBDD.sql # Script de configuration pour une grosse BDD
| |____src/
| | |____controleur/   # Contient toutes les Servlets du projet
| | |____modele/
| | | |____dao/        # Les DAO (Data Access Objects)
| | | |____dto/        # Les DTO/POJO
| | |____test/         # Répertoire pour tests temporaires (tags V1, V1.1)
| | |____utils/        # Répertoire des outils du projet
| |____vue/            # Répertoire contenant toutes les vues de l'application
```

---

### Liste des Entrées des Contrôleurs

Voici les endpoints disponibles et leur utilité :

- **`/follow/*`** : Gestion des abonnements  
  - `/follow/{uid}` : Affichage des abonnements d’un utilisateur.  
  - `/follower/{uid}` : Affichage des abonnés d’un utilisateur.  
  - `/addFollow/{uid}` : Ajouter ou supprimer un abonnement à cet utilisateur.  
  - `/addFollowers/{uid}` : Gérer l’abonnement de cet utilisateur à moi.  

- **`/accueil`** : Page d’accueil  
  - Affiche la page principale pour les utilisateurs connectés.  

- **`/admin`** : Page d’administration (Accès restreint à l’administrateur uniquement)  
  - `POST` : Supprime un utilisateur via l’interface d’administration.  

- **`/api/*`** : API REST de l’application  
  - `/post` : Récupérer tous les posts publics.  
  - `/post/{pid}` : Récupérer un de vos posts en particulier.  
  - `/user` : Liste de vos posts.  
  - `/group/{gid}` : Posts d’un groupe spécifique (si vous en êtes membre).  

- **`/connexion`** : Gestion de la connexion  
  - Affiche la page de connexion.  
  - `POST` : Connexion d’un utilisateur.  

- **`/favori/*`** : Gestion des favoris  
  - `/` : Affiche vos favoris.  
  - `/change` : Change le statut d’un favori.  

- **`/groupe/*`** : Gestion des groupes  
  - `/` : Liste les groupes de l’utilisateur.  
  - `/delete` : Supprime un groupe.  
  - `/add` : Affiche la page de création d’un groupe.  
  - `/edit` : Affiche la page de modification d’un groupe.  
  - `/member` : Gère les membres du groupe (ajout/suppression).  
  - `POST /add` : Ajoute un groupe.  
  - `POST /edit` : Modifie un groupe existant.  
  - `/group/{gid}` : Affiche les posts d’un groupe spécifique.  

- **`/inscription`** : Gestion des inscriptions  
  - Affiche la page d’inscription.  
  - `POST` : Inscrit un nouvel utilisateur.  

- **`/message/*`** : Gestion des messages privés  
  - `/` : Affiche les personnes avec qui vous pouvez discuter.  
  - `/message/{idPseudo}` : Montre la conversation avec un utilisateur.  
  - `POST` : Envoie un message dans une conversation.  

- **`/post/*`** : Gestion des posts  
  - `/delete/{pid}` : Supprime un de vos posts.  
  - `/post/{pid}` : Affiche les réponses à un post spécifique.  
  - `POST` : Crée un nouveau post.  

- **`/reaction/*`** : Gestion des réactions  
  - `/add` : Ajoute ou met à jour une réaction.  
  - `/delete` : Supprime une réaction.  

- **`/user/*`** : Gestion des utilisateurs  
  - `/` : Liste des utilisateurs inscrits.  
  - `/user/{uid}` : Affiche le profil d’un utilisateur.  
  - `/user/delete` : Supprime votre compte.  
  - `/user/edit` : Affiche la page de modification de vos informations.  
  - `POST` : Enregistre les modifications de votre compte.  

---

## Base de données

[Partie SQL du Projet](PartieSQL.md)

---

## Points Techniques Difficiles

### **1ère difficulté : la gestion du temps**

La plus grande difficulté du projet, aussi bien sur le plan technique que personnel, a été la gestion du temps. Entre ma vie personnelle, ma vie professionnelle et ma vie scolaire, il était souvent complexe de jongler entre ces responsabilités. Ce projet ambitieux, bien qu’un peu trop optimiste au départ, a malgré tout été mené à bien.  

D’un point de vue technique, l'un des principaux défis a concerné la gestion des classes `Interval` et `Timestamp` de PostgreSQL, et leur conversion en types Java comme `Duration`, `LocalDateTime` ou `LocalDate`, qui sont plus pratiques à manipuler. Finalement, j'ai choisi de ne pas utiliser `Interval` et d’implémenter directement cette gestion en Java EE, en travaillant avec les dates de début et de fin d’un post.  

---

### **2ème difficulté : SendRedirect et getRequestDispatcher**

Bien que ce ne soit pas une difficulté majeure en soi, cela m’a bloqué un long moment et fait perdre beaucoup de temps. La gestion des redirections et dispatchers (`*`, appels en boucle, fichiers externes, JSP, servlets en cascade) s'est avérée particulièrement complexe.  

Avec de la persévérance et de nombreuses expérimentations, j’ai fini par surmonter ces problèmes grâce aux outils `getHeader` et `getContextPath` des requêtes, qui m'ont permis de mieux structurer les chemins et les interactions entre les différentes couches.  

---

### **3ème difficulté : Gestion des messages et des conversations**

Enfin, un véritable casse-tête fut la gestion des conversations sans JavaScript. Ce projet, avec un minimum de JS autorisé, a rendu cette partie du développement particulièrement exigeante.  

Réaliser un système de messagerie fonctionnel impliquait plusieurs défis :  
- L’affichage dynamique avec Bootstrap.  
- La gestion des images, des contenus vides et des nouveaux utilisateurs.  
- Et surtout, des erreurs persistantes liées aux redirections et logiques, renvoyant toujours une même erreur : *« Vous ne pouvez pas discuter avec cet utilisateur. »*  

Malgré tout, à force de travail et d’itérations, ce composant a pu être finalisé et intégré.

---

## Crédits

Projet réalisé par **Lucas De Jesus Teixeira** dans le cadre de la **SAE 4.A02.1 - Développement d'une application**.

---

**Tous droits réservés**