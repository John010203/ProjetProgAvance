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
	
	public void removeVoisins(Ville v) {
		this.voisins.remove(v) ;
	}

	//boolean getZoneDeRecharge(),
	// Méthode qui retourne l'état de la zone de recharge de la ville.
	public boolean getZoneDeRecharge(){ 
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

	public List<Ville> getListVoisins(){
		return voisins;
	}

	public List<Ville> voisinsSansRecharge(){
		List<Ville> voisinsSansRecharge = new ArrayList<Ville>();
		for(Ville villes : voisins) {
			if(!villes.getZoneDeRecharge()) {
				voisinsSansRecharge.add(villes);
			}
		}
		return voisinsSansRecharge;
	}
	
	public List<Ville> voisinsAvecRecharge(){
		List<Ville> voisinsAvecRecharge = new ArrayList<Ville>();
		for(Ville villes : voisins) {
			if(villes.getZoneDeRecharge()) {
				voisinsAvecRecharge.add(villes);
			}
		}
		return voisinsAvecRecharge;
	}
}//Classe Ville.
