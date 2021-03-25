package moteur.paquets;

import cartes.CarteOuvrier;
import com.fasterxml.jackson.databind.ObjectMapper;
import donnees.PiocheOuvrierVisible;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PaquetOuvrier {
    private static final String PATH_BAT = "/bat/";
    private String PATH = "../../bat/";
    private String nomdupaquet;
    /* paquet ouvrier caché et visible */
    private List<CarteOuvrier> paquetOuvrier;// paquet face cachée
    private PiocheOuvrierVisible paquetVisibleOuvrier;//paquet visible

    /**
     * Le constructeur permet de créer une liste paquetOuvrier,issue fichier json CarteOuvrier.json et paquetVisibleOuvrier qui pioche 5 cartes aléatoirement
     */
    public PaquetOuvrier(String nomdupaquet) {
        this.nomdupaquet = nomdupaquet;
        resetPaquet();
    }

    /**
     * permet de retourner la taille du paquet visible
     *
     * @return
     */

    public int getSizePaquetVisibleOuvrier() { //retourne la taille du paquet visible
        return paquetVisibleOuvrier.getPiocheOuvrierVisible().size();

    }

    public CarteOuvrier getCarteVisibleOuvrier(int i) { //retourne une carte visible selon le choix du joueur
        if (paquetVisibleOuvrier == null) {
            return null; //retourne null si la carte n'existe pas
        }
        return paquetVisibleOuvrier.getPiocheOuvrierVisible().get(i);

    }

    /**
     * permet de retirer une carte visible choisi par le joueur l'ajouter
     * ajouter une nouvelle carte issue du paquet caché aléatoirement
     * retire du paquet caché la carte ajouter dans le paquet visible
     *
     * @param i
     */
    public void retirerCarteVisibleOuvrier(int i) { //retire une carte visible choisi par le joueur retire
        if (paquetVisibleOuvrier.getPiocheOuvrierVisible().size() == 0) {
            return;
        }
        paquetVisibleOuvrier.getPiocheOuvrierVisible().remove(i);
        int max = paquetOuvrier.size();
        if (max == 0) {
            return;
        } else {
            while (paquetVisibleOuvrier.getPiocheOuvrierVisible().size() < 5) { //pioche une carte dans le paquet cacher pour que le paquet visible reste a 5
                paquetVisibleOuvrier.getPiocheOuvrierVisible().add(paquetOuvrier.get(paquetOuvrier.size()-1));
                paquetOuvrier.remove(paquetOuvrier.get(paquetOuvrier.size()-1));
            }
        }

    }

    /**
     * permet de retourner une carte dans le paquet cache
     *
     * @param i
     * @return
     */
    public CarteOuvrier getCartePaquetOuvrier(int i) { //retourne une carte dans le paquet caché
        if (paquetOuvrier == null) {
            return null; //retourne null si la carte du paquet caché n'existe pas
        }
        return paquetOuvrier.get(i);

    }

    /**
     * permet de retourner la liste du paquet visible
     *
     * @return
     */
    public List<CarteOuvrier> getPiocheOuvrierVisible() { //retourne la liste du paquet visible
        return paquetVisibleOuvrier.getPiocheOuvrierVisible();

    }

    /**
     * permet de retourner la taille du paquet caché
     *
     * @return
     */
    public int getSizePaquetOuvrier() { // retourne la taille du paquet caché
        return paquetOuvrier.size();

    }

    /**
     * permet de retirer une carte choisi du paquet caché
     *
     * @param i
     */
    public void retirerCartePaquetOuvrier(int i) {  //retire une carte choisi du paquet caché
        paquetOuvrier.remove(i);

    }

    /**
     * permet de retourne la liste du paquet caché
     *
     * @return
     */
    public List<CarteOuvrier> getPaquetOuvrier() { //retourne la liste du paquet caché
        return paquetOuvrier;

    }

    public PiocheOuvrierVisible getPaquetVisibleOuvrier() {
        return paquetVisibleOuvrier;
    }

    public void resetPaquet() {
        //========création des cartes============//
        // create object mapper instance  //
        ObjectMapper mapper = new ObjectMapper(); //objet permettant l'utilisation des json

        // convert JSON string to Book object
        try {
            InputStream in = PaquetOuvrier.class.getResourceAsStream( PATH+nomdupaquet + ".json"); //localisation du fichier json
            List<CarteOuvrier> paquetOuvrierCopie; //initialisation du paquet caché
            paquetOuvrierCopie = Arrays.asList(mapper.readValue(in, CarteOuvrier[].class));//stockage des valeurs du json dans une liste copie non modifiable
            paquetOuvrier = new ArrayList(); //initialisation du paquet caché modifiable
            paquetOuvrier.addAll(paquetOuvrierCopie); //ajout de toutes les cartes de la liste non modifiable dans une liste modifiable pour manipuler le paquet par la suite
            Collections.shuffle(paquetOuvrier);

            //=== creation du paquet visible ouvrier === (pioche aléatoirement dans paquetOuvrier)//
            paquetVisibleOuvrier = new PiocheOuvrierVisible(); //initialisation du paquet visible dont le joueur pourra sélectionner une carte

            while (paquetVisibleOuvrier.getPiocheOuvrierVisible().size() < 5) { //on vérifie que le paquet visible a 5 cartes sinon ekke pioche aléatoirement dans la pioche cachée
                // on applique un filtre pour pas que les apprentis soient dans la pioche visible
                if (paquetOuvrier.get(paquetOuvrier.size()-1).getType().contains("apprenti") == false) {
                    paquetVisibleOuvrier.getPiocheOuvrierVisible().add(paquetOuvrier.get(paquetOuvrier.size()-1)); // ajoute la carte dans le paquet visible
                    paquetOuvrier.remove(paquetOuvrier.get(paquetOuvrier.size()-1)); //suppression de la carte dans la pioche cachée
                } else {
                    Collections.shuffle(paquetOuvrier);
                }
            }


        } catch (IOException e) {
            e.printStackTrace(); //débugger
        }
    }
}
