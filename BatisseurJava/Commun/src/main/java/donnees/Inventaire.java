package donnees;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;

import java.util.ArrayList;

public class Inventaire {

    private int couronnes;
    private int ecus;
    private ArrayList<CarteBatiment> mainBatiment;
    private ArrayList<CarteOuvrier> mainOuvrier;
    private int nbAction;
    private int nbPasserDansTour;
    private int revenusBatiment;


    private int nbRecrue = 0;
    private int nbVictoireStats = 0;
    private int revenusBatimentStats = 0;
    private int couronnesStats = 0;

    public Inventaire() {
        setCouronnes(0);
        setEcus(10);
        resetNbAction();
        setRevenusBatiment(0);
        mainBatiment = new ArrayList<CarteBatiment>();
        mainOuvrier = new ArrayList<CarteOuvrier>();
        resetNbPasserDansTour();
    }

    /* methodes Couronnes*/
    public void setCouronnes(int couronnes) { this.couronnes = couronnes ;}

    public int getCouronnes() {return couronnes;}

    /**
     * méthode pratique pour ajouter des couronnes
     * @param nbCouronnesÀAjouter le nombre de couronnes à ajouter
     */
    public void ajouterCouronnes(int nbCouronnesÀAjouter) {
        setCouronnes(getCouronnes()+nbCouronnesÀAjouter);
    }


    /* methodes nbAction*/
    public int getNbAction() { return nbAction; }

    public void setNbAction(int nbAction) { this.nbAction = nbAction; }

    public void resetNbAction(){ setNbAction(3); }

    public void decrementNbAction() { setNbAction(getNbAction() - 1);}


    /* méthodes nbPasseDansTour*/
    public int getNbPasserDansTour() { return nbPasserDansTour; }

    public void setNbPasserDansTour(int nb ) { this.nbPasserDansTour = nb;}

    public void resetNbPasserDansTour () { setNbPasserDansTour(0);}

    public void incrementNbPasserDansTour() { if(getNbPasserDansTour() > 4) {setNbPasserDansTour(6);}else {setNbPasserDansTour(getNbPasserDansTour() + 1 );}}


    /* methodes Ecus*/
    public void setEcus(int ecus) {
        this.ecus = ecus;
    }

    public int getEcus() {
        return ecus;
    }

    /**
     * méthode pratique pour ajouter des ecus
     * @param nbEcusÀAjouter le nombre d'ecus à ajouter
     */
    public void ajouterEcus(int nbEcusÀAjouter) {
        setEcus(getEcus()+nbEcusÀAjouter);
    }


    /*méthodes main ouvrier*/
    public void setMainOuvrier (ArrayList<CarteOuvrier> nouvelleMainOuvrier) {this.mainOuvrier = nouvelleMainOuvrier; }

    public ArrayList<CarteOuvrier> getMainOuvrier() {
        return mainOuvrier;
    }

    public CarteOuvrier getMainOuvrier(int i) {
        return this.mainOuvrier.get(i);
    }

    public void addCarteOuvrier(CarteOuvrier ouvrier) {this.mainOuvrier.add(ouvrier);}


    /*méthodes main batiment*/
    public void setMainBatiment (ArrayList<CarteBatiment> nouvelleMainBatiment) {this.mainBatiment = nouvelleMainBatiment; }

    public ArrayList<CarteBatiment> getMainBatiment() {
        return mainBatiment;
    }

    public CarteBatiment getMainBatiment(int i) {
        return mainBatiment.get(i);
    }

    public void addCartebatiment(CarteBatiment batiment) { this.mainBatiment.add(batiment);}


    public String toString() {
        return "[nbecus : "+getEcus()+", nbcouronnes : "+getCouronnes()+ ", nbActions : "+getNbAction()+", revenusBatiment : "+getRevenusBatiment()+", mainBatiment : "+getMainBatiment().toString()+", getMainOuvrier : "+getMainOuvrier().toString()+"]";
    }

    public int getRevenusBatiment() {
        return revenusBatiment;
    }

    public void setRevenusBatiment(int revenusBatiment) {
        this.revenusBatiment = revenusBatiment;
    }

    // trouver si la carte batiment existe dans la main du joueur
    public int trouverCarteBatiment(CarteBatiment carteChoix){
        int i;
        for (i=0; i < mainBatiment.size(); i++){
            if (comparaisonCarteBatiment(mainBatiment.get(i),carteChoix)){
                return i;
            }
        }
        return -1;
    }

    // comparaison de 2 cartes batiments
    public boolean comparaisonCarteBatiment(CarteBatiment c1,CarteBatiment c2){
        if (c1.getNom().equals(c2.getNom()) && c1.getBois()==c2.getBois()
                && c1.getPierre()==c2.getPierre() && c1.getSavoir()==c2.getSavoir() && c1.getTuile()==c2.getTuile() && c1.getBoisOutil()==c2.getBoisOutil()
                && c1.getPierreOutil()==c2.getPierreOutil() && c1.getSavoirOutil()==c2.getSavoirOutil() && c1.getTuileOutil()==c2.getTuileOutil()
                && c1.getConstruit()== c2.getConstruit() ){
            return true;
        }
        return false;
    }

    public int trouverCarteOuvrier(CarteOuvrier carteChoix){
        int i;
        for (i=0; i < mainOuvrier.size(); i++){
            if (comparaisonCarteOuvrier(mainOuvrier.get(i),carteChoix)){
                return i;
            }
        }
        return -1;
    }

    public void resetInv(){
        setCouronnes(0);
        setEcus(10);
        resetNbAction();
        setRevenusBatiment(0);
        resetNbPasserDansTour();
        mainBatiment = new ArrayList<CarteBatiment>();
        mainOuvrier = new ArrayList<CarteOuvrier>();
    }

    public void setAllStats(Boolean gagnant) {
        if (gagnant) {
            this.nbVictoireStats ++;
        }
        this.nbRecrue += getMainOuvrier().size();
        this.revenusBatimentStats += revenusBatiment;
        this.couronnesStats += couronnes;
    }

    public int getNbVictoireStats() {
        return nbVictoireStats;
    }


    public int getRevenusBatimentStats() {
        return revenusBatimentStats;
    }

    public int getCouronnesStats() {
        return couronnesStats;
    }

    public int getNbRecrue() {
        return nbRecrue;
    }

    public void setNbRecrue(int nbRecrue) {
        this.nbRecrue = nbRecrue;
    }

    public void setNbVictoireStats(int nbVictoireStats) {
        this.nbVictoireStats = nbVictoireStats;
    }

    public void setRevenusBatimentStats(int revenusBatimentStats) {
        this.revenusBatimentStats = revenusBatimentStats;
    }

    public void setCouronnesStats(int couronnesStats) {
        this.couronnesStats = couronnesStats;
    }

    public boolean comparaisonCarteOuvrier(CarteOuvrier c1,CarteOuvrier c2){
        if (c1.getType().equals(c2.getType()) && c1.getBois()==c2.getBois()
                && c1.getPierre()==c2.getPierre() && c1.getSavoir()==c2.getSavoir() && c1.getTuile()==c2.getTuile()
                && c1.getDisponible()== c2.getDisponible()&& c1.getId()==c2.getId() ){
            return true;
        }
        return false;
    }
}
