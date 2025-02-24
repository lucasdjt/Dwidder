\echo 'TEST D ERREURS'

\echo 'Ajout Doublon Groupe'
INSERT INTO Groupes (uid_admin, pid_epingle, nom_grp, description, date_creation) 
VALUES (1, NULL, 'DraggasCorp', 'Groupe en doublon', CURRENT_DATE);

\echo 'Ajout Conversation Soi-Même (Draggas - Draggas)'
INSERT INTO Conversations (uid_envoyeur, uid_receveur) VALUES (1, 1);

\echo 'Ajout Doublon Abonnements (Lucas DJT --> Draggas)'
INSERT INTO Abonnements (uid_abonne, uid_abonnement, date_abonnement) VALUES (2, 1, CURRENT_DATE);

\echo 'Ajout Doublon Favori (Post1, Draggas)'
INSERT INTO Favoris (uid, pid, date_favori) VALUES (1, 1, CURRENT_DATE);

\echo 'Ajout Doublon Réaction (Post1, Draggas)'
INSERT INTO Reactions (uid, pid, type, date_react) VALUES (1, 1, 'LIKES', CURRENT_DATE);

\echo 'Update Clara avec id_pseudo identique à John Doe'
UPDATE Users SET id_pseudo = 'johndoe' WHERE pseudo = 'Clara';

\echo 'Update Clara avec email identique à John Doe'
UPDATE Users SET email = 'john.doe@gmail.com' WHERE pseudo = 'Clara';
