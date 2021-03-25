package joueur.ia;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import donnees.Identification;
import donnees.Inventaire;
import donnees.PiocheBatimentVisible;
import donnees.PiocheOuvrierVisible;
import donnees.action.*;

import java.util.List;

public class BotBatisseur extends Bot {

    // permet a chaque action de connaitre le total des ressources disponible dans la main d'ouvrier
    private int totalBoisDispo ;
    private int totalPierreDispo;
    private int totalSavoirDispo;
    private int totalTuileDispo;

    private Inventaire inv;

    public Action jouer(Inventaire inv, Identification moi, PiocheBatimentVisible piocheBatiment, PiocheOuvrierVisible piocheOuvrier) {

        this.inv = inv;


        //getChantierCourant() = fonction qui retourne le bâtiment entrain d'être construit, dans le cas où il y a plusieurs batiments non construit, on retourne celui qui est le plus proche d'être constuit, si il n'y en a pas, on return null
        CarteBatiment chantierCourant = getChantierCourant();

        if(inv.getEcus()>15&& inv.getNbAction() == 0){
            return new ActionAcheterAction(moi);
        }

        if (chantierCourant == null) {
            //choix bâtiment permet de déterminer la meilleure carte bâtiment à piocher en fonction des ouvriers du joueur
            return new ActionPiocherBatiment(moi, getChoixBatiment(piocheBatiment.getPiocheBatimentVisible()));
        }



        //si on n'a pas les ressources nécessaires pour construire, on pioche une carte ouvrier qui va nous aider au mieux
        if (detecterRessources(chantierCourant) == false) {
            return new ActionPiocherOuvrier(moi, getChoixOuvrier(chantierCourant, piocheOuvrier.getPiocheOuvrierVisible()));
        }

        // getOuvrierAAffecter() : regarde si un ouvrier peut terminer une construction et renvoie l'ouvrier le plus économique en ressources
        CarteOuvrier ouvrierAAffecter = getOuvrierAAffecter(chantierCourant);

        if (ouvrierAAffecter != null) {
            // si l'opération est trop chère on passe son tour
            if (this.getArgent() >= ouvrierAAffecter.getCout()) {
                return new ActionAffecterOuvrier(moi,ouvrierAAffecter,chantierCourant);
            }
            return  new ActionPasserAction(moi);
        }

        // dans le cas ou il n'y a pas d'ouvrier parfait, prendre l'ouvrier qui match le mieux avec la construcution
        CarteOuvrier ouvrierMatch = getOuvrierMatch(chantierCourant);

        if (this.getArgent() >= ouvrierMatch.getCout()) {
            return new ActionAffecterOuvrier(moi,ouvrierMatch,chantierCourant);
        }
        return  new ActionPasserAction(moi);

    }


    //détecter si le joueur a les ressources nécessaire pour son chantier courant : celui avec le moins de ressources inutiles
    private boolean detecterRessources(CarteBatiment chantierCourant) {

        //calcul les ressources disponibles dans main ouvrier et les inscrit dans les variables de classe
        setTotalRessourcesDispo();

        //si on a assez de ressources disponible pour construire on retourne true
        if ( totalPierreDispo >= chantierCourant.getPierre() && totalBoisDispo >= chantierCourant.getBois() && totalSavoirDispo >= chantierCourant.getSavoir() && totalTuileDispo >= chantierCourant.getTuile()) {
            return true;
        }
        return false;
    }

    private CarteOuvrier getOuvrierMatch(CarteBatiment chantierCourant) {
        int i;
        int bestScore = 99999;
        CarteOuvrier ouvrierMatch = null;
        for (CarteOuvrier ouvrier :
                this.inv.getMainOuvrier()) {
            if (ouvrier.getDisponible()) {
                int scoreOuvrier = compareMatchOuvrier(ouvrier.getPierre(), chantierCourant.getPierre()) + compareMatchOuvrier(ouvrier.getBois(), chantierCourant.getBois()) + compareMatchOuvrier(ouvrier.getTuile(), chantierCourant.getTuile()) + compareMatchOuvrier(ouvrier.getSavoir(), chantierCourant.getSavoir());
                if (scoreOuvrier < bestScore ) {
                    bestScore = scoreOuvrier;
                    ouvrierMatch =  ouvrier;
                }
            }

        }
        return ouvrierMatch;
    }

    //fonction pour décomposer getOuvrierMatch : permet de trouver "le surplus" de ressource ouvrier
    private int compareMatch(int rOuvrier, int rBatiment) {
        if (rOuvrier > rBatiment) {
            return rOuvrier - rBatiment;
        }
        return 0;
    }



    //fonction pour décomposer getOuvrierAAffecter : permet de trouver "le surplus" de ressource ouvrier pouvant construire
    private int compareMatchOuvrier(int rOuvrier, int rBatiment) {
        if (rBatiment > rOuvrier) {
            return 0;
        }
        return  rOuvrier-rBatiment  ;
    }

    //regarde si un ouvrier peut terminer une construction et renvoie l'ouvrier le plus économique en ressources
    private CarteOuvrier getOuvrierAAffecter(CarteBatiment chantierCourant) {
        int bestScore = 99999;
        CarteOuvrier ouvrierAAffecter = null;
        for (CarteOuvrier ouvrier :
                this.inv.getMainOuvrier()) {
            if (ouvrier.getDisponible() &&ouvrier.getBois() >= chantierCourant.getBois() && ouvrier.getTuile() >= chantierCourant.getTuile() && ouvrier.getPierre() >= chantierCourant.getPierre() && ouvrier.getSavoir() >= chantierCourant.getSavoir()) {
                int scoreOuvrier = compareMatchOuvrier(ouvrier.getPierre(), chantierCourant.getPierre()) + compareMatchOuvrier(ouvrier.getBois(), chantierCourant.getBois()) + compareMatchOuvrier(ouvrier.getTuile(), chantierCourant.getTuile()) + compareMatchOuvrier(ouvrier.getSavoir(), chantierCourant.getSavoir()) ;
                if (scoreOuvrier < bestScore ) {
                    bestScore = scoreOuvrier;
                    ouvrierAAffecter = ouvrier;
                }
            }
        }

        return ouvrierAAffecter;
    };

    // fonction qui retourne le chantier que le joueur efffectue, dans le cas où il y a plusieurs batiments non construit, on retourne celui le plus proche d'être constuit
    private CarteBatiment getChantierCourant () {

        CarteBatiment chantierCourant = null;
        int bestScore = 99999;

        for (CarteBatiment batiment:
                this.inv.getMainBatiment()) {
            if (batiment.getConstruit() != true) {
                int scoreCarte = batiment.getBois() + batiment.getTuile() + batiment.getSavoir() + batiment.getPierre();
                if (scoreCarte < bestScore) {
                    bestScore = scoreCarte;
                    chantierCourant = batiment;
                }
            }
        }

        return chantierCourant;
    }

    // méthode qui permet de trouver la meilleure carte bâtiment à piocher pour Bernard
    private CarteBatiment getChoixBatiment(List<CarteBatiment> piocheBatiment) {

        if (piocheBatiment.size() == 0) {
            return null;
        }

        int indexChoix = 0;
        int bestScore = 99999;

        setTotalRessourcesDispo();

        // on cherche si un batiment est constructible avec les ressources actuelles et si oui on le retourne
        for (CarteBatiment batiment :
                piocheBatiment) {
            if (batiment.getPierre() <= totalPierreDispo && batiment.getBois() <= totalBoisDispo && batiment.getTuile() <= totalTuileDispo && batiment.getSavoir() <= totalSavoirDispo) {
                return batiment;
            }
        }

        for (CarteBatiment batiment :
                piocheBatiment) {
            int scoreBatiment = compareMatch(totalBoisDispo, batiment.getBois()) + compareMatch(totalTuileDispo, batiment.getTuile()) + compareMatch(totalSavoirDispo, batiment.getSavoir()) + compareMatch(totalPierreDispo, batiment.getPierre()) ;
            if (scoreBatiment < bestScore ) {
                indexChoix = piocheBatiment.indexOf(batiment);
                bestScore = scoreBatiment;
            }
        }
        return piocheBatiment.get(indexChoix);
    }


    // méthode qui permet de trouver la meilleure carte ouvrier pour Bernard
    private CarteOuvrier getChoixOuvrier( CarteBatiment chantierCourant, List<CarteOuvrier> piocheOuvrier) {

        int indexChoix = 0;
        int bestScore = 99999;

        // permet de mettre à jour les variables de classe correspondant auc ressources dispnibles
        setTotalRessourcesDispo();

        // on prend les ressources nécessaire pour construire
        int PierreIdeal = chantierCourant.getPierre() ;
        int TuileIdeal = chantierCourant.getTuile() ;
        int SavoirIdeal = chantierCourant.getSavoir() ;
        int BoisIdeal = chantierCourant.getBois() ;



        //le joueur verifie si le paquet n'est pas vide ou inextistant
        if (piocheOuvrier == null || piocheOuvrier.size()==0) {
            return null;
        }

        // on cherche l'ouvrier avec la meilleure correspondance : celui qui contribue le mieux a la construction, qui a le score le plus bas
        for (CarteOuvrier ouvrier:
                piocheOuvrier) {
            int scoreOuvrier = compareMatch(ouvrier.getBois(), BoisIdeal) + compareMatch(ouvrier.getSavoir(), SavoirIdeal) + compareMatch(ouvrier.getTuile(), TuileIdeal) +compareMatch(ouvrier.getPierre(), PierreIdeal);
            if (scoreOuvrier < bestScore) {
                bestScore = scoreOuvrier;
                indexChoix = piocheOuvrier.indexOf(ouvrier);
            }
        }
        return piocheOuvrier.get(indexChoix);

    }

    private void setTotalRessourcesDispo () {
        this.totalBoisDispo = 0;
        this.totalSavoirDispo = 0;
        this.totalTuileDispo = 0;
        this.totalPierreDispo = 0;
        for (CarteOuvrier ouvrier:
                this.inv.getMainOuvrier()) {
            if (ouvrier.getDisponible()) {
                this.totalBoisDispo += ouvrier.getBois();
                this.totalSavoirDispo += ouvrier.getSavoir();
                this.totalTuileDispo += ouvrier.getTuile();
                this.totalPierreDispo += ouvrier.getPierre();
            }
        }

    }

    public CarteOuvrier getMainOuvrier(int i) {
        return this.inv.getMainOuvrier(i);
    }

    public CarteBatiment getMainBatiment(int i) {
        return this.inv.getMainBatiment(i);
    }

    public int getArgent() {
        return this.inv.getEcus();
    }

}
