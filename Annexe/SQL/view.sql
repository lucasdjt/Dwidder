\echo '------------------------------------------------------'
\echo '------------------------[VIEW]------------------------'
\echo '------------------------------------------------------'

\echo 'Création de la vue PostDetails'
CREATE VIEW PostDetails AS
SELECT 
    p.pid,
    p.gid,
    g.nomGrp,
    p.pidParent,
    p.contenu,
    p.media,
    p.dpub,
    p.dfin,
    u.pdp,
    u.pseudo,
    u.uid,
    u.idPseudo,
    (SELECT COUNT(*) FROM Reactions r WHERE r.pid = p.pid AND r.type = 'LIKES') AS nbLikes,
    (SELECT COUNT(*) FROM Posts c WHERE c.pidParent = p.pid) AS nbComm
FROM 
    Posts p
LEFT JOIN 
    Users u ON p.uid = u.uid
LEFT JOIN 
    Groupes g ON p.gid = g.gid;