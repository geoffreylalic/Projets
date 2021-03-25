package donnees.action;

import cartes.CarteOuvrier;
import donnees.PiocheOuvrierVisible;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class PiocheOuvrierVisibleTest {
        @Mock
        MoteurDeJeu moteur;

        @Test
        public void testPiocherOuvrierAction() {

            //on créé l'inventaire testé

            PiocheOuvrierVisible piochetest = new PiocheOuvrierVisible();
            CarteOuvrier c1 = new CarteOuvrier();
            CarteOuvrier c2 =new CarteOuvrier();
            CarteOuvrier c3 = new CarteOuvrier();
            CarteOuvrier c4 = new CarteOuvrier();
            CarteOuvrier c5 = new CarteOuvrier();
            piochetest.getPiocheOuvrierVisible().add(c1);
            piochetest.getPiocheOuvrierVisible().add(c2);
            piochetest.getPiocheOuvrierVisible().add(c3);
            piochetest.getPiocheOuvrierVisible().add(c4);
            piochetest.getPiocheOuvrierVisible().add(c5);
            // quand le faux moteur demande La pioche apparente dans la logique de l'action on renvoie systématiquement la pioche test.
            when(moteur.getPiocheOuvrierApparent()).thenReturn(piochetest.getPiocheOuvrierVisible());
            assertEquals(c1, moteur.getPiocheOuvrierApparent().get(0));
            assertEquals(c2, moteur.getPiocheOuvrierApparent().get(1));
            assertEquals(c3, moteur.getPiocheOuvrierApparent().get(2));
            assertEquals(c4, moteur.getPiocheOuvrierApparent().get(3));
            assertEquals(c5, moteur.getPiocheOuvrierApparent().get(4));
            assertEquals(5,moteur.getPiocheOuvrierApparent().size());
        }
    }

