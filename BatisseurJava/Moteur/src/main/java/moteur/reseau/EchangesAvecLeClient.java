package moteur.reseau;

import cartes.CarteBatiment;
import cartes.CarteOuvrier;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import donnees.*;
import donnees.action.Action;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EchangesAvecLeClient implements EnvoiDesMessages {
    SocketIOServer serveur;
    RéceptionDesMessages controleur;
    private HashMap<Identification, SocketIOClient> map;

    Object synchro = new Object();
    boolean enFonctionnement = true;

    // il y a un problème en interne à SocketIo, on l'aide un peu, on fait la conversion nous même pour les Action
    // pour cela, il nous faut un ObjectMapper (plus facile)
    ObjectMapper jackson = new ObjectMapper();

    public EchangesAvecLeClient(String ip, int port, RéceptionDesMessages ctrl) {
        controleur = ctrl;

        map = new HashMap<>();
        Configuration config = new Configuration();
        config.setHostname(ip);
        config.setPort(port);

        // pour éviter un message d'erreur inutile... on attrape l'info sur DisconnectListener
        config.setExceptionListener(new ExceptionListenerAdapter() {
            @Override
            public void onDisconnectException(Exception e, SocketIOClient client) {
                // pour éviter un message d'erreur inutile... on attrape l'info sur DisconnectListener
            }

            @Override
            public boolean exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                return true;
            }
        });


        setServeur(new SocketIOServer(config));

        getServeur().addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                controleur.transfèreMsg("connexion de " + socketIOClient.getRemoteAddress());
            }
        });

        getServeur().addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient socketIOClient) {
                synchronized (synchro) {
                    if (getServeur().getAllClients().size() == 0) {
                        controleur.transfèreMsg("tou.te.s sont déconnecté.e.s");
                        arrêter();
                    }
                }
            }
        });


        // réception d'une identification
        serveur.addEventListener(Message.IDENTIFICATION, Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                synchronized (synchro) {
                    // un seul message ici à la fois
                    if (controleur.nouveauJoueur(identification)) {
                        map.put(identification, socketIOClient);
                    } else {
                        // todo...
                    }
                }
            }
        });


        // réception du choix d'une action
        // IMPORTANT : en interne, la deserialisation pause problème, du coup on passe par une chaine qu'on désérialise à la main
        serveur.addEventListener(Message.JOUER_CETTE_ACTION, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String action, AckRequest ackRequest) throws Exception {
                synchronized (synchro) {
                    // il y a un problème en interne à SocketIo, on l'aide un peu, on fait la conversion nous même
                    Action a = jackson.readValue(action, Action.class);
                    controleur.transfèreAction(a);
                }
            }
        });



    }


    public void envoyerMessage(Identification leClient, String message, Object... attachement) {
        map.get(leClient).sendEvent(message, attachement);
    }

    public void arrêter() {
        enFonctionnement = false;

        controleur.transfèreMsg("fin du serveur - début");
        for (SocketIOClient c : map.values()) c.disconnect();


        controleur.transfèreMsg("fin du serveur - désabonnement");
        getServeur().removeAllListeners("identification");


        controleur.transfèreMsg("fin du serveur - stop");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getServeur().stop(); // à faire sur un autre thread que sur le thread de SocketIO
                controleur.transfèreMsg("fin du serveur - fin");

            }
        }).start();

    }


    @Override
    public void permettreConnexion() {
        getServeur().start();
    }

    @Override
    public void envoyerSignalFin(Identification gagnant) {
        // tout le monde a gagné.
        //getServeur().getBroadcastOperations().sendEvent(Message.FIN, true);
        for (Map.Entry<Identification, SocketIOClient> j : map.entrySet()) {
            j.getValue().sendEvent(Message.FIN, j.getKey().equals(gagnant));
        }

    }


    /**
     * pour effectuer la demande de joueur
     * @param j le joueur à contacter
     * @param inventaireDuJoueur son inventaire
     * @param piocheBatimentVisible la pioche de batiment composé de 5 cartes
     * @param piocheOuvrierVisible la pioche d'ouvrier composé de 5 cartes
     **/
    @Override
    public void demandeAuJoueurDeJouer(Identification j, Inventaire inventaireDuJoueur, PiocheBatimentVisible piocheBatimentVisible, PiocheOuvrierVisible piocheOuvrierVisible) {
        if (map.containsKey(j)) {
            map.get(j).sendEvent(Message.DEMANDE_DE_JOUER, inventaireDuJoueur, piocheBatimentVisible, piocheOuvrierVisible);
        }
    }


    /******** pour les propriétés ************/
    public void setServeur(SocketIOServer serveur) {
        this.serveur = serveur;
    }

    public SocketIOServer getServeur() {
        return serveur;
    }
}
