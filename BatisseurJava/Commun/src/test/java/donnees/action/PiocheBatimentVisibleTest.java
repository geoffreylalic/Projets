package donnees.action;

import cartes.CarteBatiment;
import donnees.PiocheBatimentVisible;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class PiocheBatimentVisibleTest {
    @Mock
    MoteurDeJeu moteur;

    @Test
    public void testPiocherBatimentAction() {

        //on créé l'inventaire testé

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
        // quand le faux moteur demande La pioche apparente dans la logique de l'action on renvoie systématiquement la pioche test.
        when(moteur.getPiocheBatimentApparent()).thenReturn(piochetest.getPiocheBatimentVisible());
        assertEquals(c1, moteur.getPiocheBatimentApparent().get(0));
        assertEquals(c2, moteur.getPiocheBatimentApparent().get(1));
        assertEquals(c3, moteur.getPiocheBatimentApparent().get(2));
        assertEquals(c4, moteur.getPiocheBatimentApparent().get(3));
        assertEquals(c5, moteur.getPiocheBatimentApparent().get(4));
        assertEquals(5,moteur.getPiocheBatimentApparent().size());
    }
}
