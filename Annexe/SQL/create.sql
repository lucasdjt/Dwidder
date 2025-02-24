\echo '------------------------------------------------------'
\echo '-----------------------[CREATE]-----------------------'
\echo '------------------------------------------------------'

\echo '1/ Création de la table Users'
CREATE TABLE IF NOT EXISTS Users (
    uid serial PRIMARY KEY,
    id_pseudo varchar(15) UNIQUE NOT NULL CHECK (LENGTH(id_pseudo) >= 5),
    pseudo varchar(20) NOT NULL CHECK (LENGTH(pseudo) >= 5),
    prenom varchar(20),
    nom_user varchar(50),
    email varchar(255) UNIQUE NOT NULL,
    mdp varchar(255) NOT NULL,
    bio varchar(200),
    pdp varchar(255),
    date_insc TIMESTAMP NOT NULL DEFAULT NOW(),
    date_naiss DATE,
    loca varchar(200),
    sexe char(1) NOT NULL CHECK (sexe IN ('F', 'M', 'O')),
    num_tel varchar(30),
    langue char(2) NOT NULL CHECK (langue IN ('FR', 'EN')),
    admin BOOLEAN NOT NULL
);

\echo '2/ Création de la table Posts'
CREATE TABLE IF NOT EXISTS Posts (
    pid serial PRIMARY KEY,
    uid int NOT NULL,
    gid int,
    pid_parent int,
    contenu varchar(200),
    media varchar(255),
    date_pub TIMESTAMP NOT NULL DEFAULT NOW(),
    duree_post INTERVAL,
    CONSTRAINT fk_user FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_groupe FOREIGN KEY (gid) REFERENCES Groupes(gid) ON DELETE CASCADE,
    CONSTRAINT fk_post_parent FOREIGN KEY (pid_parent) REFERENCES Posts(pid) ON DELETE CASCADE
);
-- Permet d'accéler les requêtes qui font des recherches sur ses colonnes
CREATE INDEX idx_posts_uid ON Posts(uid);
CREATE INDEX idx_posts_gid ON Posts(gid);
CREATE INDEX idx_posts_pid_parent ON Posts(pid_parent);

\echo '3/ Création de la table Groupes'
CREATE TABLE IF NOT EXISTS Groupes (
    gid serial PRIMARY KEY,
    uid_admin int NOT NULL,
    pid_epingle int,
    nom_grp varchar(30) UNIQUE NOT NULL,
    description varchar(200),
    date_creation TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_user_admin FOREIGN KEY (uid_admin) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_post_epingle FOREIGN KEY (pid_epingle) REFERENCES Posts(pid) ON DELETE CASCADE
);
CREATE INDEX idx_groupes_uid_admin ON Groupes(uid_admin);

\echo '4/ Création de la table Conversations'
CREATE TABLE IF NOT EXISTS Conversations (
    cid serial PRIMARY KEY,
    uid_envoyeur int NOT NULL,
    uid_receveur int NOT NULL,
    CONSTRAINT fk_user_envoyeur FOREIGN KEY (uid_envoyeur) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_user_receveur FOREIGN KEY (uid_receveur) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT unique_conversation_pair UNIQUE (LEAST(uid_envoyeur, uid_receveur), GREATEST(uid_envoyeur, uid_receveur)),
    CONSTRAINT no_self_conversation CHECK (uid_envoyeur <> uid_receveur)
);
CREATE INDEX idx_conversations_env ON Conversations(uid_envoyeur);
CREATE INDEX idx_conversations_rec ON Conversations(uid_receveur);

\echo '5/ Création de la table Messages'
CREATE TABLE IF NOT EXISTS Messages (
    mid serial PRIMARY KEY,
    cid int NOT NULL,
    uid int NOT NULL,
    contenu varchar(200) NOT NULL,
    date_mess TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_user FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_conversation FOREIGN KEY (cid) REFERENCES Conversations(cid) ON DELETE CASCADE
);
CREATE INDEX idx_messages_cid ON Messages(cid);
CREATE INDEX idx_messages_uid ON Messages(uid);

\echo '6/ Création de la table Abonnements'
CREATE TABLE IF NOT EXISTS Abonnements (
    uid_abonne int NOT NULL,
    uid_abonnement int NOT NULL,
    date_abonnement TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (uid_abonne, uid_abonnement),
    CONSTRAINT fk_user_abonne FOREIGN KEY (uid_abonne) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_user_abonnement FOREIGN KEY (uid_abonnement) REFERENCES Users(uid) ON DELETE CASCADE
);
CREATE INDEX idx_abonnements_abonne ON Abonnements(uid_abonne);
CREATE INDEX idx_abonnements_abonnement ON Abonnements(uid_abonnement);

\echo '7/ Création de la table Favoris'
CREATE TABLE IF NOT EXISTS Favoris (
    uid int NOT NULL,
    pid int NOT NULL,
    date_favori TIMESTAMP NOT NULL DEFAULT NOW(),
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
    date_react TIMESTAMP NOT NULL DEFAULT NOW(),
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
    date_join TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (uid, gid),
    CONSTRAINT fk_user_group FOREIGN KEY (uid) REFERENCES Users(uid) ON DELETE CASCADE,
    CONSTRAINT fk_group_user FOREIGN KEY (gid) REFERENCES Groupes(gid) ON DELETE CASCADE
);
CREATE INDEX idx_membres_uid ON Membres(uid);
CREATE INDEX idx_membres_gid ON Membres(gid);