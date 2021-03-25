package joueur.ia;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import donnees.Identification;
import donnees.Inventaire;
import donnees.PiocheBatimentVisible;
import donnees.PiocheOuvrierVisible;
import donnees.action.Action;

import java.util.List;

public abstract class Bot {
    public abstract Action jouer(Inventaire inv, Identification moi, PiocheBatimentVisible piocheBatiment, PiocheOuvrierVisible piocheOuvrier);
}
