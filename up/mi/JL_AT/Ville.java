import java.util.ArrayList;
import java.util.List;

public class Ville { // Attributs de la classe Ville.
	private String nomVille;
	private boolean zoneDeRecharge;
	private List<Ville> voisins;
	
	public Ville(String nomVille) { // Constructeur de la classe Ville.
		this.nomVille=nomVille;
		this.zoneDeRecharge=true;
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
	// Méthode qui retourne l'état de la zone de recharge de la ville.
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
	
	//boolean checkRechargeVoisin(),
    // Méthode qui retourne un boolean et permet vérifier si au moins un voisin a une zone de recharge activée.
	private boolean checkRechargeVoisin(List<Ville> voisins, Ville villeExclu) {
		boolean rep=false;
		if(!voisins.isEmpty()) {//Vérifie que la liste de voisins n'est pas vide
			for (Ville voisin : voisins) {
				System.out.println("REP : " + rep +" "+ voisin.nomVille + " " + voisin.getZoneDeRecharge() );
				if (voisin.getZoneDeRecharge()==true && !voisin.equals(villeExclu)) {//Vérifie que les voisins ont une borne de recharge
					rep=true;
				}//if	
			}//for
		}//if
		return rep;
	}//checkRechargeVoisin()
	
	//boolean getRechargeVOisin(),
	//Méthode qui retourne un boolean et qui appelle la méthode privée qui vérifie si un voisin a une borne de recharge
	public boolean getRechargeVoisin() {
		return checkRechargeVoisin(voisins,this);
	}//getRechargeVoisin()
}//Classe Ville.