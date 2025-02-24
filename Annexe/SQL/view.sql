-- Vue pour récupérer les informations d'un utilisateur
CREATE VIEW UserInfo AS
SELECT uid, id_pseudo, pseudo, prenom, nom_user, email, bio, pdp, date_insc, date_naiss, loca, sexe, num_tel, langue
FROM Users;

-- Vue pour récupérer les groupes d'un utilisateur
CREATE VIEW UserGroups AS
SELECT M.uid, G.gid, G.nom_grp, G.description, G.date_creation
FROM Membres M
JOIN Groupes G ON M.gid = G.gid;

-- Vue pour les abonnés et abonnements
CREATE VIEW UserFollowers AS
SELECT A.uid_abonne, U.id_pseudo AS pseudo_abonne, A.uid_abonnement, U2.id_pseudo AS pseudo_abonnement, A.date_abonnement
FROM Abonnements A
JOIN Users U ON A.uid_abonne = U.uid
JOIN Users U2 ON A.uid_abonnement = U2.uid;

-- Vue pour les posts avec leurs infos globales
CREATE VIEW PostDetails AS
SELECT P.pid, P.uid, U.pseudo, P.contenu, P.media, P.date_pub, P.gid, G.nom_grp, 
       (SELECT COUNT(*) FROM Favoris F WHERE F.pid = P.pid) AS nb_favoris,
       (SELECT COUNT(*) FROM Reactions R WHERE R.pid = P.pid) AS nb_reactions
FROM Posts P
LEFT JOIN Users U ON P.uid = U.uid
LEFT JOIN Groupes G ON P.gid = G.gid;

-- Vue pour les réactions sur les posts
CREATE VIEW PostReactions AS
SELECT R.pid, R.uid, U.pseudo, R.type, R.date_react
FROM Reactions R
JOIN Users U ON R.uid = U.uid;

-- Vue pour les favoris d'un utilisateur
CREATE VIEW UserFavorites AS
SELECT F.uid, U.pseudo, F.pid, P.contenu, F.date_favori
FROM Favoris F
JOIN Users U ON F.uid = U.uid
JOIN Posts P ON F.pid = P.pid;

-- Vue pour les messages et conversations
CREATE VIEW UserMessages AS
SELECT M.mid, M.cid, M.uid, U.pseudo, M.contenu, M.date_mess
FROM Messages M
JOIN Users U ON M.uid = U.uid;

-- Vue pour les membres des groupes
CREATE VIEW GroupMembers AS
SELECT M.gid, G.nom_grp, M.uid, U.pseudo, M.date_join
FROM Membres M
JOIN Users U ON M.uid = U.uid
JOIN Groupes G ON M.gid = G.gid;