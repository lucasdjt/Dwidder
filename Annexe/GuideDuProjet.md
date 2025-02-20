## MLD final du Projet
Users (*uid, id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, dateinsc, datenaiss, loca, sexe, num_tel, langue, admin, [cover], [private], [verified])
Post (*pid, #uid, #gid, #pid_parent, contenu, media, date_pub, duree_post, [privacy])
Groupes (*gid, #uid_admin, #pid_epingle, nom_grp, description, date_creation, [grp_private]) // Fils de discussion
Conversations (*cid, #uid_envoyeur, #uid_receveur)
MessPrv (*mid, #cid, contenu, date_mess, [supprime], [lu], [duree_mess])
Abonnements (*(#uid_abonne, #uid_abonnement), date_abonnement, [validation])
Favoris (*(#uid, #pid), date_favori)
Reactions (*(#uid, #pid), type, date_react)
Mbr_Groupes (*(#uid, #gid), date_join, [role])
[Paramètres (*#uid, email_recup, a2f, notif_email, notif_push)]

## Utilisation du site
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
x Privacy
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
x Bouton "Changer de Privacy"
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
### Autres ajouts
- Droits Admin
- Pas possible de injection SQL...
- Réinitialiser MDP