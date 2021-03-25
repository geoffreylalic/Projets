### On a besoin de deux argument pour lancer une partie

* le nombre de joueur (minimum 2 joueur et maximum 4) 
exemple:on choisi 2 joueur ,on met 2 en argument numéro1.
* efectuer le choix entre les paquets MoyenAge et Antiquite(choix 1 moyen age /choix 2 antiquité)
exemple:En argument 2 et 3 on a CarteBatiment et CarteOuvrier
* si on veut lancer 500 parties ou non (optionnel)
exemple:En argument 500 pour lancer 500 parties, sinon laisser vide

on a donc pour lancer 500 parties avec 3 joueurs sur le jeu MoyenAge : 3 MoyenAge 500

### Pour le Démarrer le client/serveur
Le client/serveur nécessite 2 joueurs pour fonctionner. Il faut donc lancer 2 Client.

pour lancer le serveur :
 * Serveur 
 * edit configuration
 * program arguments : rentrer les arguments voulus
 * lancer le programme
 
 pour lancer le client : ( X le nombre de joueurs)
 * cd Joueur
 * mvn exec:java
