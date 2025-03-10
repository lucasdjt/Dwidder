\echo '------------------------------------------------------'
\echo '-----------------------[CREATE]-----------------------'
\echo '------------------------------------------------------'

\echo '1/ Création de la table Users'
CREATE TABLE IF NOT EXISTS Users (
    uid serial PRIMARY KEY,
    idPseudo varchar(15) UNIQUE NOT NULL CHECK (LENGTH(idPseudo) >= 3),
    pseudo varchar(20) NOT NULL CHECK (LENGTH(pseudo) >= 1),
    prenom varchar(20),
    nomUser varchar(50),
    email varchar(255) UNIQUE NOT NULL,
    mdp varchar(255) NOT NULL,
    bio varchar(200),
    pdp varchar(255) DEFAULT 'img/pdp.png',
    dinsc TIMESTAMP NOT NULL DEFAULT NOW(),
    dnaiss DATE,
    loca varchar(200),
    sexe char(1) NOT NULL CHECK (sexe IN ('F', 'M', 'O')),
    tel varchar(30),
    langue char(2) NOT NULL CHECK (langue IN ('FR', 'EN')),
    admin BOOLEAN NOT NULL
);

\echo '3/ Création de la table Groupes'
CREATE TABLE IF NOT EXISTS Groupes (
    gid serial PRIMARY KEY,
    uid int NOT NULL,
    nomGrp varchar(30) UNIQUE NOT NULL,
    description varchar(200),
    dcreat TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_user_admin FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE
);
CREATE INDEX idx_groupes_uid_admin ON Groupes(uid);

\echo '2/ Création de la table Posts'
CREATE TABLE IF NOT EXISTS Posts (
    pid serial PRIMARY KEY,
    uid int NOT NULL,
    gid int,
    pidParent int,
    contenu varchar(200),
    media varchar(255),
    dpub TIMESTAMP DEFAULT NOW(),
    dfin TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_groupe FOREIGN KEY (gid) REFERENCES Groupes(gid) ON DELETE CASCADE,
    CONSTRAINT fk_post_parent FOREIGN KEY (pidParent) REFERENCES Posts(pid) ON DELETE CASCADE
);
-- Permet d'accéler les requêtes qui font des recherches sur ses colonnes
CREATE INDEX idx_posts_uid ON Posts(uid);
CREATE INDEX idx_posts_gid ON Posts(gid);
CREATE INDEX idx_posts_pidParent ON Posts(pidParent);

ALTER TABLE Groupes
ADD pid int,
ADD CONSTRAINT fk_post_epingle FOREIGN KEY (pid) REFERENCES Posts(pid) ON DELETE CASCADE;

\echo '4/ Création de la table Conversations'
CREATE TABLE IF NOT EXISTS Conversations (
    cid serial PRIMARY KEY,
    uidEnvoyeur int NOT NULL,
    uidReceveur int NOT NULL,
    CONSTRAINT fk_user_envoyeur FOREIGN KEY (uidEnvoyeur) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_user_receveur FOREIGN KEY (uidReceveur) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT no_self_conversation CHECK (uidEnvoyeur <> uidReceveur)
);
CREATE INDEX idx_conversations_env ON Conversations(uidEnvoyeur);
CREATE INDEX idx_conversations_rec ON Conversations(uidReceveur);

\echo '5/ Création de la table Messages'
CREATE TABLE IF NOT EXISTS Messages (
    mid serial PRIMARY KEY,
    cid int NOT NULL,
    uid int NOT NULL,
    corps varchar(200) NOT NULL,
    dmess TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_user FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_conversation FOREIGN KEY (cid) REFERENCES Conversations(cid) ON DELETE CASCADE
);
CREATE INDEX idx_messages_cid ON Messages(cid);
CREATE INDEX idx_messages_uid ON Messages(uid);

\echo '6/ Création de la table Abonnements'
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

\echo '7/ Création de la table Favoris'
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

\echo '8/ Création de la table Reactions'
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

\echo '9/ Création de la table Membres'
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