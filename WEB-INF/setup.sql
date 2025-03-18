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

\echo '3/ Création de la table Groupes'
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

\echo '2/ Création de la table Posts'
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
-- Permet d'accéler les requêtes qui font des recherches sur ses colonnes
CREATE INDEX idx_posts_uid ON Posts(uid);
CREATE INDEX idx_posts_gid ON Posts(gid);
CREATE INDEX idx_posts_pidParent ON Posts(pidParent);

\echo '5/ Création de la table Messages'
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

\echo '------------------------------------------------------'
\echo '-----------------------[INSERT]-----------------------'
\echo '------------------------------------------------------'

\echo '1/ Création de Comptes utilisateurs'
INSERT INTO Users (idPseudo, pseudo, prenom, nomUser, email, mdp, bio, dinsc, dnaiss, loca, admin) VALUES
('draggas', 'Draggas', 'Lucas', 'De Jesus Teixeira', 'draggaspro@gmail.com', 'draggas', 'Admin du reseau', DEFAULT, '2005-05-10', 'Isbergues', TRUE), -- ADMIN
('lucasdjt', 'Lucas', 'Lucas', 'DJT', 'lucasdjtpro@gmail.com', 'lucas', 'Etudiant en informatique', DEFAULT, NULL, DEFAULT, FALSE),
('johndoe', 'John', DEFAULT, DEFAULT, 'john.doe@gmail.com', 'john', DEFAULT, '2000-01-01', '1950-01-01', DEFAULT, FALSE), -- LE PLUS VIEUX / Compte Test
('tom', 'Tom', DEFAULT, DEFAULT, 'tom@gmail.com', 'tom', DEFAULT, '2030-01-01', '1950-01-01', DEFAULT, FALSE), -- DANS LE FUTUR / Compte Test
('clara', 'Clara', 'Clara', 'Dupont', 'clara@gmail.com', 'clara', 'Graphiste', DEFAULT, '1950-01-01', 'Paris', FALSE); -- Compte Test

\echo '3/ Création de Groupes'
INSERT INTO Groupes (uid, nomGrp, description, dcreat) VALUES
(1, 'DraggasCorp', 'Communauté Draggas', DEFAULT), 
(2, 'Private', 'Groupe privé', DEFAULT), -- Groupe Test
(3, 'Test', DEFAULT, '2000-01-01'), -- LE PLUS VIEUX / Groupe Test
(4, 'Test2', 'Groupe de test par Tom', '2030-01-01'); -- DANS LE FUTUR / Groupe Test

\echo '9/ Création des Membres'
INSERT INTO Membres (uid, gid, djoin) VALUES
(1, 1, DEFAULT), -- Ajout draggas, DraggasCorp
(2, 1, DEFAULT), -- Ajout lucasdjt, DraggasCorp
(3, 1, DEFAULT), -- Ajout johndoe, DraggasCorp
(2, 2, DEFAULT), -- Ajout lucasdjt, Private
(3, 2, DEFAULT), -- Ajout johndoe, Private
(1, 3, DEFAULT), -- Ajout draggas, Test
(3, 3, '2000-01-01'), -- Ajout johndoe, Test - LE PLUS VIEUX
(4, 3, '2030-01-01'), -- Ajout tom, Test - DANS LE FUTUR
(4, 4, DEFAULT); -- Ajout tom, Test2

\echo '2/ Création de Posts'
INSERT INTO Posts (uid, gid, pidParent, contenu, media, dpub, dfin) VALUES
(1, 1, NULL, 'Post 1 du groupe DraggasCorp, plus disponible dans 7j', 'img/post1.jpg', DEFAULT, NOW() + INTERVAL '7 days'), -- Post Draggas avec toutes les infos sans parent au groupe DraggasCorp
(2, NULL, NULL, 'Post 2 public', NULL, DEFAULT, NULL), -- Post Lucas sans grp, ni parent
(3, NULL, NULL, 'Post 3 public ancien', NULL, '2000-01-01', NULL), -- Post Test John sans grp, ni parent [le plus vieux]  
(2, 1, 1, 'Post 4 réponse à Post 1', NULL, DEFAULT, NULL), -- Post Test réponse John à Lucas
(4, NULL, NULL, 'Post 5 public futur', NULL, '2030-01-01', NULL), -- Post Test John sans grp, ni parent [dans le futur] 
(3, 1, 1, 'Post 6 réponse à Post 1', NULL, DEFAULT, NULL), -- Post Test John sans grp, ni parent [dans le futur] 
(2, NULL, 6, 'Post 7 réponse à Post 6, plus disponible dans 10j', NULL, DEFAULT, NOW() + INTERVAL '10 day'); -- Post Test réponse Lucas à John [futur]

\echo '5/ Création de Messages'
INSERT INTO Messages (uidEnvoyeur, uidReceveur, corps, dmess) VALUES
(1, 2, 'Slt Draggas', NOW() - INTERVAL '1 hour'), -- Conv1 - Message1 (Lucas DJT -> Draggas)
(2, 1, 'Slt Lucas ça va ?', DEFAULT), -- Conv1 - Message2 (Draggas -> Lucas DJT)
(1, 3, 'Slt John dans le passé', '2000-01-01'), -- Conv2 - Message (Draggas -> John Doe) [le plus vieux]
(4, 1, 'Slt Draggas dans le futur', '2030-01-01'); -- Conv3 - Message (Tom -> Draggas) [dans le futur]

\echo '6/ Création d abonnements'
INSERT INTO Abonnements (uidAbonne, uidAbonnement, dabonnement) VALUES
(2, 1, DEFAULT), -- Lucas Follow Draggas
(1, 2, DEFAULT), -- Draggas Follow Lucas
(2, 3, DEFAULT), -- Lucas Follow John
(3, 1, '2000-01-01'), -- John Follow Draggas
(4, 1, '2030-01-01'), -- Tom Follow Draggas
(4, 2, DEFAULT); -- John Follow Lucas

\echo '7/ Création de Favoris'
INSERT INTO Favoris (uid, pid, dfavori) VALUES
(1, 1, DEFAULT), -- Draggas Favori Post 1
(1, 2, DEFAULT), -- Draggas Favori Post 2
(1, 3, DEFAULT), -- Draggas Favori Post 7
(3, 1, DEFAULT), -- John Favori Post 1
(3, 2, DEFAULT), -- John Favori Post 2
(3, 7, DEFAULT), -- John Favori Post 7
(3, 4, '2000-01-01'), -- John Favori Post 4 [Le plus vieux]
(3, 6, '2030-01-01'); -- John Favori Post 1 [Dans le futur]

\echo '8/ Création de Réactions'
INSERT INTO Reactions (uid, pid, type) VALUES
(1, 1, 'LIKES'), -- Draggas ajoute "LIKES" Post 1
(1, 2, 'LOVES'), -- Draggas ajoute "LOVES" Post 2
(1, 3, 'THIFT'), -- Draggas ajoute "THIFT" Post 3
(1, 5, 'THIFT'), -- Draggas ajoute "THIFT" Post 5
(1, 7, 'FIRES'), -- Draggas ajoute "FIRES" Post 7
(3, 1, 'JOYYY'), -- John ajoute "JOYYY" Post 1
(3, 2, 'SADDD'), -- John ajoute "SADDD" Post 2
(3, 3, 'THIFT'), -- John ajoute "THIFT" Post 3
(3, 5, 'THIFT'), -- John ajoute "THIFT" Post 5
(3, 7, 'ANGER'); -- John ajoute "ANGER" Post 7

\echo '------------------------------------------------------'
\echo '------------------------[VIEW]------------------------'
\echo '------------------------------------------------------'

\echo 'Création de la vue PostDetails'
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