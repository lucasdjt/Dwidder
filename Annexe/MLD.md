Users (*uid, id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, dateinsc, datenaiss, loca, sexe, num_tel, langue, admin)
Post (*pid, #uid, #gid, #pid_parent, contenu, media, date_pub, duree_post)
Groupes (*gid, #uid_admin, #pid_epingle, nom_grp, description, date_creation) // Fils de discussion
Conversations (*cid, #uid_envoyeur, #uid_receveur)
MessPrv (*mid, #cid, contenu, date_mess)
Abonnements (*(#uid_abonne, #uid_abonnement), date_abonnement)
Favoris (*(#uid, #pid), date_favori)
Reactions (*(#uid, #pid), type, date_react)
Mbr_Groupes (*(#uid, #gid), date_join)