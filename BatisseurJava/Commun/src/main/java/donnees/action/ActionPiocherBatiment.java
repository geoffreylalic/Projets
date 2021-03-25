package donnees.action;

import cartes.CarteBatiment;
import donnees.Identification;
import donnees.Inventaire;

import java.util.List;

public class ActionPiocherBatiment extends Action {

    private CarteBatiment carteChoix;

    public ActionPiocherBatiment() {

    }

    public ActionPiocherBatiment(Identification id,CarteBatiment carteChoix) {
        setJoueur(id);
        setCarteChoix(carteChoix);
    }

    @Override
    public void appliquerAction(MoteurDeJeu moteur) {
        Inventaire inventaire = moteur.getInventaireDuJoueur(getJoueur());
        inventaire.addCartebatiment(this.carteChoix);
        inventaire.decrementNbAction();
        moteur.retirerCarteBatiment(moteur.getPiocheBatimentVisible().trouverCarte(carteChoix));
    }

    @Override
    public boolean verifier(MoteurDeJeu moteur) {
        List<CarteBatiment> piocheBatiment = moteur.getPiocheBatimentApparent();
        if (piocheBatiment.size() == 0 || piocheBatiment == null|| moteur.getPiocheBatimentVisible().trouverCarte(carteChoix)==-1 || carteChoix==null)  {
            return false;
        }
        return true;
    }

    public CarteBatiment getCarteChoix() {
        return carteChoix;
    }

    public void setCarteChoix(CarteBatiment carteChoix) {
        this.carteChoix = carteChoix;
    }

    public String toString() {
        return "piocher " + carteChoix;
    }

}