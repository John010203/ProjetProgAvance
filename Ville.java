import java.util.ArrayList;
import java.util.List;

public class Ville { // Attributs de la classe Ville.
	private String nomVille;
	private boolean zoneDeRecharge;
	private List<Ville> voisins;
	
	public Ville(String nomVille) { // Constructeur de la classe Ville.
		this.nomVille=nomVille;
		this.zoneDeRecharge=false;
		this.voisins=new ArrayList<Ville>();
	}
	//String getNomVille(),
	// Méthode pour obtenir le nom de la ville.
	public String getNomVille() {
		return nomVille;
	}//getNomVille()

	//void setZoneDeRecharge(boolean b),
    // Méthode pour définir la présence d'une zone de recharge dans la ville.
	public void setZoneDeRecharge(boolean b) {
		this.zoneDeRecharge = b;
	}//setZoneDeRecharge()

	//void setVoisins(Ville v),
	// Méthode qui définie les voisins de la ville.
	public void setVoisins(Ville v) {
		this.voisins.add(v) ;
	}//setVoisins()
	
	//boolean getZoneDeRecharge(),
	// Méthode qui retourne 'état de la zone de recharge de la ville.
	public boolean getZoneDeRecharge() {
		return this.zoneDeRecharge;
	}


	//String getVoisins(),
	// Méthode qui retourne le nom des voisins de la ville sous forme de chaîne de caractères.
	public String getVoisins() {
		StringBuffer sb = new StringBuffer();
		for (int i =0; i< voisins.size();i++) {
			sb.append(voisins.get(i).getNomVille());
			sb.append(" ");
		}//for
		return sb.toString();
	}// getVoisins()
	
	//boolean getRechargeVoisin(),
    // Méthode qui retourne un boolean et permet vérifier si au moins un voisin a une zone de recharge activée.
	public boolean getRechargeVoisin() {
		boolean rep=false;
		for (int i =0; i< voisins.size();i++) {
			if (((Ville) this.voisins).getZoneDeRecharge()==true) {
				rep=true;
			}//if
			/*else if(this.voisins.get(i).rechargeVoisin()==false){
				for (int j=0;j<this.voisins.get(i).voisins.size();j++) {
					if (((Ville) this.voisins).getZoneDeRecharge()==true) {
						rep=true;
					}
				}
			}*/
		}//for
		return rep;
	}//getRechargeVoisin()
}//Classe Ville.