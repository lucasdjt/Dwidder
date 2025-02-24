## REQUETES A REALISER 
### INSERT

- 3/ Création de Groupes
INSERT INTO Groupes (uid_admin, pid_epingle, nom_grp, description, date_creation) VALUES
(1, NULL, 'DraggasCorp', 'Communauté Draggas', CURRENT_DATE), 
(2, NULL, 'Private', 'Groupe privé', CURRENT_DATE), -- Groupe Test
(3, NULL, 'Test', NULL, '2000-01-01'), -- LE PLUS VIEUX / Groupe Test
(4, NULL, 'Test2', 'Groupe de test par Tom', '2030-01-01'); -- DANS LE FUTUR / Groupe Test

- 9/ Création des Membres
INSERT INTO Membres (uid, gid, date_join) VALUES
(1, 1, CURRENT_DATE), -- Ajout draggas, DraggasCorp
(2, 1, CURRENT_DATE), -- Ajout lucasdjt, DraggasCorp
(3, 1, CURRENT_DATE), -- Ajout johndoe, DraggasCorp
(2, 2, CURRENT_DATE), -- Ajout lucasdjt, Private
(3, 2, CURRENT_DATE), -- Ajout johndoe, Private
(1, 3, CURRENT_DATE), -- Ajout draggas, Test
(3, 3, '2000-01-01'), -- Ajout johndoe, Test - LE PLUS VIEUX
(4, 3, '2030-01-01'), -- Ajout tom, Test - DANS LE FUTUR
(4, 4, CURRENT_DATE); -- Ajout tom, Test2

- 2/ Création de Posts
INSERT INTO Posts (uid, gid, pid_parent, contenu, media, date_pub, duree_post) VALUES
(1, 1, NULL, 'Post 1 du groupe DraggasCorp, plus disponible dans 7j', 'post1.jpg', NOW(), INTERVAL '7 days'), -- Post Draggas avec toutes les infos sans parent au groupe DraggasCorp
(2, NULL, NULL, 'Post 2 public', NULL, CURRENT_DATE, NULL), -- Post Lucas sans grp, ni parent
(3, NULL, NULL, 'Post 3 public ancien', NULL, '2000-01-01', NULL), -- Post Test John sans grp, ni parent [le plus vieux]  
(2, NULL, 1, 'Post 4 réponse à Post 1', NULL, CURRENT_DATE, NULL), -- Post Test réponse John à Lucas
(4, NULL, NULL, 'Post 5 public futur', NULL, '2030-01-01', NULL), -- Post Test John sans grp, ni parent [dans le futur] 
(3, NULL, 1, 'Post 6 réponse à Post 1', NULL, CURRENT_DATE, NULL), -- Post Test John sans grp, ni parent [dans le futur] 
(2, NULL, 6, 'Post 7 réponse à Post 6, plus disponible dans 10j', 'post3.jpg', NOW(), INTERVAL '10 day'); -- Post Test réponse Lucas à John [futur]

- 4/ Création de Conversations
INSERT INTO Conversations (uid_envoyeur, uid_receveur) VALUES
(2, 1), -- Création de conversation entre Lucas DJT et Draggas
(1, 3), -- Création de conversation entre Draggas et John Doe
(1, 4); -- Création de conversation entre Draggas et Tom

- 5/ Création de Messages
INSERT INTO Messages (cid, uid, contenu, date_mess) VALUES
(1, 2, 'Slt Draggas', NOW() - INTERVAL '1 hour'), -- Conv1 - Message1 (Lucas DJT -> Draggas)
(1, 1, 'Slt Lucas ça va ?', NOW()), -- Conv1 - Message2 (Draggas -> Lucas DJT)
(2, 1, 'Slt John dans le passé', '2000-01-01'), -- Conv2 - Message (Draggas -> John Doe) [le plus vieux]
(3, 4, 'Slt Draggas dans le futur', '2030-01-01'); -- Conv3 - Message (Tom -> Draggas) [dans le futur]

- 6/ Création d abonnements
INSERT INTO Abonnements (uid_abonne, uid_abonnement, date_abonnement) VALUES
(2, 1, CURRENT_DATE), -- Lucas Follow Draggas
(1, 2, CURRENT_DATE), -- Draggas Follow Lucas
(2, 3, CURRENT_DATE), -- Lucas Follow John
(3, 1, '2000-01-01'), -- John Follow Draggas
(4, 1, '2030-01-01'), -- Tom Follow Draggas
(4, 2, CURRENT_DATE); -- John Follow Lucas

- 7/ Création de Favoris
INSERT INTO Favoris (uid, pid, date_favori) VALUES
(1, 1, CURRENT_DATE), -- Draggas Favori Post 1
(1, 2, CURRENT_DATE), -- Draggas Favori Post 2
(1, 3, CURRENT_DATE), -- Draggas Favori Post 7
(3, 1, CURRENT_DATE), -- John Favori Post 1
(3, 2, CURRENT_DATE), -- John Favori Post 2
(3, 7, CURRENT_DATE), -- John Favori Post 7
(3, 4, '2000-01-01'), -- John Favori Post 4 [Le plus vieux]
(3, 6, '2030-01-01'); -- John Favori Post 1 [Dans le futur]

- 8/ Création de Réactions
INSERT INTO Reactions (uid, pid, type, date_react) VALUES
(1, 1, 'LIKES', CURRENT_DATE), -- Draggas ajoute "LIKES" Post 1
(1, 2, 'LOVES', CURRENT_DATE), -- Draggas ajoute "LOVES" Post 2
(1, 3, 'THIFT', CURRENT_DATE), -- Draggas ajoute "THIFT" Post 3
(1, 5, 'THIFT', CURRENT_DATE), -- Draggas ajoute "THIFT" Post 5
(1, 7, 'FIRES', CURRENT_DATE), -- Draggas ajoute "FIRES" Post 7
(3, 1, 'JOYYY', CURRENT_DATE), -- John ajoute "JOYYY" Post 1
(3, 2, 'SADDD', CURRENT_DATE), -- John ajoute "SADDD" Post 2
(3, 3, 'THIFT', '2000-01-01'), -- John ajoute "THIFT" Post 3 [le plus vieux]
(3, 5, 'THIFT', '2030-01-01'), -- John ajoute "THIFT" Post 5 [dans le futur]
(3, 7, 'ANGER', CURRENT_DATE); -- John ajoute "ANGER" Post 7

### DELETE

- 3/ Supprimer le groupe Test Private
DELETE FROM Groupes WHERE nom_grp = 'Private';

- 9/ Supprimer Lucas du grp DraggasCorp
DELETE FROM Membres WHERE uid = 2 AND gid = 1;

- 2/ Supprimer le post2
DELETE FROM Posts WHERE uid = 2 AND pid = 2;

- 4/ Supprimer sa conversation
DELETE FROM Conversations WHERE uid_envoyeur = 2 AND uid_receveur = 1;

- 6/ Supprimer LucasDJT des abonnés, abonnements
DELETE FROM Abonnements WHERE uid_abonne = 2;
DELETE FROM Abonnements WHERE uid_abonnement = 2;

- 7/ Supprimer Favori Draggas
DELETE FROM Favoris WHERE uid = 1 AND pid = 1;
DELETE FROM Favoris WHERE uid = 1 AND pid = 7;

- 8/ Supprimer Réactions Draggas
DELETE FROM Reactions WHERE uid = 1 AND pid = 1;
DELETE FROM Reactions WHERE uid = 1 AND pid = 7;

### SELECT

- On peut obtenir la liste des groupes que nous avons rejoint
SELECT * FROM UserGroups WHERE uid = 1;
- On peut obtenir la liste des posts
SELECT * FROM PostDetails WHERE uid = 1 ORDER BY date_pub DESC; -- trié par date
SELECT * FROM PostDetails WHERE uid = 1 ORDER BY nb_reactions DESC; -- trié par réactions
SELECT COUNT(*) FROM PostDetails WHERE uid = 1; -- le nombre de posts
- On peut obtenir les abonnés/abonnements d un utilisateur
SELECT * FROM UserFollowers WHERE uid_abonnement = 1 ORDER BY date_abonnement DESC; -- liste d'abonné trié par date
SELECT COUNT(*) FROM UserFollowers WHERE uid_abonnement = 1; -- nombre d'abonnés
SELECT * FROM UserFollowers WHERE uid_abonne = 1 ORDER BY date_abonnement DESC; -- liste d'abonnement trié par date
SELECT COUNT(*) FROM UserFollowers WHERE uid_abonne = 1; -- nombre d'abonnements
- On peut obtenir la liste des favoris fait par un utilisateur trié par date
SELECT * FROM UserFavorites WHERE uid = 1 ORDER BY date_favori DESC;
SELECT COUNT(*) FROM UserFavorites WHERE uid = 1; -- nombre de favoris
- Les infos complet d un post
SELECT * FROM PostDetails WHERE pid = 1;
- On peut obtenir le nombre de favoris et de réactions sur un post
SELECT nb_favoris FROM PostDetails WHERE pid = 1;
SELECT nb_reactions FROM PostDetails WHERE pid = 1;
- On peut obtenir le liste des réactions sur un post avec les infos de ses réactions
SELECT * FROM PostReactions WHERE pid = 1 ORDER BY date_react DESC;
- Du fil public sans parent/groupe
SELECT * FROM PostDetails WHERE gid IS NULL AND pid_parent IS NULL ORDER BY date_pub DESC; -- trié par date
SELECT * FROM PostDetails WHERE gid IS NULL AND pid_parent IS NULL ORDER BY nb_reactions, nb_favoris DESC; -- trié par réactions
- D un groupe particulier sans parent
SELECT * FROM PostDetails WHERE gid = 1 AND pid_parent IS NULL ORDER BY date_pub DESC; -- trié par date
SELECT * FROM PostDetails WHERE gid = 1 AND pid_parent IS NULL ORDER BY nb_reactions, nb_favoris DESC; -- trié par réactions
SELECT COUNT(*) FROM PostDetails WHERE gid = 1; -- nombre de posts du groupe
- Réponses d un post particulier trié par réaction
SELECT * FROM PostDetails WHERE pid_parent = 1 ORDER BY nb_reactions DESC;
- On peut obtenir les infos d un groupe
SELECT P.* FROM Posts P JOIN Groupes G ON P.pid = G.pid_epingle WHERE G.gid = 1; -- post épinglé
SELECT U.* FROM Users U JOIN Groupes G ON U.uid = G.uid_admin WHERE G.gid = 1; -- l'admin du groupe
SELECT nom_grp FROM Groupes WHERE gid = 1; -- le nom du groupe
SELECT * FROM Groupes WHERE gid = 1; -- les infos globales
SELECT * FROM GroupMembers WHERE gid = 1 ORDER BY date_join DESC; -- obtenir la liste des membres d un groupe trié par date
SELECT COUNT(*) FROM GroupMembers WHERE gid = 1; -- le nombre de membres d'un groupe
- On peut obtenir la liste des personnes a qui la personne a (été) contacté trié par date
SELECT DISTINCT C.*, U.id_pseudo FROM Conversations C JOIN Users U ON (C.uid_envoyeur = U.uid OR C.uid_receveur = uid) WHERE C.uid_envoyeur = 1 OR C.uid_receveur = 1 ORDER BY C.cid DESC;
- On peut obtenir les messages privés d une conversation trié par date
SELECT * FROM UserMessages WHERE cid = 1 ORDER BY date_mess DESC;
- On peut obtenir les infos d un message
SELECT * FROM UserMessages WHERE mid = 1;

### UPDATE

- Groupe (DraggasCorp) épinglé le post7
UPDATE Groupes
SET pid_epingle = 7
WHERE nom_grp = 'DraggasCorp';

- Groupe (DraggasCorp) épinglé le post1
UPDATE Groupes
SET pid_epingle = 1
WHERE nom_grp = 'DraggasCorp';

- Draggas change sa réaction sur le post1
UPDATE Reactions
SET type = 'LOVES'
WHERE uid = 1 AND pid = 1 ;


