BILAN: 

- en terme de fonctionnalités livrées et issues faites : 

Il est désormais disponible d'avoir plusieurs tours sur le projet, tant qu'un des joueurs n'a pas atteint les 17 ecus, la partie ne s'arrete pas.
Ensuite, il est possible d'avoir une pioche visible
Les stats affichent maintenant qui a gagné. 
 

- en terme de tests
	- Ajout de tous les test d'action (EffectuerAction..)  
	- Test PaquetsCartes 
	- Les tests sont maintenant silencieux
	


- en terme d'organisation de code et de refactoring

	Création de la classe abstact Action, ajout de plusieurs classes qui herite tous d'Action.
	On a retiré plusieurs println pour des toString. 
	Completion des cartes json
	Correction de la methode retireCarte qui avait certain problème lors de l'itération précèdente.
	Lors de l'itération 3, choix action etait un int, maintenant c'est l'objet Action.
	Le pom.xml ne génére plus d'erreur.

