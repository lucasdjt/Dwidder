# GUIDE DU PROJET
### Préface
- L'interface est adapté pour toutes plateformes Mobile/PC
### Page principale
L'utilisateur arrive sur la Page d'accueil du site
> Si il n'est pas connecté à un compte, le click sur tout bouton, actionne directement la Page de connexion
> Si il est connecté, toutes les sections de la page ont une action précise :
- Au Milieu , Affichage de {Section de Posts}
- En Bas a Gauche, Redirection vers {Page de Profil}
- En Bas a Droite, Redirection vers {Page de Messages Privés}
- En Haut a Gauche, Redirection vers {Page de Paramètres}
- En Haut a Droite, Redirection vers {Page de Connexion (Déconnexion/Connexion)}
### Page de Connexion
L'utilisateur arrive sur la page de connexion, 2 actions possibles
> L'utilisateur peut se connecter à son compte
- Il remplit ses différentes informations et se connecte :
x Pseudo/Email
x Mdp
x Appuyer sur le Bouton "Se Connecter"
> Il peut retourner sur la {Page Principale} en cliquant sur l'icône au dessus à gauche
### Page de Création de Compte
Pour s'inscrire il doit inscrire ses différentes informations :
x ID* (@draggas qui deviendra son uid)
x Pseudo*
x email*
x prenom
x nom de famille
x datenaiss
x loca
x sexe*
x langue*
x mdp*
x confirmation de mdp*
x Appuyer sur le Bouton "Créer un Compte"
Lorsque cela est fait,
- Il est redirigé vers la {Page de Connexion} si tout est bon
- Il reste sur la page avec un message d'alerte "Champs Manquants"
- Les Champs Manquants sont affichés en Rouge sur la page
> Il peut retourner sur la {Page de Connexion} en cliquant sur l'icône au dessus à gauche
### Section de Posts
L'utilisateur voit la section de posts depuis la {Page Principale}, il y voit 2 sections différentes :
> Le Fil d'actualité
- Il affiche tout les posts soit
x Du Plus Liké
x Du Plus Récent
- Chaque Post est affiché du genre :
x Auteur [Nombre de followers, Pdp]
x Groupe appartenant (si Grp)
x Contenu
x Media
x Date de publication
x Durée du post
x Nbr Réactions sur le post
x Bouton "Commenter"
x Bouton "Mettre en Favori"
x Bouton "Créer un post" qui affiche un formulaire avec "Contenu" "Média" "Durée du post" Bouton "Publier"
> Le Fil de Groupe
Il affiche une liste de groupes, avec un groupe sélectionné par défaut
Selon le groupe sélectionné, ça affiche :
- Le Nom du Groupe
- Le @user à qui appartient le groupe
- La Description
- La Date de Création
- La liste des membres du groupe
- Le Post épinglé du groupe
- La liste des posts
x Du Plus Liké
x Du Plus Récent
Avec le Post Epinglé affiché en 1er
Chaque Post est affiché du genre :
x Auteur [Nombre de followers, Pdp]
x Contenu
x Media
x Date de publication
x Durée du post
x Nbr Réactions sur le post
x Bouton "Reactions" / "Supprimer Reactions"
x Bouton "Commenter"
x Bouton "Mettre en Favori" / "Enlever des Favoris"
x Bouton "Créer un post" qui affiche un formulaire avec "Contenu" "Média" "Durée du post" Bouton "Publier"
### Page de Profil
L'utilisateur peut aller sur la page de profil depuis la {Page Principale}, il y voit plusieurs choses :
- Sa Photo de Profil
- Son Pseudo
- Sa bio
- Sa Loca
- Son Age
- Un Bouton "Modifier ses informations"
Affiche Un Formulaire avec :
x Pseudo
x Prenom
x Nom de Famille
x Bio
x Photo de Profil
x Date de Naissance
x Localisation
x Sexe
- Un Bouton "Abonnés"
Affiche :
x Le nombre d'abonnés
x Le Pseudo du followers avec un bouton "x" à côté qui permet de le supprimer
- Un Bouton "Abonnements"
x Le nombre d'abonnements
x Le Pseudo des follows avec un bouton "x" à côté qui permet de le supprimer
- Un Bouton "Groupes"
x Le nombre de groupes
x Le nom des groupes avec un bouton "x" à côté qui permet de le supprimer
- La Liste de Ses Posts
Chaque Post est affiché du genre :
x Groupe appartenant (si Grp)
x Contenu
x Media
x Date de publication
x Durée du post
x Nbr Réactions sur le post
x Bouton "Commenter"
x Bouton "Mettre en Favori"
x Bouton "Supprimer le Post"
### Page de Messages Privés
La Liste des conversations avec chaque personne (du plus récent au plus ancien)
et en cliquant sur la conversation on voit les messages (avec leur date)
### Page de Paramètres
En allant sur cette page on peut :
- changer l'id_pseudo
- l'email
- le mdp
- la langue
Ajouter/Changer numéro de tel
Supprimer son compte

# GUIDE SQL
## Les étapes du SQL
- Création des tables - CREATE TABLE
- Insertion des données - INSERT INTO
- Modification des données - UPDATE
- Supression des données - DELETE
- Récupération des données [Transformation en Vues pour Simplifier les requêtes] - SELECT
## Les Requêtes du SQL
### CREATE TABLE
1 - Users (*uid, id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, date_insc, date_naiss, loca, sexe, num_tel, langue, admin)
2 - Posts (*pid, #uid, #gid, #pid_parent, contenu, media, date_pub, duree_post)
3 - Groupes (*gid, #uid_admin, #pid_epingle, nom_grp, description, date_creation) // Fils de discussion
4 - Conversations (*cid, #uid_envoyeur, #uid_receveur)
5 - Messages (*mid, #cid, contenu, date_mess)
6 - Abonnements (*(#uid_abonne, #uid_abonnement), date_abonnement)
7 - Favoris (*(#uid, #pid), date_favori)
8 - Reactions (*(#uid, #pid), type, date_react)
9 - Membres (*(#uid, #gid), date_join)
### INSERT INTO
- 1 L'utilisateur peut se créer un compte avec id_pseudo*/pseudo*/prenom/nom_user/email*/mdp*/datenaiss/loca/num_tel/sexe*/langue*
- 2 L'utilisateur peut créer un post avec uid*/gid/pid_parent/contenu*/media/date_pub*/duree_post*
- 3 L'utilisateur peut créer un groupe avec uid_admin*/nom_grp*/description/date_creation*
- 4 L'utilisateur peut créer une conversation avec uid_envoyeur*/uid_receveur*
- 5 L'utilisateur peut créer un message privé avec cid*/contenu*/date_mess*
- 6 L'utilisateur peut ajouter un abonné avec uid_abonne*/uid_abonnement*/date_abonnement*
- 7 L'utilisateur peut ajouter un post dans ses favoris avec uid*/pid*/date_favori*
- 8 L'utilisateur peut ajouter une réaction à son post avec uid*/pid*/type*/date_react*
- 9 L'utilisateur peut ajouter un membre à son groupe avec uid*/gid*/date_join*
### UPDATE
- 1 L'utilisateur peut changer ses infos USERS
- 3 L'utilisateur peut changer ses infos GROUPE
### DELETE
- 1 L'utilisateur peut supprimer son compte
- 2 L'utilisateur peut supprimer son post
- 3 L'utilisateur peut supprimer son groupe
- 4 L'utilisateur peut supprimer sa conversation
- 5 L'utilisateur peut supprimer son message privé
- 6 L'utilisateur peut supprimer son abonné
- 7 L'utilisateur peut supprimer son favori
- 8 L'utilisateur peut supprimer sa réaction
- 9 L'utilisateur peut supprimer un membre à son groupe
### SELECT
- On peut obtenir les informations de l'utilisateur [Pour Se Connecter / Pour le Profil Public]
- On peut obtenir les posts d'un utilisateur
- On peut obtenir les posts trié par date la plus récente
- On peut obtenir les posts trié par le plus liké
- On peut obtenir les posts d'un groupe trié par date la plus récente
- On peut obtenir les posts d'un groupe trié par le plus liké
- On peut obtenir les réponses d'un post trié par le plus liké
- On peut obtenir les réponses d'un post trié par le plus récent
- On peut obtenir la liste des membres d'un groupe
- On peut obtenir la liste des groupes que nous avons rejoint
- On peut obtenir les infos d'un groupe
- On peut obtenir la liste des conversations d'un utilisateur
- On peut obtenir les messages privés d'une conversation trié par date
- On peut obtenir les abonnées d'un utilisateur
- On peut obtenir les abonnements d'un utilisateur
- On peut obtenir les favoris fait par un utilisateur
- On peut obtenir le nombre de posts fait par un utilisateur
- On peut obtenir le nombre de groupes rejoint par l'utilisateur
- On peut obtenir le nombre d'abonnés d'un utilisateur
- On peut obtenir le nombre d'abonnements d'un utilisateur
- On peut obtenir le nombre de réactions qu'il y a sur un post
- On peut obtenir le nombre de commentaires qu'il y a sur un post
- On peut obtenir le nombre de membres qu'il y a sur un groupe
- On peut obtenir le post épinglé du groupe

---

# AVANCEE
## MLD FINAL DU PROJET
Users --> Ajout cover, private, verified
Posts --> Ajout privacy
Groupes --> Ajout grp_private
Messages --> Ajout supprime, lu, duree_mess
Abonnements --> Ajout validation
Membres --> Ajout role
Ajout Paramètres (*#uid, email_recup, a2f, notif_email, notif_push)
## Autres ajouts
- Ajouts Interface Droits Admin
- Pas possible de injection SQL...
- Réinitialiser MDP