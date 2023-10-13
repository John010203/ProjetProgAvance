
public class Ville {
	private String nomVille;
	private boolean zoneDeRecharge;
	
	public Ville(String nomVille) {
		this.nomVille=nomVille;
		this.zoneDeRecharge=false;
	}
	
	public String getNomVille() {
		return nomVille;
	}
	
	public void setZoneDeRecharge(boolean b) {
		this.zoneDeRecharge = b;
	}
	
	public boolean getZoneDeRecharge() {
		return this.zoneDeRecharge;
	}
}
