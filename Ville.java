import java.util.ArrayList;
import java.util.List;

public class Ville {
	private String nomVille;
	private boolean zoneDeRecharge;
	private List<Ville> voisins;
	
	public Ville(String nomVille) {
		this.nomVille=nomVille;
		this.zoneDeRecharge=false;
		this.voisins=new ArrayList<Ville>();
	}
	
	public String getNomVille() {
		return nomVille;
	}
	
	public void setZoneDeRecharge(boolean b) {
		this.zoneDeRecharge = b;
	}
	public void setVoisins(Ville v) {
		this.voisins.add(v) ;
	}
	
	public boolean getZoneDeRecharge() {
		return this.zoneDeRecharge;
	}
	public String getVoisins() {
		StringBuffer sb = new StringBuffer();
		for (int i =0; i< voisins.size();i++) {
			sb.append(voisins.get(i).getNomVille());
			sb.append(" ");
		}
		return sb.toString();
	}
	
	public boolean getRechargeVoisin() {
		boolean rep=false;
		for (int i =0; i< voisins.size();i++) {
			if (((Ville) this.voisins).getZoneDeRecharge()==true) {
				rep=true;
			}
			/*else if(this.voisins.get(i).rechargeVoisin()==false){
				for (int j=0;j<this.voisins.get(i).voisins.size();j++) {
					if (((Ville) this.voisins).getZoneDeRecharge()==true) {
						rep=true;
					}
				}
			}*/
		}
		return rep;
	}
}
