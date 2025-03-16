\echo '------------------------------------------------------'
\echo '-----------------------[INSERT]-----------------------'
\echo '------------------------------------------------------'

\echo '1/ Création de Comptes utilisateurs'
INSERT INTO Users (idPseudo, pseudo, prenom, nomUser, email, mdp, bio, dinsc, dnaiss, loca, sexe, tel, langue, admin) VALUES
('draggas', 'DraggasAV', 'Lucas', 'De Jesus Teixeira', 'draggasav@gmail.com', 'draggasav', 'Admin du réseau', CURRENT_DATE, '2005-05-10', 'Isbergues', 'M', '+33000000000', 'EN', TRUE), -- ADMIN
('lucasdjt', 'Lucas', 'Lucas', 'DJT', 'lucasdjtpro@gmail.com', 'lucas', 'Étudiant en informatique', CURRENT_DATE, '2005-05-10', NULL, 'M', NULL, 'FR', FALSE),
('johndoe', 'John', NULL, NULL, 'john.doe@gmail.com', 'john', NULL, '2000-01-01', '1950-01-01', NULL, 'O', NULL, 'EN', FALSE), -- LE PLUS VIEUX / Compte Test
('tom', 'Tom', NULL, NULL, 'tom@gmail.com', 'tom', NULL, '2030-01-01', '1950-01-01', NULL, 'M', NULL, 'FR', FALSE), -- DANS LE FUTUR / Compte Test
('clara', 'Clara', 'Clara', 'Dupont', 'clara@gmail.com', 'clara', 'Graphiste', CURRENT_DATE, '1950-01-01', 'Paris', 'F', '+33333333333', 'FR', FALSE); -- Compte Test

\echo '3/ Création de Groupes'
INSERT INTO Groupes (uid, pid, nomGrp, description, dcreat) VALUES
(1, NULL, 'DraggasCorp', 'Communauté Draggas', CURRENT_DATE), 
(2, NULL, 'Private', 'Groupe privé', CURRENT_DATE), -- Groupe Test
(3, NULL, 'Test', NULL, '2000-01-01'), -- LE PLUS VIEUX / Groupe Test
(4, NULL, 'Test2', 'Groupe de test par Tom', '2030-01-01'); -- DANS LE FUTUR / Groupe Test

\echo '9/ Création des Membres'
INSERT INTO Membres (uid, gid, djoin) VALUES
(1, 1, CURRENT_DATE), -- Ajout draggas, DraggasCorp
(2, 1, CURRENT_DATE), -- Ajout lucasdjt, DraggasCorp
(3, 1, CURRENT_DATE), -- Ajout johndoe, DraggasCorp
(2, 2, CURRENT_DATE), -- Ajout lucasdjt, Private
(3, 2, CURRENT_DATE), -- Ajout johndoe, Private
(1, 3, CURRENT_DATE), -- Ajout draggas, Test
(3, 3, '2000-01-01'), -- Ajout johndoe, Test - LE PLUS VIEUX
(4, 3, '2030-01-01'), -- Ajout tom, Test - DANS LE FUTUR
(4, 4, CURRENT_DATE); -- Ajout tom, Test2

\echo '2/ Création de Posts'
INSERT INTO Posts (uid, gid, pidParent, contenu, media, dpub, dfin) VALUES
(1, 1, NULL, 'Post 1 du groupe DraggasCorp, plus disponible dans 7j', 'img/post1.jpg', NOW(), NOW() + INTERVAL '7 days'), -- Post Draggas avec toutes les infos sans parent au groupe DraggasCorp
(2, NULL, NULL, 'Post 2 public', NULL, CURRENT_DATE, NULL), -- Post Lucas sans grp, ni parent
(3, NULL, NULL, 'Post 3 public ancien', NULL, '2000-01-01', NULL), -- Post Test John sans grp, ni parent [le plus vieux]  
(2, NULL, 1, 'Post 4 réponse à Post 1', NULL, CURRENT_DATE, NULL), -- Post Test réponse John à Lucas
(4, NULL, NULL, 'Post 5 public futur', NULL, '2030-01-01', NULL), -- Post Test John sans grp, ni parent [dans le futur] 
(3, NULL, 1, 'Post 6 réponse à Post 1', NULL, CURRENT_DATE, NULL), -- Post Test John sans grp, ni parent [dans le futur] 
(2, NULL, 6, 'Post 7 réponse à Post 6, plus disponible dans 10j', 'img/post3.jpg', NOW(), NOW() + INTERVAL '10 day'); -- Post Test réponse Lucas à John [futur]

\echo '4/ Création de Conversations'
INSERT INTO Conversations (uidEnvoyeur, uidReceveur) VALUES
(1, 2), -- Création de conversation entre Lucas DJT et Draggas
(1, 3), -- Création de conversation entre Draggas et John Doe
(1, 4); -- Création de conversation entre Draggas et Tom

\echo '5/ Création de Messages'
INSERT INTO Messages (cid, uid, corps, dmess) VALUES
(1, 2, 'Slt Draggas', NOW() - INTERVAL '1 hour'), -- Conv1 - Message1 (Lucas DJT -> Draggas)
(1, 1, 'Slt Lucas ça va ?', NOW()), -- Conv1 - Message2 (Draggas -> Lucas DJT)
(2, 1, 'Slt John dans le passé', '2000-01-01'), -- Conv2 - Message (Draggas -> John Doe) [le plus vieux]
(3, 4, 'Slt Draggas dans le futur', '2030-01-01'); -- Conv3 - Message (Tom -> Draggas) [dans le futur]

\echo '6/ Création d abonnements'
INSERT INTO Abonnements (uidAbonne, uidAbonnement, dabonnement) VALUES
(2, 1, CURRENT_DATE), -- Lucas Follow Draggas
(1, 2, CURRENT_DATE), -- Draggas Follow Lucas
(2, 3, CURRENT_DATE), -- Lucas Follow John
(3, 1, '2000-01-01'), -- John Follow Draggas
(4, 1, '2030-01-01'), -- Tom Follow Draggas
(4, 2, CURRENT_DATE); -- John Follow Lucas

\echo '7/ Création de Favoris'
INSERT INTO Favoris (uid, pid, dfavori) VALUES
(1, 1, CURRENT_DATE), -- Draggas Favori Post 1
(1, 2, CURRENT_DATE), -- Draggas Favori Post 2
(1, 3, CURRENT_DATE), -- Draggas Favori Post 7
(3, 1, CURRENT_DATE), -- John Favori Post 1
(3, 2, CURRENT_DATE), -- John Favori Post 2
(3, 7, CURRENT_DATE), -- John Favori Post 7
(3, 4, '2000-01-01'), -- John Favori Post 4 [Le plus vieux]
(3, 6, '2030-01-01'); -- John Favori Post 1 [Dans le futur]

\echo '8/ Création de Réactions'
INSERT INTO Reactions (uid, pid, type, dreact) VALUES
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