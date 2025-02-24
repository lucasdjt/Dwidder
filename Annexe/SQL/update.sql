\echo '------------------------------------------------------'
\echo '-----------------------[UPDATE]-----------------------'
\echo '------------------------------------------------------'

\echo 'Admin (Draggas) avec changement de toutes les infos'
UPDATE Users
SET nom_user = 'PRO', prenom = 'DRAGGAS', pseudo = 'Draggas', email = 'draggaspro@gmail.com', mdp = 'draggas', loca = 'Lille'
WHERE pseudo = 'DraggasAV';

\echo 'User Test (Clara) avec suppression au minimum'
UPDATE Users
SET prenom = NULL, nom_user = NULL, bio = NULL, pdp = NULL, date_naiss = NULL, loca = NULL, num_tel = NULL
WHERE pseudo = 'Clara';

\echo 'Groupe (DraggasCorp) épinglé le post7'
UPDATE Groupes
SET pid_epingle = 7
WHERE nom_grp = 'DraggasCorp';

\echo 'Groupe (DraggasCorp) épinglé le post1'
UPDATE Groupes
SET pid_epingle = 1
WHERE nom_grp = 'DraggasCorp';

\echo 'Draggas change sa réaction sur le post1'
UPDATE Reactions
SET type = 'LOVES'
WHERE uid = 1 AND pid = 1 ;


