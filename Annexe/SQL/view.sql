/*VUES UTILISES*/
-- Vue pour les abonnés et abonnements
CREATE VIEW UserFollowers AS
SELECT uidAbonne, U.idPseudo AS pseudoAbonne, uidAbonnement, U2.idPseudo AS pseudoAbonnement, dabonnement
FROM Abonnements JOIN Users U ON uidAbonne = U.uid JOIN Users U2 ON uidAbonnement = U2.uid;

/*VUES NON UTILISES*/
-- Vue pour les posts avec leurs infos globales
CREATE VIEW PostDetails AS
SELECT P.pid, P.uid, P.gid, P.pidParent, U.pseudo, P.contenu, P.media, P.dpub, G.nomGrp, 
       (SELECT COUNT(*) FROM Favoris F WHERE F.pid = P.pid) AS nbFavoris,
       (SELECT COUNT(*) FROM Reactions R WHERE R.pid = P.pid) AS nbReactions
FROM Posts P
LEFT JOIN Users U ON P.uid = U.uid
LEFT JOIN Groupes G ON P.gid = G.gid;

-- Vue pour les réactions sur les posts
CREATE VIEW PostReactions AS
SELECT pid, uid, pseudo, type, dreact
FROM Reactions JOIN Users USING(uid);

-- Vue pour les favoris d'un utilisateur
CREATE VIEW UserFavorites AS
SELECT F.uid, U.pseudo, F.pid, P.contenu, F.dfavori
FROM Favoris F
JOIN Users U ON F.uid = U.uid
JOIN Posts P ON F.pid = P.pid;

-- Vue pour les messages et conversations
CREATE VIEW UserMessages AS
SELECT mid, cid, uid, pseudo, corps, dmess
FROM Messages JOIN Users USING(uid);

-- Vue pour les membres des groupes
CREATE VIEW GroupMembers AS
SELECT gid, nomGrp, U.uid, U.pseudo, djoin
FROM Membres JOIN Users U USING(uid) JOIN Groupes USING(gid);