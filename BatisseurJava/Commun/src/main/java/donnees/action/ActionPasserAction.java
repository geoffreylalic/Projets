package donnees.action;

import donnees.Identification;
import donnees.Inventaire;

public class ActionPasserAction extends Action{

    public ActionPasserAction() {

    }

    public ActionPasserAction(Identification id) {
        setJoueur(id);
    }

    @Override
    public void appliquerAction(MoteurDeJeu moteur) {
        Inventaire inventaire = moteur.getInventaireDuJoueur(getJoueur());
        inventaire.decrementNbAction();
        inventaire.ajouterEcus( 1 + inventaire.getNbPasserDansTour());
        inventaire.incrementNbPasserDansTour();
    }

    @Override
    public boolean verifier(MoteurDeJeu moteur) {
        return true;
    }

    public String toString() {
        return "ActionPasserAction jouée par "+getJoueur();
    }

}
