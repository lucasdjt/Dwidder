Users (
    uid INT PRIMARY KEY
    pseudo VARCHAR(50) UNIQUE CHECK (LENGTH(pseudo) >= 5)
    prenom VARCHAR(50)
    nom_user VARCHAR(50) 
    email VARCHAR(255) UNIQUE
    mdp VARCHAR(255)
    bio VARCHAR(200)
    pdp VARCHAR(255)
    cover VARCHAR(255)
    dateinsc DATE
    datenaiss DATE CHECK (datenaiss <= dateinsc)
    loca VARCHAR(200)
    sexe CHAR(1) CHECK (sexe IN ('F','M','O'))
    langue CHAR(2) CHECK (langue IN ('FR','EN'))
    private BOOLEAN
    verified BOOLEAN
)

Abonnements (
    uid_abonne INT PRIMARY KEY
    uid_abonnement INT PRIMARY KEY
    date_abonnement DATE
    validation BOOLEAN

    #uid_abonne
    #uid_abonnement
)

Post (
    pid INT PRIMARY KEY
    uid INT
    gid INT
    pid_parent INT
    contenu VARCHAR(200)
    media VARCHAR(255)
    date_pub DATE

    #uid
    #gid
    #pid_parent
)

Reactions (
    uid INT PRIMARY KEY
    pid INT PRIMARY KEY
    type CHAR(3) CHECK (type IN ('lik','lov','fir','joy','sad','ang','thi'))
    date_react DATE

    #uid
    #pid
)

Groupes (
    gid INT PRIMARY KEY
    uid_admin INT
    nom_grp VARCHAR(30)
    description VARCHAR(200)
    date_creation DATE
    private BOOLEAN

    #uid_admin
)

MessPrv (
    mid INT PRIMARY KEY
    uid_envoyeur INT
    uid_receveur INT
    contenu VARCHAR(200)
    date_mess DATE
    lu BOOLEAN
    supprime CHAR(3) CHECK (supprime IN ('non','exp','rec','oui'))

    #uid_envoyeur
    #uid_receveur
)



```
MLD :

Users (*uid, pseudo, prenom, nom_user, email, mdp, bio, pdp, cover, dateinsc, datenaiss, loca, sexe, langue, private, verified)

Abonnements (*#uid_abonne, *#uid_abonnement, date_abonnement, validation)

Post (*pid, #uid, #gid, #pid_parent, contenu, media, date_pub)

Reactions (*#uid, *#pid, type, date_react)

Groupes (*gid, #uid_admin, nom_grp, description, date_creation, private)

MessPrv (*mid, #uid_envoyeur, #uid_receveur, contenu, date_mess, lu, supprimé)

Favoris (*#uid, *#pid, date_favori)

Paramètres (#uid, email_recup, num_tel, a2f, notif_email, notif_push)
```

uid : user_id
pdp : photo de profil
cover : Photo de couverture
dateinsc : Date d'inscription
pid : post_id (Un commentaire est un post avec un post parent [plus simple pour les comms de comms])
gid : groupe_id
mid : message_id

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

Un Post peut avoir qu'un seul parent mais un Post peut être parents de plusieurs Posts
Un Post peut être réalisé par 1 User
Un Post peut être posté dans 0à1 Groupe
Un Post peut avoir 0à* Réactions
Un Post peut avoir 0à* Favoris

Une Reaction peut être réalisé que par un User
Une Reaction peut être réalisé que sur un Post

Un Groupe peut avoir qu'un admin

Un Message peut avoir qu'un seul envoyeur
Un Message peut avoir qu'un seul receveur

Un Favori ne peut être réalisé que par un User
Un Favori ne peut être réalisé que sur un Post

Un Paramètre ne peut être assigné qu'à un User

Conditions en cas de problème :
Si l'Admin supprime son compte, il est obligé de léguer le leader du grp à qq1, sinon il sera léguer au hasard
Si un Abonnement est refusé, alors il se supprime automatiquement de la table Abonnement