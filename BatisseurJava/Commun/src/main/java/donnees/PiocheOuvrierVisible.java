package donnees;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;

import java.util.ArrayList;
import java.util.List;

public class PiocheOuvrierVisible {
    private List<CarteOuvrier> piocheOuvrierVisible;

    public PiocheOuvrierVisible() {
        piocheOuvrierVisible = new ArrayList();
    }

    public PiocheOuvrierVisible(List<CarteOuvrier> cartes) {
        piocheOuvrierVisible = new ArrayList();
        piocheOuvrierVisible.addAll(cartes);
    }

    public List<CarteOuvrier> getPiocheOuvrierVisible() {
        return piocheOuvrierVisible;
    }

    public void setPiocheOuvrierVisible(List<CarteOuvrier> piocheOuvrierVisible) {
        this.piocheOuvrierVisible = piocheOuvrierVisible;
    }
    public int trouverCarte(CarteOuvrier carteChoisi){
        int i;
        for (i=0; i < piocheOuvrierVisible.size(); i++){
            if (comparaisonCarte(piocheOuvrierVisible.get(i),carteChoisi)){
                return i;
            }
        }
        return -1;
    }


    private boolean comparaisonCarte(CarteOuvrier c1,CarteOuvrier c2){
        if (c1.getType().equals(c2.getType()) && c1.getBois()==c2.getBois()
                && c1.getPierre()==c2.getPierre() && c1.getSavoir()==c2.getSavoir() && c1.getTuile()==c2.getTuile()
                && c1.getDisponible()== c2.getDisponible()&& c1.getId()==c2.getId() ){
            return true;
        }
        return false;
    }
}
