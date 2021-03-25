package moteur;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import donnees.Identification;
import donnees.Inventaire;
import moteur.reseau.EchangesAvecLeClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServeurTest {


    Identification joueur1 = new Identification("joueur1");
    Identification joueur2 = new Identification("joueur2");

    @Mock
    EchangesAvecLeClient connexion;

    @Mock
    Serveur serveurTest;


    @BeforeEach
    void setUp() throws InterruptedException {

    }

    @Test
    public void lancerPartieTest() {
        Moteur moteurTest = new Moteur(serveurTest,2,"Batisseur");
        moteurTest.ajouterJoueur(joueur1);
        moteurTest.ajouterJoueur(joueur2);

        when(serveurTest.getMoteur()).thenReturn(moteurTest);
        assertEquals(serveurTest.getMoteur(), moteurTest);
        assertEquals(2, serveurTest.getMoteur().getJoueurs().length );

        doNothing().when(serveurTest).lancerPartie();
        serveurTest.lancerPartie();

        Identification[] id = new Identification[2];
        id[0] = joueur1;
        id[1] = joueur2;
        doNothing().when(serveurTest).faireUnTour(id,false);
        serveurTest.faireUnTour(id,false);
        //doReturn(serveurTest.getMoteur().getInventaireDuJoueur(joueur1)).when(serveurTest).faireUnTour(id);
        //verify(serveurTest,times(2)).getMoteur().getInventaireDuJoueur(joueur1).getNbAction();
    }


}