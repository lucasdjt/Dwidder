\echo '------------------------------------------------------'
\echo '------------------------[DROP]------------------------'
\echo '------------------------------------------------------'

\echo 'suppression des tables existantes'
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Posts CASCADE;
DROP TABLE IF EXISTS Groupes CASCADE;
DROP TABLE IF EXISTS Conversations CASCADE;
DROP TABLE IF EXISTS Messages CASCADE;
DROP TABLE IF EXISTS Abonnements CASCADE;
DROP TABLE IF EXISTS Favoris CASCADE;
DROP TABLE IF EXISTS Reactions CASCADE;
DROP TABLE IF EXISTS Membres CASCADE; 