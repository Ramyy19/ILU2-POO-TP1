package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
    private String nom;
    private Chef chef;
    private Gaulois[] villageois;
    private int nbVillageois = 0;
    private Marche marche; // Ne pas oublier de déclarer l'attribut marche !

    public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
        this.nom = nom;
        this.villageois = new Gaulois[nbVillageoisMaximum];
        this.marche = new Marche(nbEtals);
    }

    public String getNom() {
        return nom;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public void ajouterHabitant(Gaulois gaulois) {
        if (nbVillageois < villageois.length) {
            villageois[nbVillageois] = gaulois;
            nbVillageois++;
        }
    }

    public Gaulois trouverHabitant(String nomGaulois) {
        if (chef != null && nomGaulois.equals(chef.getNom())) {
            return chef;
        }
        for (int i = 0; i < nbVillageois; i++) {
            if (villageois[i].getNom().equals(nomGaulois)) {
                return villageois[i];
            }
        }
        return null;
    }

    public String afficherVillageois() {
        String chaine = "Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n";
        if (nbVillageois < 1) {
            return "Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n";
        }
        for (int i = 0; i < nbVillageois; i++) {
            chaine += "- " + villageois[i].getNom() + "\n";
        }
        return chaine;
    }

    // --- MÉTHODES DE VILLAGE UTILISANT LE MARCHÉ ---

    public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
        String message = vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n";
        int indice = marche.trouverEtalLibre();
        if (indice == -1) {
            message += "Mais il n'y a plus d'étal disponible.\n";
        } else {
            marche.utiliserEtal(indice, vendeur, produit, nbProduit);
            message += "Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indice + 1) + ".\n";
        }
        return message;
    }

    public String rechercherVendeursProduit(String produit) {
        Etal[] etalsProduit = marche.trouverEtals(produit);
        if (etalsProduit.length == 0) {
            return "Il n'y a pas de vendeur qui propose des " + produit + " au marché.";
        }
        if (etalsProduit.length == 1) {
            return "Seul le vendeur " + etalsProduit[0].getVendeur().getNom() + " propose des " + produit + " au marché.";
        }
        String liste = "Les vendeurs qui proposent des " + produit + " sont :\n";
        for (int i = 0; i < etalsProduit.length; i++) {
            liste += "- " + etalsProduit[i].getVendeur().getNom() + "\n";
        }
        return liste;
    }

    public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }

    public String partirVendeur(Gaulois vendeur) {
        Etal etal = marche.trouverVendeur(vendeur);
        if (etal == null) {
            return vendeur.getNom() + " n'a pas d'étal à quitter.";
        }
        int vendus = etal.nbProduitInitial() - etal.nbProduitRestant();
        etal.libererEtal();
        return "Le vendeur " + vendeur.getNom() + " quitte son étal, il a vendu "
                + vendus + " " + etal.getProduit() + " parmi les "
                + etal.nbProduitInitial() + " qu'il voulait vendre.";
    }

    public String afficherMarche() {
        return "Le marché du village \"" + nom + "\" possède plusieurs étals :\n" + marche.afficherMarche();
    }

    // --- CLASSE INTERNE MARCHE ---

    private class Marche {
        private Etal[] etals;

        public Marche(int nbEtals) {
            etals = new Etal[nbEtals];
            for (int i = 0; i < nbEtals; i++) {
                etals[i] = new Etal();
            }
        }

        public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
            if (indiceEtal >= 0 && indiceEtal < etals.length) {
                etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
            }
        }

        public int trouverEtalLibre() {
            for (int i = 0; i < etals.length; i++) {
                if (!etals[i].isEtalOccupe()) {
                    return i;
                }
            }
            return -1;
        }

        public Etal[] trouverEtals(String produit) {
            int nb = 0;
            for (int i = 0; i < etals.length; i++) {
                if (etals[i].contientProduit(produit)) nb++;
            }
            Etal[] resultat = new Etal[nb];
            int j = 0;
            for (int i = 0; i < etals.length; i++) {
                if (etals[i].contientProduit(produit)) {
                    resultat[j] = etals[i];
                    j++;
                }
            }
            return resultat;
        }

        public Etal trouverVendeur(Gaulois gaulois) {
            for (int i = 0; i < etals.length; i++) {
                if (etals[i].isEtalOccupe() && etals[i].getVendeur().equals(gaulois)) {
                    return etals[i];
                }
            }
            return null;
        }

        public String afficherMarche() {
            String resultat = "";
            int nbEtalsVides = 0;
            for (int i = 0; i < etals.length; i++) {
                if (etals[i].isEtalOccupe()) {
                    resultat += etals[i].afficherEtal() + "\n";
                } else {
                    nbEtalsVides++;
                }
            }
            if (nbEtalsVides > 0) {
                resultat += "Il reste " + nbEtalsVides + " étals non utilisés dans le marché.\n";
            }
            return resultat;
        }
    }
}