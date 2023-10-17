public class Ville {
	private String nomVille;
	private boolean zoneDeRecharge;
	private boolean zoneDeRechargeAdjacent;
	
	public Ville(String nomVille) {
		this.nomVille=nomVille;
		this.zoneDeRecharge=false;
		this.zoneDeRechargeAdjacent=false;
	}
	
	public String getNomVille() {
		return nomVille;
	}
	
	public void setZoneDeRecharge(boolean b) {
		this.zoneDeRecharge = b;
	}
	public void setZoneDeRechargeAdjacent(boolean b) {
		this.zoneDeRechargeAdjacent = b;
	}
	
	public boolean getZoneDeRecharge() {
		return this.zoneDeRecharge;
	}
	public boolean getZoneDeRechargeAdjacent() {
		return this.zoneDeRechargeAdjacent;
	}
}