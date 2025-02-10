## MLD du Projet

Users (*uid, pseudo, prenom, nom_user, email, mdp, bio, pdp, cover, dateinsc, datenaiss, loca, sexe, langue, private, verified, admin)

Abonnements (*#uid_abonne, *#uid_abonnement, date_abonnement, validation)

Post (*pid, #uid, #gid, #pid_parent, contenu, media, date_pub, duree_post, privacy, epingle)

Reactions (*#uid, *#pid, type, date_react)

Groupes (*gid, #uid_admin, #pid_epingle, nom_grp, description, date_creation, grp_private) // Fils de discussion

MessPrv (*mid, #uid_envoyeur, #uid_receveur, contenu, date_mess, lu, supprimé, duree_mess)

Favoris (*#uid, *#pid, date_favori)

Paramètres (#uid, email_recup, num_tel, a2f, notif_email, notif_push)

<!--
uid : user_id
pdp : photo de profil
cover : Photo de couverture
dateinsc : Date d'inscription
pid : post_id (Un commentaire est un post avec un post parent [plus simple pour les comms de comms])
gid : groupe_id
mid : message_id
-->