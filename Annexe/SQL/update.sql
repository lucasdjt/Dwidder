\echo '------------------------------------------------------'
\echo '-----------------------[UPDATE]-----------------------'
\echo '------------------------------------------------------'

\echo 'Admin (Draggas) avec changement de toutes les infos'
UPDATE Utilisateur
SET nom = 'PRO', prenom = 'DRAGGAS', email = 'draggaspro@gmail.com', mot_de_passe = 'draggas', loca = 'Lille'
WHERE pseudo = 'DraggasAV';

\echo 'User Test (Clara) avec suppression au minimum'
UPDATE Utilisateur
SET prenom = NULL, nom_user = NULL, bio = NULL, pdp = NULL, date_naiss = NULL, loca = NULL, num_tel = NULL;
WHERE pseudo = 'Clara';

\echo 'Groupe (DraggasCorp) épinglé le post7'
UPDATE Groupe
SET pid_epingle = 7
WHERE nom = 'DraggasCorp';

\echo 'Groupe (DraggasCorp) épinglé le post1'
UPDATE Groupe
SET pid_epingle = 1
WHERE nom = 'DraggasCorp';

\echo 'Draggas change sa réaction sur le post1'
UPDATE Reactions
SET type = 'LOVES'
WHERE uid = 1 AND pid = 1 ;


