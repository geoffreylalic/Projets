package joueur.reseau;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import donnees.Inventaire;
import donnees.Message;
import donnees.PiocheBatimentVisible;
import donnees.PiocheOuvrierVisible;
import donnees.action.ActionPiocherBatiment;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import joueur.Client;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EchangesAvecLeServeur {

    private Client controleur;
    Socket connexion;
    ObjectMapper jackson;

    /**
     * @param url  : adresse du serveur
     * @param ctrl
     */
    public EchangesAvecLeServeur(String url, Client ctrl) {
        setControleur(ctrl);

        jackson = new ObjectMapper();

        try {
            connexion = IO.socket(url);

            this.controleur.transfèreMessage("on s'abonne à la connection / déconnection ");

            connexion.on("connect", new Emitter.Listener() {
                public void call(Object... objects) {
                    // déplacement du message dans Client/Controleur
                    // on s'identifie
                    controleur.aprèsConnexion();

                }
            });

            connexion.on("disconnect", new Emitter.Listener() {
                public void call(Object... objects) {
                    controleur.transfèreMessage(" !! on est déconnecté !! ");
                    controleur.finPartie();

                }
            });

            this.controleur.transfèreMessage("on s'abonne à fin du jeu ");
            connexion.on(Message.FIN, new Emitter.Listener() {
                public void call(Object... objects) {
                    controleur.résultat((boolean) objects[0]);
                }
            });

            this.controleur.transfèreMessage("on s'abonne aux demandes de jouer ");
            connexion.on(Message.DEMANDE_DE_JOUER, new Emitter.Listener() {
                public void call(Object... objects) {
                    // objects[0] est un Inventaire
                    JSONObject invJSON = (JSONObject) objects[0];
                    Inventaire inv = new Inventaire();

                    // objects[1] est la pioche Batiment
                    JSONObject piocheBatimentJSON = (JSONObject) objects[1];
                    PiocheBatimentVisible piocheVisibleBatiment = new PiocheBatimentVisible ();

                    // objects[2] est la pioche Ouvrier
                    JSONObject piocheOuvrierJSON = (JSONObject) objects[2];
                    PiocheOuvrierVisible piocheVisibleOuvrier = new PiocheOuvrierVisible ();


                    try {

                        ObjectMapper mapper = new ObjectMapper();

                        inv = mapper.readValue(invJSON.toString(), Inventaire.class);


                        piocheVisibleBatiment = mapper.readValue(piocheBatimentJSON.toString(), PiocheBatimentVisible.class);

                        piocheVisibleOuvrier = mapper.readValue(piocheOuvrierJSON.toString(), PiocheOuvrierVisible.class);

                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    controleur.jouer(inv, piocheVisibleBatiment, piocheVisibleOuvrier);
                }
            });

        } catch (URISyntaxException e) {
            controleur.signaleErreur(Arrays.toString(e.getStackTrace()));
        }
    }

    public void setControleur(Client controleur) {
        this.controleur = controleur;
    }

    public Client getControleur() {
        return controleur;
    }

    public void stop() {
        connexion.off("connect");
        connexion.off("disconnect");
        connexion.off(Message.FIN);

        // pour ne pas être sur le thread de SocketIO
        new Thread(new Runnable() {
            public void run() {
                connexion.disconnect();
                connexion.close();
                System.out.println("@todo >>>> c'est fini");
                // hack pour arrêter plus vite (sinon attente de plusieurs secondes)
                System.exit(0);
            }
        }).start();
    }

    public void envoyerId(Object pj) {
        JSONObject pieceJointe = new JSONObject(pj);
        connexion.emit(Message.IDENTIFICATION, pieceJointe);
    }

    public void seConnecter() {
        connexion.connect();
    }


    public void envoyerActionChoisie(Object pj) {
        // JSONObject pieceJointe = new JSONObject(pj);
        // c'est à Jackton de séraliser l'action
        try {
            String json = jackson.writeValueAsString(pj);
            connexion.emit(Message.JOUER_CETTE_ACTION, json);
        } catch (JsonProcessingException e) {
            controleur.transfèreMessage("erreur avec jackson...");
        }
    }
}