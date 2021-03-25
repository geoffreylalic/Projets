package moteur;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import donnees.Identification;
import donnees.Inventaire;
import donnees.PiocheBatimentVisible;
import donnees.PiocheOuvrierVisible;
import donnees.action.MoteurDeJeu;
import moteur.paquets.PaquetBatiment;
import moteur.paquets.PaquetOuvrier;
import moteur.reseau.RéceptionDesMessages;

import java.security.SecureRandom;
import java.util.*;

public class Moteur  implements MoteurDeJeu {

    private HashMap<Identification, Inventaire> inventaires = new HashMap<>();
    private PaquetBatiment paquetBatiment;
    private PaquetOuvrier paquetOuvrier;

    private int NB_MAX_JOUEUR;

    public Moteur(Serveur serveur, int NB_MAX_JOUEUR, String nomPaquet) {
        setNB_MAX_JOUEUR(NB_MAX_JOUEUR);
        if (nomPaquet.equals("Antiquite") || nomPaquet.equals("antiquite")) {
            paquetBatiment = new PaquetBatiment("BatimentAntiquite");
            paquetOuvrier = new PaquetOuvrier("OuvrierAntiquite");
        } else {
            paquetBatiment = new PaquetBatiment("CarteBatiment");
            paquetOuvrier = new PaquetOuvrier("CarteOuvrier");
        }

    }

    /**
     * s'il y a encore de la place, on ajoute un joueur
     * @param id l'identificaiton du nouveau joueur
     * @return true si l'ajout est effectif, faux si le joueur est rejeté
     */
    public boolean ajouterJoueur(Identification id) {
        Set joueurs = inventaires.keySet();
        if ((joueurs.size() < NB_MAX_JOUEUR) && (! joueurs.contains(id))) {
            inventaires.put(id, new Inventaire());
            return true;
        }
        return false;
    }

    /**
     * pour savoir si la partie est prete à démarrer
     * @return vrai s'il y a NB_MAX_JOUEUR d'enregistrer
     */
    public boolean estPartieComplete() {
        Set joueurs = inventaires.keySet();
        return (joueurs.size() >= NB_MAX_JOUEUR);
    }


    /**
     * @return vrai si la partie est finie ( 17 couronnes)
     */
    public boolean estPartieFinie() {
        Iterator<Identification> joueurs = inventaires.keySet().iterator();
        Identification j ;
        while (joueurs.hasNext()) {
            j = joueurs.next();
            if(inventaires.get(j).getCouronnes() > 16) {
                return true;
            }
        }
        return false;
    }

    /**
     * pour déterminer le gagnant
     *  @return le gagnant
     */
    public Identification getGagnant() {
        if (estPartieFinie()) {
            Iterator<Identification> joueurs = inventaires.keySet().iterator();
            Identification j ;
            Identification gagnant = null;
            int nbmax = -1000;
            int maxPiece = 0;
            while (joueurs.hasNext()) {
                j = joueurs.next();
                int nbPoints = inventaires.get(j).getCouronnes();
                if (nbPoints > nbmax) {
                    nbmax = nbPoints;
                    maxPiece = inventaires.get(j).getEcus();
                    gagnant = j;
                }
                if (nbPoints == nbmax) { //on gere les egalite de couronnes avec les pieces
                    if(maxPiece < inventaires.get(j).getEcus()) {
                        maxPiece = inventaires.get(j).getEcus();
                        gagnant = j;
                    }
                }
            }

            return gagnant;
        }
        else return null;
    }

    /**
     * Pour faire le lien entre une identification et l'iventaire d'un joueur
     * @param joueur le joueur (via son identification) dont on veut l'inventaire
     * @return l'inventaire du joueur concerné ou null si le joueur n'est pas identifié
     */
    @Override
    public Inventaire getInventaireDuJoueur(Identification joueur) {
        return inventaires.get(joueur);
    }

    public void resetInventaireDuJoueur(Identification joueur) {
        inventaires.get(joueur).resetInv();
    }


    @Override
    public List<CarteBatiment> getPiocheBatimentApparent() {
        return paquetBatiment.getPiocheBatimentVisible();
    }

    @Override
    public PiocheBatimentVisible getPiocheBatimentVisible() {
        return paquetBatiment.getPaquetVisibleBatiment();
    }

    @Override
    public PiocheOuvrierVisible getPiocheOuvrierVisible() {
        return paquetOuvrier.getPaquetVisibleOuvrier();
    }

    @Override
    public List<CarteOuvrier> getPiocheOuvrierApparent() {
        return paquetOuvrier.getPiocheOuvrierVisible();
    }

    @Override
    public void retirerCarteBatiment(int index) {
        paquetBatiment.retirerCarteVisibleBatiment(index);
    }

    @Override
    public void retirerCarteOuvrier(int index) {
        paquetOuvrier.retirerCarteVisibleOuvrier(index);
    }


    /**
     * pour itérer sur les joueurs
     * @return un tableau de joueur
     */
    public Identification[] getJoueurs() {
        Set joueurs = inventaires.keySet();
        Identification[] résultat = new Identification[joueurs.size()];
        return  inventaires.keySet().toArray(résultat);
    }


    public int getNB_MAX_JOUEUR() {
        return NB_MAX_JOUEUR;
    }

    private void setNB_MAX_JOUEUR(int NB_MAX_JOUEUR) {
        this.NB_MAX_JOUEUR = NB_MAX_JOUEUR;
    }


    /*
    Méthodes pour gérer les pioches
     */
    public List<CarteBatiment> getPiocheVisibleBatiment () { return paquetBatiment.getPiocheBatimentVisible();}

    public List<CarteOuvrier> getPiocheVisibleOuvrier () { return  paquetOuvrier.getPiocheOuvrierVisible();}

    public PaquetOuvrier getPaquetOuvrier() {
        return paquetOuvrier;
    }

    public PaquetBatiment getPaquetBatiment() {
        return paquetBatiment;
    }
}
