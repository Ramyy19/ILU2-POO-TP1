package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        Etal etal = new Etal();
        try {
            etal.libererEtal();
        } catch (IllegalStateException e) {
            e.printStackTrace(System.err); 
        }
        try {
            etal.acheterProduit(10, null);
        } catch (NullPointerException e) {
            e.printStackTrace(System.err);
        }
        try {
            etal.occuperEtal(new Gaulois("Obélix", 10), "menhirs", 5);
            etal.acheterProduit(-1, new Gaulois("Abraracourcix", 5));
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.err);
        }

        System.out.println("Fin du test des cas dégradés.");
    }
}