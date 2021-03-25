package donnees.action;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import donnees.Identification;
import donnees.Inventaire;

public class ActionAffecterOuvrier extends Action {
    CarteBatiment carteBatiment;
    CarteOuvrier carteOuvrier;
    Inventaire joueurAction;

    public ActionAffecterOuvrier() {

    }

    public ActionAffecterOuvrier(Identification id, CarteOuvrier carteOuvrier, CarteBatiment carteBatiment) {
        setJoueur(id);
        this.carteOuvrier = carteOuvrier;
        this.carteBatiment = carteBatiment;
    }

    @Override
    public void appliquerAction(MoteurDeJeu moteur) {
        joueurAction = moteur.getInventaireDuJoueur(getJoueur());
        carteBatiment=joueurAction.getMainBatiment().get(joueurAction.trouverCarteBatiment(carteBatiment));
        carteOuvrier=joueurAction.getMainOuvrier().get(joueurAction.trouverCarteOuvrier(carteOuvrier));
        moteur.getInventaireDuJoueur(getJoueur()).decrementNbAction();
        if (carteOuvrier.getDisponible() == true) { // si l'ouvrier est diponible nous pouvons l'affecter
            if (carteBatiment.getConstruit() == false) {//si le chantier n'est pas construit nous pouvons commencer le chantier
                carteOuvrier.setDisponible(false);
                carteBatiment.addChantierOuvrier(carteOuvrier);

                //si une des ressources n'est pas a 0 l'ouvrier construit
                carteBatiment.setBois(carteBatiment.getBois() - carteOuvrier.getBois());
                carteBatiment.setPierre(carteBatiment.getPierre() - carteOuvrier.getPierre());
                carteBatiment.setSavoir(carteBatiment.getSavoir() - carteOuvrier.getSavoir());
                carteBatiment.setTuile(carteBatiment.getTuile() - carteOuvrier.getTuile());
                joueurAction.setEcus(joueurAction.getEcus() - carteOuvrier.getCout());
                //joueurAction.setDepenseOuvrier(joueurAction.getDepenseOuvrier()+carteOuvrier.getCout());
                if (carteBatiment.getBois() <= 0 && carteBatiment.getTuile() <= 0 && carteBatiment.getSavoir() <= 0 && carteBatiment.getPierre() <= 0) {
                    // si toute les ressources sont a 0 me batiment est construit
                    carteBatiment.setConstruit(true);
                    CarteOuvrier machine = carteBatiment.isMachine();
                    if (machine != null) {
                        joueurAction.getMainOuvrier().add(machine);
                    }

                    joueurAction.setCouronnes(joueurAction.getCouronnes() + carteBatiment.getPoints());
                    for (int i = 0; i < joueurAction.getMainBatiment().size(); i++) {
                        for (int j = 0; j < joueurAction.getMainBatiment().get(i).getChantierOuvrier().size(); j++) {
                            joueurAction.getMainBatiment().get(i).getChantierOuvrier().get(j).setDisponible(true);
                        }
                    }
                    joueurAction.setRevenusBatiment(joueurAction.getRevenusBatiment() + carteBatiment.getEcu());
                    joueurAction.setEcus(carteBatiment.getEcu()+joueurAction.getEcus());
                    // joueurAction.setChantier(joueurAction.getChantier()+1);
                }
            }
        }

    }



    @Override
    public boolean verifier(MoteurDeJeu moteur) {
        joueurAction = moteur.getInventaireDuJoueur(getJoueur());
        if (joueurAction.getMainOuvrier().get(joueurAction.trouverCarteOuvrier(carteOuvrier)).getDisponible() == false) {//si l'ouvrier n'est pas diponible nous pouvons pas l'affecter
            System.out.println("peut pas affecter");
            return false;
        } else if (joueurAction.getMainBatiment().get(joueurAction.trouverCarteBatiment(carteBatiment)).getConstruit() == true) {
            System.out.println("peut pas affecter");
            return false;
        } else if (moteur.getInventaireDuJoueur(getJoueur()).trouverCarteOuvrier(carteOuvrier) == -1
                || moteur.getInventaireDuJoueur(getJoueur()).trouverCarteBatiment(carteBatiment) == -1) {
            System.out.println("peut pas affecter");
            return false;
        }
        return true;
    }

    public CarteBatiment getCarteBatiment() {
        return carteBatiment;
    }

    public void setCarteBatiment(CarteBatiment carteBatiment) {
        this.carteBatiment = carteBatiment;
    }

    public CarteOuvrier getCarteOuvrier() {
        return carteOuvrier;
    }

    public void setCarteOuvrier(CarteOuvrier carteOuvrier) {
        this.carteOuvrier = carteOuvrier;
    }

    public Inventaire getJoueurAction() {
        return joueurAction;
    }

    public void setJoueurAction(Inventaire joueurAction) {
        this.joueurAction = joueurAction;
    }

    public String toString() {
        return "affecter ouvrier : "+ getCarteOuvrier() +" "+ getCarteBatiment();
    }

}

