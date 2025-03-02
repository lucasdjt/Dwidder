\echo '------------------------------------------------------'
\echo '-----------------------[SELECT]-----------------------'
\echo '------------------------------------------------------'

\echo '- On peut obtenir les infos d une personne'
SELECT * FROM Users WHERE id_pseudo = 'draggas'; -- Obtenir tous les infos
SELECT pseudo FROM Users WHERE uid = 1; -- Obtenir pseudo
\echo '- On peut chercher les infos correspondant aux infos de connexion id_pseudo/mail et mdp'
SELECT * FROM Users WHERE (id_pseudo = 'Lucas' OR email = 'lucasdjtpro@gmail.com') AND mdp = 'lucas';
\echo '- On peut obtenir la liste des groupes que nous avons rejoint'
SELECT G.* FROM GROUPES G INNER JOIN Membres M ON G.gid = M.gid WHERE M.uid = 1;
\echo '- On peut obtenir la liste des posts'
SELECT * FROM PostDetails WHERE uid = 1 ORDER BY date_pub DESC; -- trié par date
SELECT * FROM PostDetails WHERE uid = 1 ORDER BY nb_reactions DESC; -- trié par réactions
SELECT COUNT(*) FROM PostDetails WHERE uid = 1; -- le nombre de posts
\echo '- On peut obtenir les abonnés/abonnements d un utilisateur'
SELECT * FROM UserFollowers WHERE uid_abonnement = 1 ORDER BY date_abonnement DESC; -- liste d'abonné trié par date
SELECT COUNT(*) FROM UserFollowers WHERE uid_abonnement = 1; -- nombre d'abonnés
SELECT * FROM UserFollowers WHERE uid_abonne = 1 ORDER BY date_abonnement DESC; -- liste d'abonnement trié par date
SELECT COUNT(*) FROM UserFollowers WHERE uid_abonne = 1; -- nombre d'abonnements
\echo '- On peut obtenir la liste des favoris fait par un utilisateur trié par date'
SELECT * FROM UserFavorites WHERE uid = 1 ORDER BY date_favori DESC;
SELECT COUNT(*) FROM UserFavorites WHERE uid = 1; -- nombre de favoris

\echo '- Les infos complet d un post'
SELECT * FROM PostDetails WHERE pid = 1;
\echo '- On peut obtenir le nombre de favoris et de réactions sur un post'
SELECT nb_favoris FROM PostDetails WHERE pid = 1;
SELECT nb_reactions FROM PostDetails WHERE pid = 1;
\echo '- On peut obtenir le liste des réactions sur un post avec les infos de ses réactions'
SELECT * FROM PostReactions WHERE pid = 1 ORDER BY date_react DESC;
\echo '- Du fil public sans parent/groupe'
SELECT * FROM PostDetails WHERE gid IS NULL AND pid_parent IS NULL ORDER BY date_pub DESC; -- trié par date
SELECT * FROM PostDetails WHERE gid IS NULL AND pid_parent IS NULL ORDER BY nb_reactions, nb_favoris DESC; -- trié par réactions
\echo '- D un groupe particulier sans parent'
SELECT * FROM PostDetails WHERE gid = 1 AND pid_parent IS NULL ORDER BY date_pub DESC; -- trié par date
SELECT * FROM PostDetails WHERE gid = 1 AND pid_parent IS NULL ORDER BY nb_reactions, nb_favoris DESC; -- trié par réactions
SELECT COUNT(*) FROM PostDetails WHERE gid = 1; -- nombre de posts du groupe
\echo '- Réponses d un post particulier trié par réaction'
SELECT * FROM PostDetails WHERE pid_parent = 1 ORDER BY nb_reactions DESC;
\echo '-- On peut obtenir les infos d un groupe'
SELECT P.* FROM Posts P JOIN Groupes G ON P.pid = G.pid_epingle WHERE G.gid = 1; -- post épinglé
SELECT U.* FROM Users U JOIN Groupes G ON U.uid = G.uid_admin WHERE G.gid = 1; -- l'admin du groupe
SELECT nom_grp FROM Groupes WHERE gid = 1; -- le nom du groupe
SELECT * FROM Groupes WHERE gid = 1; -- les infos globales
SELECT * FROM GroupMembers WHERE gid = 1 ORDER BY date_join DESC; -- obtenir la liste des membres d un groupe trié par date
SELECT COUNT(*) FROM GroupMembers WHERE gid = 1; -- le nombre de membres d'un groupe
\echo '- On peut obtenir la liste des personnes a qui la personne a (été) contacté trié par date'
SELECT DISTINCT C.*, U.id_pseudo FROM Conversations C JOIN Users U ON (C.uid_envoyeur = U.uid OR C.uid_receveur = uid) WHERE C.uid_envoyeur = 1 OR C.uid_receveur = 1 ORDER BY C.cid DESC;
\echo '- On peut obtenir les messages privés d une conversation trié par date'
SELECT * FROM UserMessages WHERE cid = 1 ORDER BY date_mess DESC;
\echo '- On peut obtenir les infos d un message'
SELECT * FROM UserMessages WHERE mid = 1;
