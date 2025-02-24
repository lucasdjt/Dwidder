\echo '------------------------------------------------------'
\echo '-----------------------[DELETE]-----------------------'
\echo '------------------------------------------------------'

\echo '1/ Supprimer les comptes Tests'
DELETE FROM Users WHERE id_pseudo IN ('johndoe', 'tom', 'clara');

\echo '3/ Supprimer le groupe Test Private'
DELETE FROM Groupes WHERE nom_grp = 'Private';

\echo '9/ Supprimer Lucas du grp DraggasCorp'
DELETE FROM Membres WHERE uid = 2 AND gid = 1;

\echo '2/ Supprimer le post2'
DELETE FROM Posts WHERE uid = 2 AND pid = 2;

\echo '4/ Supprimer sa conversation'
DELETE FROM Conversations WHERE uid_envoyeur = 2 AND uid_receveur = 1;

\echo '6/ Supprimer LucasDJT des abonnés, abonnements'
DELETE FROM Abonnements WHERE uid_abonne = 2;
DELETE FROM Abonnements WHERE uid_abonnement = 2;

\echo '7/ Supprimer Favori Draggas'
DELETE FROM Favoris WHERE uid = 1 AND pid = 1;
DELETE FROM Favoris WHERE uid = 1 AND pid = 7;

\echo '8/ Supprimer Réactions Draggas'
DELETE FROM Reactions WHERE uid = 1 AND pid = 1;
DELETE FROM Reactions WHERE uid = 1 AND pid = 7;