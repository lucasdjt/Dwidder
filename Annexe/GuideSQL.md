## Les étapes du SQL
- Création des tables - CREATE TABLE
- Insertion des données - INSERT INTO
- Modification des données - UPDATE
- Supression des données - DELETE
- Récupération des données [Transformation en Vues pour Simplifier les requêtes] - SELECT
## Les Requêtes du SQL
### CREATE TABLE
> Users
uid : serial
id_pseudo : varchar[15] - unique / all low / min 5
pseudo : varchar[20] - min 5
prenom : varchar[20] 
nom_user : varchar[50]
email : varchar[255] - unique
mdp : varchar[32] - hashed (or like that)
bio : varchar[200]
pdp : varchar[255]
dateinsc : DATE
datenaiss : DATE
loca : varchar[200]
sexe : char[1] IN ("F","M","O")
num_tel : varchar[30]
langue : char[2] IN ("FR","EN")
admin : BOOLEAN
PRIMARY KEY : uid
> Post
pid : serial
uid : int
gid : int
pid_parent : int
contenu : varchar[200]
media : varchar[255]
date_pub : DATE
duree_post : INTERVAL
PRIMARY KEY : pid
FOREIGN KEY : uid -> uid / gid -> gid / pid -> pid_parent
> Groupes
gid : serial
uid_admin : int
pid_epingle : int
nom_grp : varchar[30]
description : varchar[200]
date_creation : DATE
PRIMARY KEY : gid
FOREIGN KEY : uid -> uid_admin / pid -> pid_epingle
> Conversations
cid : serial
uid_envoyeur : int
uid_receveur : int
PRIMARY KEY : cid, (uid_envoyeur, uid_receveur)
FOREIGN KEY : uid -> uid_envoyeur / uid -> uid_receveur
> MessPrv
mid : serial
cid : int
contenu : varchar[200]
date_mess : DATE
PRIMARY KEY : mid
FOREIGN KEY : cid -> cid
> Abonnements
uid_abonne : int
uid_abonnement : int
date_abonnement : DATE
PRIMARY KEY : (uid_abonne, uid_abonnement)
FOREIGN KEY : uid -> uid_abonne / uid -> uid_abonnement
> Favoris
uid : int
pid : int
date_favori : DATE
PRIMARY KEY : (uid, pid)
FOREIGN KEY : uid -> uid / pid -> pid
> Reactions
uid : int
pid : int
type : char[3] IN ("lik", "lov", "fir", "joy", "sad", "ang", "thi")
date_react : DATE
PRIMARY KEY : (uid, pid)
FOREIGN KEY : uid -> uid / pid -> pid
> Mbr_Groupes
uid : int
gid : int
date_join : DATE
PRIMARY KEY : (uid, gid)
FOREIGN KEY : uid -> uid / gid -> gid
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