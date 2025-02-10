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
    admin BOOLEAN
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
    duree_post INTERVAL
    privacy CHAR(6) CHECK (privacy IN ('public','friend','privee','meonly'))
    epingle BOOLEAN

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
    grp_private BOOLEAN
    pid_epingle INT

    #uid_admin
    #pid_epingle
)

MessPrv (
    mid INT PRIMARY KEY
    uid_envoyeur INT
    uid_receveur INT
    contenu VARCHAR(200)
    date_mess DATE
    lu BOOLEAN
    supprime CHAR(3) CHECK (supprime IN ('non','exp','rec','oui'))
    duree_mess INTERVAL

    #uid_envoyeur
    #uid_receveur
)