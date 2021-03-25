BILAN: 

- en terme de fonctionnalités livrées et issues faites : 

	- Le serveur client est désormais disponible mais n'est pas complet, il est possible d'établir une connexion entre les deux.
	- Le serveur arrive a recupere le nom du client. 
	- Il est possible de choisir le deck depart(en argument) Antiquité ou bien Moyen-âge, cependant les règles d'Antiquité ne sont pas encore utilisés.
	- Le premier joueur est choisi de manière aléatoire. 



- en terme de tests

	- Les tests sont plus complets, grâce a l'utilisation dans certains cas de Mock (pour Paquets Bâtiment et Ouvrier).
	- On a divisé ActionTest en plusieurs tests (ActionAffecterOuvrier...).



- en terme d'organisation de code et de refactoring

	- Création des classes Serveur et Clients.
	- La partie est maintenant silencieuse.
	- Choisis en argument les paquets de cartes des deux decks (soit Antiquité ou Moyen-âge).
	- Création de plusieurs pom.xml pour le Serveur Client.
	- Simplification des deux main afin de l'utiliser de l'exterieur.