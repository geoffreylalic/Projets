package donnees.action;

import donnees.Identification;
import donnees.Inventaire;

public class ActionAcheterAction extends Action {

    public ActionAcheterAction() {

    }

    public ActionAcheterAction(Identification id) {
        setJoueur(id);
    }


    @Override
    public void appliquerAction(MoteurDeJeu moteur) {
        Inventaire inventaire = moteur.getInventaireDuJoueur(getJoueur());
        inventaire.setEcus(inventaire.getEcus() - 5 );
        inventaire.setNbAction(inventaire.getNbAction() + 1);
    }

    @Override
    public boolean verifier(MoteurDeJeu moteur) {
        Inventaire inventaire = moteur.getInventaireDuJoueur(getJoueur());
        if (inventaire.getEcus() < 5 ) {
            return false;
        }
        return true;
    }
    public String toString() {
        return "Le joueur a acheter une action";
    }

}
