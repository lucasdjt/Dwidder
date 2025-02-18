## MLD du Projet

Users (*uid, pseudo, prenom, nom_user, email, mdp, bio, pdp, cover, dateinsc, datenaiss, loca, sexe, langue, private, verified, admin)

Abonnements (*#uid_abonne, *#uid_abonnement, date_abonnement, validation)

Post (*pid, #uid, #gid, #pid_parent, contenu, media, date_pub, duree_post, privacy, epingle)

Reactions (*#uid, *#pid, type, date_react)

Groupes (*gid, #uid_admin, #pid_epingle, nom_grp, description, date_creation, grp_private) // Fils de discussion

MessPrv (*mid, #uid_envoyeur, #uid_receveur, contenu, date_mess, lu, supprimé, duree_mess)

Favoris (*#uid, *#pid, date_favori)

Paramètres (#uid, email_recup, num_tel, a2f, notif_email, notif_push)

<!--
uid : user_id
pdp : photo de profil
cover : Photo de couverture
dateinsc : Date d'inscription
pid : post_id (Un commentaire est un post avec un post parent [plus simple pour les comms de comms])
gid : groupe_id
mid : message_id
-->

## Association
### USER
Un User peut avoir 0à* abonnements
Un User peut avoir 0à* followers
Un User peut avoir 0à* Posts
Un User peut réagir à 0à* Reactions
Un User peut avoir rejoints 0à* groupes
Un User peut être admin de 0à* groupes
Un User peut envoyer 0à* messages
Un User peut recevoir 0à* messages
Un User peut mettre en favori 0à* messages
Un User peut avoir que 1 paramètre
### POST
Un Post peut avoir qu'un seul parent mais un Post peut être parents de plusieurs Posts
Un Post peut être réalisé par 1 User
Un Post peut être posté dans 0à1 Groupe
Un Post peut avoir 0à* Réactions
Un Post peut avoir 0à* Favoris
### REACTION
Une Reaction peut être réalisé que par un User
Une Reaction peut être réalisé que sur un Post
### GROUPE
Un Groupe peut avoir qu'un admin
### MESSAGE
Un Message peut avoir qu'un seul envoyeur
Un Message peut avoir qu'un seul receveur
### FAVORI
Un Favori ne peut être réalisé que par un User
Un Favori ne peut être réalisé que sur un Post
### PARAMETRE
Un Paramètre ne peut être assigné qu'à un User
## CONDITIONS EN CAS DE PROBLEME
Si l'Admin supprime son compte, il est obligé de léguer le leader du grp à qq1, sinon il sera léguer au hasard
Si un Abonnement est refusé, alors il se supprime automatiquement de la table Abonnement

## EXEMPLE D'UTILISATION

Une nouvelle personne rentre l'URL du lien dans la barre de recherche et le redirige vers le site web.

Sur Mobile ou sur PC, le site web s'affiche correctement et lui montre le fil public.

Il regarde et voit qu'il y a plusieurs autres onglets :

En bas à gauche : Une Photo de Profil qui affiche au click 
- Le Profil de l'User
- Un onglet Profil
- Un onglet Paramètres
- Un onglet "Se Connecter" / "Se Déconnecter"

Au Milieu : La page principale qui montre les fils

Un 1er volet : Un Fil Public avec plusieurs Posts où on peut :
- Liker un Post ou React
- Commenter un Post
- Partager un Post
- Favori un Post
- Créer son propre Post

Un 2ème volet : Un Fil Privé qui affiche un slider d'une liste de "Groupes" avec lequel on a :
- Le @ de l'Admin qu'on peut ajouter
- Les Posts les plus Récents
- Les Posts les Plus Likés 

En bas à droite : On a aussi un espace Message qui affiche
- L'Espace Message
- Les Messages avec les derniers utilisateurs

Avec tout cela l'utilisateur se perd pas mal, mais dès qu'il clique sur un autre volet que le principal, ça lui dit de "se connecter" ou de "créer un compte", ce qui le redirige vers la page de connexion

d'ici il peut créer son compte ou se connecter

Une fois connecté, il peut aussi allez sur son profil pour :
- Supprimer ses posts
- Edit ses infos
- Supprimer les reacts de ses posts
- Epingler un message

Ou allez dans les paramètres pour :
- Changer ses paramètres
- Supprimer son compte

L'utilisateur peut créer un groupe dont il devient l'admin et y ajouter quelques potes dans ce groupe, comme ça l'user peut poster dans ce groupe pour que seuls ses amis de ce groupe peuvent voir, mais aussi il peut poster dans un groupe etc...

Dans son groupe, il peut supprimer les posts, épingler celui qui le plaît, créer ses propres posts, et visualiser les différents posts

Certains utilisateurs ne plaît pas au user, donc il peut les supprimer de ses abonnées et enlever leurs likes sur ses différents posts

User peut poster une image mais qui durera que 10min, pour pas que tout le monde le voit

User peut voir la liste des listes qu'il follow sur son profil