package moteur.paquets;

import cartes.CarteBatiment;
import com.fasterxml.jackson.databind.ObjectMapper;
import donnees.PiocheBatimentVisible;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PaquetBatiment {
    /* paquet batiment caché et visible */
    private String PATH = "../../bat/";
    private String nomdupaquet ;
    private List<CarteBatiment> paquetBatiment;// paquet face caché
    private PiocheBatimentVisible paquetVisibleBatiment;  //paquet visible
    /**
     *  Le constructeur permet de créer une liste paquetBatiment,issue fichier json CarteBatiment.json et paquetVisibleBatiment qui pioche 5 cartes aléatoirement
     */
    public PaquetBatiment (String nomdupaquet){
        this.nomdupaquet = nomdupaquet;
        resetPaquet();
    }

    /**
     * permet de retourner une carte visible selon le choix du joueur
     * @param i indice de la carte dans la liste de paquetVisibleBatiment à retourner
     * @return
     */
    public CarteBatiment getCarteVisibleBatiment(int i){ //retourne une carte visible selon le choix du joueur
        if (paquetBatiment == null){
            return null; // retourne null si la carte n'existe pas
        }
        return paquetVisibleBatiment.getPiocheBatimentVisible().get(i);
    }

    /**
     * permet de retourner une carte dans le paquet cache
     * @param i indice de la carte dans la liste de paquetBatiment à retourner
     * @return
     */
    public CarteBatiment getCartePaquetBatiment(int i) { // retourne une carte dans le paquet cache
        if (paquetBatiment == null){
            return null; //retourne null si la carte du paquet caché n'existe pas
        }
        return paquetBatiment.get(i);
    }

    /**
     * permet de retourner la taille du paquet caché
     * @return
     */
    public int getSizePaquetBatiment() { // retourne la taille du paquet caché
        return paquetBatiment.size();
    }

    /**
     * permet de retirer une carte choisi du paquet caché
     * @param i indice de la carte à retirer dans la liste paquetBatiment
     */
    public void retirerCartePaquetBatiment(int i) { // retire une carte choisi du paquet caché
        paquetBatiment.remove(i);
    }

    /**
     * permet de retirer une carte visible choisi par le joueur l'ajouter
     * ajouter une nouvelle carte issue du paquet caché aléatoirement
     * retire du paquet caché la carte ajouter dans le paquet visible
     * @param i indice de la carte à retirer dans la liste paquetVisibleBatiment
     */
    public void retirerCarteVisibleBatiment(int i) { //retire une carte visible choisi par le joueur de l'ajouter dans dans la liste du paquet visible et la retire dans le paquet caché
        if (paquetVisibleBatiment.getPiocheBatimentVisible().size() == 0) {
            return;
        }
        paquetVisibleBatiment.getPiocheBatimentVisible().remove(i);
        int max = paquetBatiment.size();
        if (max == 0){
            return;
        } else {
            while (paquetVisibleBatiment.getPiocheBatimentVisible().size()<5){ //pioche une carte dans le paquet cacher pour que le paquet visible reste a 5
                paquetVisibleBatiment.getPiocheBatimentVisible().add(paquetBatiment.get(paquetBatiment.size()-1));
                paquetBatiment.remove(paquetBatiment.get(paquetBatiment.size()-1));
            }
        }
    }

    /**
     * permet de retourner la taille du paquet visible
     * @return
     */
    public int getSizePaquetVisibleBatiment() { // retourne la taille du paquet visible
        return paquetVisibleBatiment.getPiocheBatimentVisible().size();
    }

    /**
     * permet de retourner la liste du paquet visible
     * @return
     */
    public List<CarteBatiment> getPiocheBatimentVisible() { // retourne la liste du paquet visible
        return paquetVisibleBatiment.getPiocheBatimentVisible();
    }

    /**
     * permet de retourne la liste du paquet caché
     * @return
     */
    public List<CarteBatiment> getPaquetBatiment() { //retourne la liste du paquet caché
        return paquetBatiment;
    }

    public PiocheBatimentVisible getPaquetVisibleBatiment() {
        return paquetVisibleBatiment;
    }

    public void resetPaquet() {

        //========création des cartes============//
        // create object mapper instance
        ObjectMapper mapper = new ObjectMapper(); //objet permettant l'utilisation des json

        // convert JSON string to Book object
        try {
            URL u = PaquetBatiment.class.getResource("CarteBatiment.json");
            InputStream on = PaquetBatiment.class.getResourceAsStream(PATH+nomdupaquet+".json"); //localisation du fichier json
            List<CarteBatiment> paquetBatimentCopie; //initialisation du paquet caché
            paquetBatimentCopie = Arrays.asList(mapper.readValue(on, CarteBatiment[].class)); //stockage des valeurs du json dans une liste copie non modifiable
            paquetBatiment = new ArrayList(); //initialisation du paquet caché modifiable
            paquetBatiment.addAll(paquetBatimentCopie); // ajout de toutes les cartes issus de la liste non modifiable dans une liste modifiable pour manipuler le paquet par la suite
            Collections.shuffle(paquetBatiment);

            //=== creation du paquet visible batiment === (pioche aléatoirement dans paquetBatiment)//

            paquetVisibleBatiment = new PiocheBatimentVisible();  //initialisation du paquet visible dont le joueur pourra sélectionner une carte parmis cette liste
            while (paquetVisibleBatiment.getPiocheBatimentVisible().size()<5){ //on vérifie que le paquet visible a 5 cartes sinon elle pioche aléatoirement dans la pioche cachéee
                paquetVisibleBatiment.getPiocheBatimentVisible().add(paquetBatiment.get(paquetBatiment.size() - 1)); // ajoute la carte dans le paquet visible
                paquetBatiment.remove(paquetBatiment.get(paquetBatiment.size() - 1)); // suppresion de la carte dans la pioche cachée
            }

        } catch (IOException e) {
            e.printStackTrace(); //débugger
        }
    }
}
