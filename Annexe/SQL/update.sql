\echo '------------------------------------------------------'
\echo '-----------------------[UPDATE]-----------------------'
\echo '------------------------------------------------------'

\echo 'Admin (Draggas) avec changement de toutes les infos'
UPDATE Users
SET nomUser = 'PRO', prenom = 'DRAGGAS', pseudo = 'Draggas', email = 'draggaspro@gmail.com', mdp = 'draggas', loca = 'Lille'
WHERE pseudo = 'DraggasAV';

\echo 'User Test (Clara) avec suppression au minimum'
UPDATE Users
SET prenom = NULL, nomUser = NULL, bio = NULL, dnaiss = NULL, loca = NULL, tel = NULL
WHERE pseudo = 'Clara';

\echo 'Groupe (DraggasCorp) épinglé le post7'
UPDATE Groupes
SET pid = 7
WHERE nomGrp = 'DraggasCorp';

\echo 'Groupe (DraggasCorp) épinglé le post1'
UPDATE Groupes
SET pid = 1
WHERE nomGrp = 'DraggasCorp';

\echo 'Draggas change sa réaction sur le post1'
UPDATE Reactions
SET type = 'LOVES'
WHERE uid = 1 AND pid = 1 ;


