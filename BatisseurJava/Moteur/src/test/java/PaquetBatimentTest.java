import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaquetBatimentTest {
    @Test
    public void paquetBatimentTest(){
        moteur.paquets.PaquetBatiment paquetBatiment = new moteur.paquets.PaquetBatiment("CarteBatiment");
        assertEquals(5,paquetBatiment.getSizePaquetVisibleBatiment());
        assertEquals(37,paquetBatiment.getSizePaquetBatiment());
    }
    @Test
    public void testRetirerCarteBatiment() {
        // initialisation du paquet Batiment
        moteur.paquets.PaquetBatiment deckBatiment = new moteur.paquets.PaquetBatiment("BatimentAntiquite"); //constructeur Batiment
        assertEquals(28, deckBatiment.getSizePaquetBatiment());
        assertEquals(5,deckBatiment.getSizePaquetVisibleBatiment());

        deckBatiment.retirerCarteVisibleBatiment(2); //retire une carte
        assertEquals(27, deckBatiment.getSizePaquetBatiment());
        assertEquals(5,deckBatiment.getSizePaquetVisibleBatiment());

        for (int i = 0; i< 27; i++){
            deckBatiment.retirerCarteVisibleBatiment(0);//pioche toute les cartes cache
        }
        assertEquals(0,deckBatiment.getSizePaquetBatiment());
        assertEquals(5, deckBatiment.getSizePaquetVisibleBatiment());

        //paquet visible Batiment mis a 0
        for (int i = 0; i < 5; i++) {
            deckBatiment.retirerCarteVisibleBatiment(0);
        }
        assertEquals(0,deckBatiment.getSizePaquetVisibleBatiment());

        //si je retire des cartes visibles alors que le paquet visible est vide
        for (int i = 0; i < 10; i++) {
            deckBatiment.retirerCarteVisibleBatiment(0);
        }
        assertEquals(0, deckBatiment.getSizePaquetVisibleBatiment());
    }



}
