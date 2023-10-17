import java.util.ArrayList;
import java.util.List;

public class Agglomeration {
	private List <Ville> villes;
	private List<Route> routes;
	
	public Agglomeration(List<Ville> villes) {
		this.villes = new ArrayList<Ville>(villes);
		this.routes = new ArrayList<Route>();
	}
	
	public String getVilles() {
		StringBuffer sb = new StringBuffer();
		for (int i =0; i< villes.size();i++) {
			sb.append(villes.get(i).getNomVille());
			sb.append(" ");
		}
		return sb.toString();
	}
	
	public void setRoute(Route route) {
		routes.add(route);
	}
	
	public void ajoutRoute(String ville1, String ville2) {
		Ville v1 = null,v2 = null;
		for (Ville ville : this.villes) {
			if(ville.getNomVille().equals(ville1)) {
				v1=ville;
			}
			if(ville.getNomVille().equals(ville2)) {
				v2=ville;
			}
		}
		
		if(v1!=null && v2!= null) {
			this.setRoute(new Route(v1,v2));
		}
		else {
			if(v1==null) {
				System.out.println(ville1+ " ne se trouve pas dans l'agglomération.");
			}
			if(v2==null) {
				System.out.println(ville2+ " ne se trouve pas dans l'agglomération.");
			}
		}
	}
	
	public void recharge(String ville) {
		boolean rep = true;
		int i = 0;
		while(rep && i < this.villes.size()) {
			if(this.villes.get(i).getNomVille().equals(ville)) {
				this.villes.get(i).setZoneDeRecharge(true);
				rep = false;
				for(Route route : this.routes) {
					if(route.getVilleA().equals(villes.get(i))) {
						route.getVilleB().setZoneDeRechargeAdjacent(true);
						System.out.println(route.getVilleB().getZoneDeRechargeAdjacent());
					}
					if(route.getVilleB().equals(villes.get(i))) {
						route.getVilleA().setZoneDeRechargeAdjacent(true);
						System.out.println(route.getVilleA().getZoneDeRechargeAdjacent());
					}
				}
			}
			else{
				i++;
			}
		}
		if(rep) {
			System.out.println(ville + " ne se trouve pas dans l'agglomération.");
		}
	}
	public void decharge(String ville) {
		boolean rep = true;
		int i = 0;
		while(rep && i < this.villes.size()) {
			if(this.villes.get(i).getNomVille().equals(ville)) {
				if(this.villes.get(i).getZoneDeRechargeAdjacent()==true) {
					this.villes.get(i).setZoneDeRecharge(false);
					rep = false;
				}
				else {
					System.out.println("Vous ne pouvez pas retirer la borne de recharge dans " + ville + ". Il n'y a pas de borne de recharge aux alentours.");
					rep=false;
				}
			}	
			else {
				i++;
			}
		}
		if(rep) {
			System.out.println(ville + " ne se trouve pas dans l'agglomération.");
		}
	}
}