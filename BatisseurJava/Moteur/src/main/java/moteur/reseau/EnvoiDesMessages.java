package moteur.reseau;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import donnees.Identification;
import donnees.Inventaire;
import donnees.PiocheBatimentVisible;
import donnees.PiocheOuvrierVisible;

import java.util.List;

public interface EnvoiDesMessages {

    void permettreConnexion();

    void envoyerSignalFin(Identification gagnant);

    void demandeAuJoueurDeJouer(Identification j, Inventaire inventaireDuJoueur,PiocheBatimentVisible piocheBatimentVisible, PiocheOuvrierVisible piocheOuvrierVisible);
}
