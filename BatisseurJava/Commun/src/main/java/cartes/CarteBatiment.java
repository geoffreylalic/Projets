package cartes;

import java.util.ArrayList;

public class CarteBatiment {
    private String nom;
    private int points;
    private int ecu;
    ;
    private boolean construit;//si il est construit ou pas.
    private int bois;
    private int savoir;
    private int tuile;
    private int pierre;
    private int boisOutil;
    private int savoirOutil;
    private int tuileOutil;
    private int pierreOutil;
    private ArrayList<CarteOuvrier> chantierOuvrier; // arraylist d'ouvrier qui sont actuellement dans le chatier
    //Directement false car la carte n'est pas construite au debut donc c'est un chantier

    public CarteBatiment(String nom, int bois, int savoir, int tuile, int pierre, int ecu, int points, int pierreOutil, int tuileOutil, int savoirOutil, int boisOutil) {
        setNom(nom);
        setBois(bois);
        setSavoir(savoir);
        setTuile(tuile);
        setPierre(pierre);
        setEcu(ecu);
        setPoints(points);
        setPierreOutil(pierreOutil);
        setTuileOutil(tuileOutil);
        setSavoirOutil(savoirOutil);
        setBoisOutil(boisOutil);
        setConstruit(false);
        chantierOuvrier=new ArrayList<CarteOuvrier>();
    }


    public CarteBatiment(){
        this("eglise", 2,0,0,0,0,0,0,0,0,0);
        setConstruit(false);
    }

//*************************************** GETTER & SETTER ***************************************//

    /**
     *
     * @return
     */
    public boolean getConstruit() {
        return construit;
    }
    /**
     *
     * @param construction
     */
    public void setConstruit(boolean construction) {
        this.construit = construction;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setBois(int bois) {
        if (bois <= 0 ) {
            this.bois = 0;
        } else {
            this.bois = bois;
        }
    }

    public int getBois() {
        return bois;
    }

    public void setSavoir(int savoir) {
        if (savoir <= 0) {
            this.savoir = 0;
        } else {
            this.savoir = savoir;
        }
    }

    public int getSavoir() {
        return savoir;
    }

    public void setTuile(int tuile) {
        if (tuile <=0 ) {
            this.tuile = 0;
        } else {
            this.tuile = tuile;
        }
    }

    public int getTuile() {
        return tuile;
    }

    public void setPierre(int pierre) {
        if (pierre < 0 ) {
            this.pierre = 0;
        } else {
            this.pierre = pierre;
        }
    }

    public int getPierre() {
        return pierre;
    }

    public void setEcu(int ecu) {
        this.ecu = ecu;
    }

    public int getEcu() {
        return ecu;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setBoisOutil(int boisOutil) {
        this.boisOutil = boisOutil;
    }

    public int getBoisOutil() {
        return boisOutil;
    }

    public void setSavoirOutil(int savoirOutil) {
        this.savoirOutil = savoirOutil;
    }

    public int getSavoirOutil() {
        return savoirOutil;
    }

    public void setTuileOutil(int tuileOutil) {
        this.tuileOutil = tuileOutil;
    }

    public int getTuileOutil() {
        return tuileOutil;
    }

    public void setPierreOutil(int pierreOutil) {
        this.pierreOutil = pierreOutil;
    }

    public int getPierreOutil() {
        return pierreOutil;
    }

    @Override
    public String toString() {
        return  "batiment: "+
                "nom='" + nom + '\'' +
                ", points=" + points +
                ", ecu=" + ecu +
                ", construit=" + construit +
                ", bois=" + bois +
                ", savoir=" + savoir +
                ", tuile=" + tuile +
                ", pierre=" + pierre +
                ", boisOutil=" + boisOutil +
                ", savoirOutil=" + savoirOutil +
                ", tuileOutil=" + tuileOutil +
                ", pierreOutil=" + pierreOutil;
    }

    public ArrayList<CarteOuvrier> getChantierOuvrier() {
        return chantierOuvrier;
    }

    public void addChantierOuvrier(CarteOuvrier ouvrier) {
        chantierOuvrier.add(ouvrier);
    }

    public CarteOuvrier isMachine(){
        if (this.getBoisOutil()==0 && this.getSavoirOutil()==0 && this.getTuileOutil()==0 && this.getPierreOutil()==0 && this.getConstruit()==true){
            return null;
        }else if (this.getBoisOutil()>0 || this.getSavoirOutil()>0 || this.getTuileOutil()>0 || this.getPierreOutil()==0 || this.getConstruit()==true){
            CarteOuvrier machine;
            machine = new CarteOuvrier(0,this.getNom(),0,this.getPierreOutil(),this.getTuileOutil(),this.getBoisOutil(),this.getSavoirOutil());
            return machine;
        }
        return null;
    }
}
