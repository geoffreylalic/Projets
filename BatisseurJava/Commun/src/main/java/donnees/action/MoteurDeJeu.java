package donnees.action;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import donnees.Identification;
import donnees.Inventaire;
import donnees.PiocheBatimentVisible;
import donnees.PiocheOuvrierVisible;

import java.util.List;

public interface MoteurDeJeu {

    public Inventaire getInventaireDuJoueur(Identification joueur);

    public List<CarteBatiment> getPiocheBatimentApparent();

    public PiocheBatimentVisible getPiocheBatimentVisible();

    public PiocheOuvrierVisible getPiocheOuvrierVisible();

    public List<CarteOuvrier> getPiocheOuvrierApparent();

    public void retirerCarteBatiment(int index);
    public void retirerCarteOuvrier(int index);
}
