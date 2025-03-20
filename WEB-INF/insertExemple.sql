\echo '------------------------------------------------------'
\echo '-----------------------[INSERT]-----------------------'
\echo '------------------------------------------------------'

\echo '1/ Création de Comptes utilisateurs'
INSERT INTO Users (idPseudo, pseudo, prenom, nomUser, email, mdp, bio, dinsc, dnaiss, loca, admin) VALUES
('dwidder', 'Dwidder', 'Lucas', 'De Jesus Teixeira', 'dwidderpro@gmail.com', 'dwidder', 'Admin - 1/8', DEFAULT, '2005-05-10', 'Isbergues', TRUE), -- ADMIN
('lucasdjt', 'Lucas', 'Lucas', 'DJT', 'lucasdjtpro@gmail.com', 'lucas', 'Etudiant en informatique - 8/1', DEFAULT, NULL, 'Lille', FALSE),
('johndoe', 'John', DEFAULT, DEFAULT, 'john.doe@gmail.com', 'john', DEFAULT, '2000-01-01', '1950-01-01', DEFAULT, FALSE), -- LE PLUS VIEUX
('tom', 'Tom', DEFAULT, DEFAULT, 'tom@gmail.com', 'tom', DEFAULT, NOW() + INTERVAL '1 day', '2006-05-10', DEFAULT, FALSE), -- DANS LE FUTUR
('clara', 'Clara', 'Clara', 'Dupont', 'clara@gmail.com', 'clara', 'Graphiste', DEFAULT, DEFAULT, 'Paris', FALSE),
('bob', 'Bob', 'Bob', 'Marley', 'bob@gmail.com', 'bob', 'Musician', DEFAULT, DEFAULT, 'London', FALSE),
('charlie', 'Charlie', 'Charlie', 'Chaplin', 'charlie@gmail.com', 'charlie', 'Je suis charlie', DEFAULT, DEFAULT, 'France', FALSE),
('mario', 'Mario', 'Mario', DEFAULT, 'mario@gmail.com', 'mario', 'Luigi', DEFAULT, DEFAULT, DEFAULT, FALSE),
('mario2', '2', 'Mario2', DEFAULT, 'mario2@gmail.com', 'mario2', 'Luigi', DEFAULT, DEFAULT, DEFAULT, FALSE),
('hack', '<h1>HACK</h1>', 't', 'e', 'hack@gmail.com', 'hack', '<script>alert("PRENOM")</script>', DEFAULT, DEFAULT, '<div style="background:url(javascript:alert("XSS"))">', FALSE); -- XSS

\echo '2/ Création de Groupes'
INSERT INTO Groupes (uid, nomGrp, description, dcreat) VALUES
(1, 'DwidderCorp', 'Communaute de Dwidder', DEFAULT), 
(2, 'Private', 'Groupe prive', DEFAULT),
(3, 'Test', DEFAULT, '2000-01-01'), -- LE PLUS VIEUX
(4, 'Test2', 'Groupe de test par Tom', '2030-01-01'), -- DANS LE FUTUR
(10, '<script>alert("HACK")</script>', '<h1>ATTAQUER PAR DU XSS</h1>', '2030-01-01'); -- XSS

\echo '3/ Création des Membres'
INSERT INTO Membres (uid, gid, djoin) VALUES
(1, 1, DEFAULT), -- Ajout dwidder, DwidderCorp
(1, 2, DEFAULT), -- Ajout dwidder, Private
(1, 3, DEFAULT), -- Ajout dwidder, Test
(1, 4, DEFAULT), -- Ajout dwidder, Test2
(1, 5, DEFAULT), -- Ajout dwidder, <script>alert("HACK")</script>
(2, 1, DEFAULT), -- Ajout lucasdjt, DwidderCorp
(2, 2, DEFAULT), -- Ajout lucasdjt, Private
(3, 1, '2000-01-01'), -- Ajout johndoe, DwidderCorp - LE PLUS VIEUX
(3, 3, DEFAULT), -- Ajout johndoe, Test
(4, 1, '2030-01-01'), -- Ajout tom, DwidderCorp - DANS LE FUTUR
(4, 4, DEFAULT), -- Ajout tom, Test2
(10, 5, DEFAULT); -- Ajout hack, <script>alert("HACK")</script>

\echo '4/ Création de Posts'
INSERT INTO Posts (uid, gid, pidParent, contenu, media, dpub, dfin) VALUES
(1, NULL, NULL, 'Post 1 de Dwidder public, ++ Reactions, expiration NULL', 'img/pdp.png', DEFAULT, NULL),
(1, 1, NULL, 'Post 2 de Dwidder du groupe DwidderCorp, plus disponible dans 1j', NULL, DEFAULT, NOW() + INTERVAL '1 days'),
(2, 2, NULL, 'Post 3 de Lucas du groupe Private, plus disponible dans 7j', DEFAULT, DEFAULT, NOW() + INTERVAL '7 days'),
(1, NULL, NULL, 'Post 4 Expire depuis 7j', DEFAULT, DEFAULT, NOW() - INTERVAL '7 days'), -- Expiré
(10, NULL, NULL, '<h1>Post 5 HACK poste dans 1jour</h1>', 'img/pdp.png', NOW() + INTERVAL '1 days', NULL), -- XSS

(1, NULL, 1, 'Post 6 reponse au 1', DEFAULT, DEFAULT, NULL),
(1, NULL, 1, 'Post 7 reponse au 1 le plus recent', DEFAULT, NOW() + INTERVAL '1 days', NULL),
(2, 1, 2, 'Post 8 reponse à Post 2', NULL, DEFAULT, NULL);

\echo '5/ Creation de Messages'
INSERT INTO Messages (uidEnvoyeur, uidReceveur, corps, dmess) VALUES
(1, 2, 'Slt Dwidder, c est Lucas y a 1h', NOW() - INTERVAL '1 hour'),
(2, 1, 'Slt Lucas, c est Dwidder mtn', DEFAULT),
(1, 3, 'Slt John, c est Dwidder', DEFAULT),
(4, 1, 'Slt Dwidder, c est TOM', DEFAULT),
(10, 1, '<script>alert("SALUT")</script>', DEFAULT); -- XSS

\echo '6/ Création d abonnements'
INSERT INTO Abonnements (uidAbonne, uidAbonnement, dabonnement) VALUES
(3, 1, DEFAULT),
(4, 1, DEFAULT),
(5, 1, DEFAULT),
(6, 1, DEFAULT),
(7, 1, DEFAULT),
(8, 1, DEFAULT),
(9, 1, DEFAULT),
(10, 1, DEFAULT),
(1, 2, DEFAULT),
(2, 3, DEFAULT),
(2, 4, DEFAULT),
(2, 5, DEFAULT),
(2, 6, DEFAULT),
(2, 7, DEFAULT),
(2, 8, DEFAULT),
(2, 9, DEFAULT),
(2, 10, DEFAULT);

\echo '7/ Création de Favoris'
INSERT INTO Favoris (uid, pid, dfavori) VALUES
(1, 1, DEFAULT),
(1, 2, DEFAULT),
(1, 3, DEFAULT),
(2, 1, DEFAULT),
(2, 2, DEFAULT);

\echo '8/ Création de Réactions'
INSERT INTO Reactions (uid, pid, type) VALUES
(1, 1, 'LIKES'),
(1, 2, 'LIKES'),
(1, 3, 'LIKES'),
(2, 1, 'LOVES'),
(3, 1, 'THIFT'),
(4, 1, 'FIRES'),
(5, 1, 'JOYYY'),
(6, 1, 'SADDD'),
(7, 1, 'ANGER');