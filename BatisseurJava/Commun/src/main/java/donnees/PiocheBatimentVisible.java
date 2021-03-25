package donnees;

import cartes.CarteBatiment;

import java.util.ArrayList;
import java.util.List;

public class PiocheBatimentVisible {
    private List<CarteBatiment> piocheBatimentVisible;
    public PiocheBatimentVisible(){
        piocheBatimentVisible =  new ArrayList();
    }

    public PiocheBatimentVisible(List<CarteBatiment> cartes){
        piocheBatimentVisible =  new ArrayList();
        piocheBatimentVisible.addAll(cartes);
    }

    public List<CarteBatiment> getPiocheBatimentVisible() {
        return piocheBatimentVisible;
    }

    public void setPiocheBatimentVisible(List<CarteBatiment> piocheBatimentVisible) {
        this.piocheBatimentVisible = piocheBatimentVisible;
    }


    public int trouverCarte(CarteBatiment carteChoix){
        int i;
        for (i=0; i < piocheBatimentVisible.size(); i++){
            if (comparaisonCarte(piocheBatimentVisible.get(i),carteChoix)){
                return i;
            }
        }
        return -1;
    }


    public boolean comparaisonCarte(CarteBatiment c1,CarteBatiment c2){
        if (c1.getNom().equals(c2.getNom()) && c1.getBois()==c2.getBois()
                && c1.getPierre()==c2.getPierre() && c1.getSavoir()==c2.getSavoir() && c1.getTuile()==c2.getTuile() && c1.getBoisOutil()==c2.getBoisOutil()
                && c1.getPierreOutil()==c2.getPierreOutil() && c1.getSavoirOutil()==c2.getSavoirOutil() && c1.getTuileOutil()==c2.getTuileOutil()
                && c1.getConstruit()== c2.getConstruit() ){
            return true;
        }
        return false;
    }
}
