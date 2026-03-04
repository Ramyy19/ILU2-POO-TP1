package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public String getProduit() {
		return produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public int getQuantiteDebutMarche() {
		return quantiteDebutMarche;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		
		if (!etalOccupe) {
			throw new IllegalStateException("L'étal doit être occupé pour être libéré.");
		}
		
		etalOccupe = false;
		String message = "Le vendeur " + vendeur.getNom() + " quitte son étal, ";
		int produitVendu = quantiteDebutMarche - quantite;
		
		if (produitVendu > 0) {
			message += "il a vendu " + produitVendu + " " + produit + " parmi les " + quantiteDebutMarche + " qu'il voulait vendre.\n";
		} else {
			message += "il n'a malheureusement rien vendu.\n";
		}
		return message;
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
		
		if (acheteur == null) {
			throw new NullPointerException("L'acheteur ne peut pas être null.");
		}
		
		
		if (quantiteAcheter < 1) {
			throw new IllegalArgumentException("La quantité achetée doit être au moins de 1.");
		}
		
		
		if (!etalOccupe) {
			throw new IllegalStateException("On ne peut pas acheter sur un étal vide.");
		}

		StringBuilder chaine = new StringBuilder();
		chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
				+ " " + produit + " à " + vendeur.getNom());
		
		if (quantite == 0) {
			chaine.append(", malheureusement il n'y en a plus !");
			quantiteAcheter = 0;
		} else if (quantiteAcheter > quantite) {
			chaine.append(", comme il n'y en a plus que " + quantite + ", "
					+ acheteur.getNom() + " vide l'étal de "
					+ vendeur.getNom() + ".\n");
			quantiteAcheter = quantite;
			quantite = 0;
		} else {
			quantite -= quantiteAcheter;
			chaine.append(". " + acheteur.getNom()
					+ ", est ravi de tout trouver sur l'étal de "
					+ vendeur.getNom() + "\n");
		}
		return chaine.toString();
	}

	public boolean contientProduit(String produit) {
		return etalOccupe && produit.equals(this.produit);
	}
}