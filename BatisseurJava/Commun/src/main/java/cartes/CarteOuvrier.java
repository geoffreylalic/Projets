package cartes;

public class CarteOuvrier {

    private int id;
    private String type;
    private int cout;
    private boolean disponible;
    private int pierre;
    private int tuile;
    private int savoir;
    private int bois;

    public CarteOuvrier(int id,String type, int cout, int pierre,int tuile,int Bois, int Savoir) {
        setId(id);
        setType(type);
        setCout(cout);
        setPierre(pierre);
        setTuile(tuile);
        setSavoir(Savoir);
        setBois(Bois);
        setDisponible(true);
    }

    public CarteOuvrier(){
        this(1,"apprenti", 2,1,1,1,1);
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public void setCout(int cout) {
        this.cout = cout;
    }

    public int getCout() {
        return cout;
    }

    public void setPierre(int pierre) {
        this.pierre = pierre;
    }

    public int getPierre() {
        return pierre;
    }

    public void setTuile(int tuile) {
        this.tuile = tuile;
    }

    public int getTuile() {
        return tuile;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean getDisponible() {
        return disponible;
    }

    @Override
    public String toString() {
        return  "ouvrier: "+
                "id=" + this.id +
                ", type=" + this.type +
                ", disponible=" + this.disponible +
                ", cout=" + this.cout +
                ", pierre=" + this.pierre +
                ", tuile=" + this.tuile +
                ", bois=" + this.bois +
                ", savoir=" + this.savoir;

    }

    public void setSavoir(int savoir) {
        this.savoir = savoir;
    }

    public int getSavoir() {
        return savoir;
    }

    public void setBois(int bois) {
        this.bois = bois;
    }

    public int getBois() {
        return bois;
    }
}
