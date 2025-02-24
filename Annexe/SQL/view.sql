-- Vue pour récupérer les informations d'un utilisateur
CREATE VIEW UserInfo AS
SELECT uid, id_pseudo, pseudo, prenom, nom_user, email, bio, pdp, date_insc, date_naiss, loca, sexe, num_tel, langue
FROM Users;

-- Vue pour récupérer les groupes d'un utilisateur
CREATE VIEW UserGroups AS
SELECT uid, gid, nom_grp, description, date_creation
FROM Membres JOIN Groupes USING(gid);

-- Vue pour les abonnés et abonnements
CREATE VIEW UserFollowers AS
SELECT uid_abonne, U.id_pseudo AS pseudo_abonne, uid_abonnement, U2.id_pseudo AS pseudo_abonnement, date_abonnement
FROM Abonnements JOIN Users U ON uid_abonne = U.uid JOIN Users U2 ON uid_abonnement = U2.uid;

-- Vue pour les posts avec leurs infos globales
CREATE VIEW PostDetails AS
SELECT P.pid, P.uid, P.gid, P.pid_parent, U.pseudo, P.contenu, P.media, P.date_pub, G.nom_grp, 
       (SELECT COUNT(*) FROM Favoris F WHERE F.pid = P.pid) AS nb_favoris,
       (SELECT COUNT(*) FROM Reactions R WHERE R.pid = P.pid) AS nb_reactions
FROM Posts P
LEFT JOIN Users U ON P.uid = U.uid
LEFT JOIN Groupes G ON P.gid = G.gid;

-- Vue pour les réactions sur les posts
CREATE VIEW PostReactions AS
SELECT pid, uid, pseudo, type, date_react
FROM Reactions JOIN Users USING(uid);

-- Vue pour les favoris d'un utilisateur
CREATE VIEW UserFavorites AS
SELECT F.uid, U.pseudo, F.pid, P.contenu, F.date_favori
FROM Favoris F
JOIN Users U ON F.uid = U.uid
JOIN Posts P ON F.pid = P.pid;

-- Vue pour les messages et conversations
CREATE VIEW UserMessages AS
SELECT mid, cid, uid, pseudo, contenu, date_mess
FROM Messages JOIN Users USING(uid);

-- Vue pour les membres des groupes
CREATE VIEW GroupMembers AS
SELECT gid, nom_grp, uid, pseudo, date_join
FROM Membres JOIN Users USING(uid) JOIN Groupes USING(gid);