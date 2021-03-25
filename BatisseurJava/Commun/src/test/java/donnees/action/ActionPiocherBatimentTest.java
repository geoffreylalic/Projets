package donnees.action;

import cartes.CarteBatiment;
import donnees.Identification;
import donnees.Inventaire;
import donnees.PiocheBatimentVisible;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionPiocherBatimentTest {
    //on créer un faux moteur de jeu
    @Mock
    MoteurDeJeu moteur;

    @Test
    public void testPiocherBatimentAction() {

        //on créé l'inventaire testé
        Inventaire invTest = new Inventaire();

        // quand le faux moteur demande l'inventaire du joueur dans la logique de l'action on renvoie systématiquement l'inventaire testé, peu importe l'ID.
        when(moteur.getInventaireDuJoueur(any(Identification.class))).thenReturn(invTest);

        //on créé la fausse pioche
        PiocheBatimentVisible piochetest = new PiocheBatimentVisible();
        CarteBatiment c1 = new CarteBatiment("la tonnelle",0,1,0,1,8,0,0,0,0,0);
        CarteBatiment c2 = new CarteBatiment("la cabane perchée",0,2,1,0,6,1,0,0,0,0);
        CarteBatiment c3 = new CarteBatiment("la hutte de paille",0,1,0,2,6,1,0,0,0,0);
        CarteBatiment c4 = new CarteBatiment("le lavoir",0,1,0,2,6,1,0,0,0,0);
        CarteBatiment c5 = new CarteBatiment("le pont en pierre",0,2,1,0,6,1,0,0,0,0);
        piochetest.getPiocheBatimentVisible().add(c1);
        piochetest.getPiocheBatimentVisible().add(c2);
        piochetest.getPiocheBatimentVisible().add(c3);
        piochetest.getPiocheBatimentVisible().add(c4);
        piochetest.getPiocheBatimentVisible().add(c5);
        // quand le faux moteur demande La pioche apprente dans la logique de l'action on renvoie systématiquement la pioche visble testé.
        when(moteur.getPiocheBatimentVisible()).thenReturn(piochetest);

        Identification idTest = new Identification("cobaye");
        ActionPiocherBatiment p = new ActionPiocherBatiment(idTest, c2);

        p.appliquerAction(moteur);

        assertEquals(c2, invTest.getMainBatiment(0));

    }
}
