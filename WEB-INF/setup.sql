\echo '------------------------------------------------------'
\echo '------------------------[DROP]------------------------'
\echo '------------------------------------------------------'

\echo 'suppression des tables existantes'
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Posts CASCADE;
DROP TABLE IF EXISTS Groupes CASCADE;
DROP TABLE IF EXISTS Messages CASCADE;
DROP TABLE IF EXISTS Abonnements CASCADE;
DROP TABLE IF EXISTS Favoris CASCADE;
DROP TABLE IF EXISTS Reactions CASCADE;
DROP TABLE IF EXISTS Membres CASCADE; 
DROP TABLE IF EXISTS Logs CASCADE;

\echo '------------------------------------------------------'
\echo '-----------------------[CREATE]-----------------------'
\echo '------------------------------------------------------'

\echo '1/ Création de la table Users'
CREATE TABLE IF NOT EXISTS Users (
    uid serial PRIMARY KEY,
    idPseudo varchar(15) UNIQUE NOT NULL CHECK (LENGTH(idPseudo) >= 3),
    pseudo varchar(20) NOT NULL CHECK (LENGTH(pseudo) >= 1),
    prenom varchar(20) DEFAULT '',
    nomUser varchar(50) DEFAULT '',
    email varchar(255) UNIQUE NOT NULL,
    mdp varchar(255) NOT NULL,
    bio varchar(200) DEFAULT '',
    pdp varchar(255) DEFAULT 'img/pdp.png',
    dinsc TIMESTAMP NOT NULL DEFAULT NOW(),
    dnaiss DATE,
    loca varchar(200) DEFAULT '',
    admin BOOLEAN NOT NULL
);

\echo '2/ Création de la table Groupes'
CREATE TABLE IF NOT EXISTS Groupes (
    gid serial PRIMARY KEY,
    uid int NOT NULL,
    nomGrp varchar(30) UNIQUE NOT NULL,
    pdpGrp varchar(255) DEFAULT 'img/pdp.png',
    description varchar(200) DEFAULT '',
    dcreat TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_user_admin FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE
);
CREATE INDEX idx_groupes_uid_admin ON Groupes(uid);

\echo '3/ Création de la table Posts'
CREATE TABLE IF NOT EXISTS Posts (
    pid serial PRIMARY KEY,
    uid int NOT NULL,
    gid int,
    pidParent int,
    contenu varchar(200) DEFAULT '',
    media varchar(255),
    dpub TIMESTAMP DEFAULT NOW(),
    dfin TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_groupe FOREIGN KEY (gid) REFERENCES Groupes(gid) ON DELETE CASCADE,
    CONSTRAINT fk_post_parent FOREIGN KEY (pidParent) REFERENCES Posts(pid) ON DELETE CASCADE
);
CREATE INDEX idx_posts_uid ON Posts(uid);
CREATE INDEX idx_posts_gid ON Posts(gid);
CREATE INDEX idx_posts_pidParent ON Posts(pidParent);

\echo '4/ Création de la table Messages'
CREATE TABLE IF NOT EXISTS Messages (
    mid serial PRIMARY KEY,
    uidEnvoyeur int NOT NULL,
    uidReceveur int NOT NULL,
    corps varchar(200) DEFAULT '',
    imgMess varchar(255),
    dmess TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_user_envoyeur FOREIGN KEY (uidEnvoyeur) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_user_receveur FOREIGN KEY (uidReceveur) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT no_self_conversation CHECK (uidEnvoyeur <> uidReceveur)
);
CREATE INDEX idx_messages_env ON Messages(uidEnvoyeur);
CREATE INDEX idx_messages_rec ON Messages(uidReceveur);

\echo '5/ Création de la table Abonnements'
CREATE TABLE IF NOT EXISTS Abonnements (
    uidAbonne int NOT NULL,
    uidAbonnement int NOT NULL,
    dabonnement TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (uidAbonne, uidAbonnement),
    CONSTRAINT fk_user_abonne FOREIGN KEY (uidAbonne) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_user_abonnement FOREIGN KEY (uidAbonnement) REFERENCES Users(uid) ON DELETE CASCADE
);
CREATE INDEX idx_abonnements_abonne ON Abonnements(uidAbonne);
CREATE INDEX idx_abonnements_abonnement ON Abonnements(uidAbonnement);

\echo '6/ Création de la table Favoris'
CREATE TABLE IF NOT EXISTS Favoris (
    uid int NOT NULL,
    pid int NOT NULL,
    dfavori TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (uid, pid),
    CONSTRAINT fk_user_favori FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_post_favori FOREIGN KEY (pid) REFERENCES Posts(pid) ON DELETE CASCADE
);
CREATE INDEX idx_favoris_uid ON Favoris(uid);
CREATE INDEX idx_favoris_pid ON Favoris(pid);

\echo '7/ Création de la table Reactions'
CREATE TABLE IF NOT EXISTS Reactions (
    uid int NOT NULL,
    pid int NOT NULL,
    type varchar(10) NOT NULL CHECK (type IN ('LIKES', 'LOVES', 'FIRES', 'JOYYY', 'SADDD', 'ANGER', 'THIFT')),
    dreact TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (uid, pid),
    CONSTRAINT fk_user_react FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_post_react FOREIGN KEY (pid) REFERENCES Posts(pid) ON DELETE CASCADE
);
CREATE INDEX idx_reactions_uid ON Reactions(uid);
CREATE INDEX idx_reactions_pid ON Reactions(pid);

\echo '8/ Création de la table Membres'
CREATE TABLE IF NOT EXISTS Membres (
    uid int NOT NULL,
    gid int NOT NULL,
    djoin TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (uid, gid),
    CONSTRAINT fk_user_group FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_group_user FOREIGN KEY (gid) REFERENCES Groupes(gid) ON DELETE CASCADE
);
CREATE INDEX idx_membres_uid ON Membres(uid);
CREATE INDEX idx_membres_gid ON Membres(gid);

\echo '9/ Création de la table Logs'
CREATE TABLE IF NOT EXISTS Logs (
    lid serial PRIMARY KEY,
    daction TIMESTAMP NOT NULL DEFAULT NOW(),
    pseudoLog varchar(255) NOT NULL,
    textLog varchar(255) NOT NULL
);

\echo '10/ Création de la vue PostDetails'
CREATE VIEW PostDetails AS
SELECT 
    p.pid,
    p.gid,
    g.nomGrp,
    g.uid AS uidAdmin,
    p.pidParent,
    p.contenu,
    p.media,
    p.dpub,
    p.dfin,
    u.pdp,
    u.pseudo,
    u.uid,
    u.idPseudo,
    (SELECT COUNT(*) FROM Reactions r WHERE r.pid = p.pid) AS nbLikes,
    (SELECT COUNT(*) FROM Posts c WHERE c.pidParent = p.pid) AS nbComm
FROM 
    Posts p
LEFT JOIN 
    Users u ON p.uid = u.uid
LEFT JOIN 
    Groupes g ON p.gid = g.gid
WHERE p.dfin IS NULL OR p.dfin > NOW();