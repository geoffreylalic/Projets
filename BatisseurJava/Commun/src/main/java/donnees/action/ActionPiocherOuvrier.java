package donnees.action;

import cartes.CarteOuvrier;
import donnees.Identification;
import donnees.Inventaire;

import java.util.List;

public class ActionPiocherOuvrier extends Action {



    private CarteOuvrier carteChoix;

    public ActionPiocherOuvrier() {

    }

    public ActionPiocherOuvrier(Identification id, CarteOuvrier carteChoix) {
        setJoueur(id);
        setCarteChoix(carteChoix);
    }

    @Override
    public void appliquerAction(MoteurDeJeu moteur) {
        Inventaire inventaire = moteur.getInventaireDuJoueur(getJoueur());
        inventaire.decrementNbAction();
        inventaire.addCarteOuvrier(this.carteChoix);
        moteur.retirerCarteOuvrier(moteur.getPiocheOuvrierVisible().trouverCarte(this.carteChoix));
    }

    @Override
    public boolean verifier(MoteurDeJeu moteur) {
        List<CarteOuvrier> piocheOuvrier = moteur.getPiocheOuvrierApparent();
        if (piocheOuvrier.size() == 0 || piocheOuvrier == null ||moteur.getPiocheOuvrierVisible().trouverCarte(this.carteChoix)==-1 || carteChoix==null) {
            return false;
        }
        return true;
    }

    public void setCarteChoix(CarteOuvrier carteChoix) {
        this.carteChoix = carteChoix;
    }

    public CarteOuvrier getCarteChoix() {
        return carteChoix;
    }

    public String toString() {
        return "piocher  "+ carteChoix;
    }

}