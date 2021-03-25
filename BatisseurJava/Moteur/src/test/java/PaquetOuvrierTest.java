import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaquetOuvrierTest {
    @Test
    public void paquetOuvrierTest(){
        moteur.paquets.PaquetOuvrier paquetOuvrier = new moteur.paquets.PaquetOuvrier("CarteOuvrier");
        assertEquals(5,paquetOuvrier.getSizePaquetVisibleOuvrier());
        assertEquals(37,paquetOuvrier.getSizePaquetOuvrier());
    }
    @Test
    public void testRetirerCarteOuvrier() {
        // initialisation du paquet ouvrier
        moteur.paquets.PaquetOuvrier deckOuvrier = new moteur.paquets.PaquetOuvrier("OuvrierAntiquite");
        assertEquals(13, deckOuvrier.getSizePaquetOuvrier());
        assertEquals(5,deckOuvrier.getSizePaquetVisibleOuvrier());

        deckOuvrier.retirerCarteVisibleOuvrier(2); //retire une carte
        assertEquals(12, deckOuvrier.getSizePaquetOuvrier());
        assertEquals(5,deckOuvrier.getSizePaquetVisibleOuvrier());

        for (int i = 0; i< 12; i++){
            deckOuvrier.retirerCarteVisibleOuvrier(0);//pioche toute les cartes cache
        }
        assertEquals(0,deckOuvrier.getSizePaquetOuvrier());
        assertEquals(5, deckOuvrier.getSizePaquetVisibleOuvrier());

        //paquet visible ouvrier mis a 0
        for (int i = 0; i < 5; i++) {
            deckOuvrier.retirerCarteVisibleOuvrier(0);
        }
        assertEquals(0,deckOuvrier.getSizePaquetVisibleOuvrier());

        //si je retire des cartes visibles alors que le paquet visible est vide
        for (int i = 0; i < 10; i++) {
            deckOuvrier.retirerCarteVisibleOuvrier(0);
        }
        assertEquals(0, deckOuvrier.getSizePaquetVisibleOuvrier());
    }
}
