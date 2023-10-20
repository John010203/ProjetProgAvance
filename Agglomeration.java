import java.util.ArrayList;
import java.util.List;

public class Agglomeration {
	private List <Ville> villes;
	private List<Route> routes;
	private List<Ville> recharge;
	
	public Agglomeration(List<Ville> villes) {
		this.villes = new ArrayList<Ville>(villes);
		this.routes = new ArrayList<Route>();
		this.recharge = new ArrayList<Ville>();
	}
	
	public String getVilles() {
		StringBuffer sb = new StringBuffer();
		for (int i =0; i< villes.size();i++) {
			sb.append(villes.get(i).getNomVille());
			sb.append(" ");
		}
		return sb.toString();
	}
	
	public String getRecharge() {
		StringBuffer sb = new StringBuffer();
		for (int i =0; i< recharge.size();i++) {
			sb.append(recharge.get(i).getNomVille());
			sb.append(" ");
		}
		return sb.toString();
	}
	
	public void setRoute(Route route) {
		if(route.getVilleA().equals(route.getVilleB())) {
			System.out.println("Vous ne pouvez pas rajouter une route dans la ville elle-même.");
		}
		for (Route routes2 : this.routes) {
			if ((routes2.getVilleA().equals(route.getVilleA())) && (routes2.getVilleB().equals(route.getVilleB())) || (routes2.getVilleA().equals(route.getVilleB())) && (routes2.getVilleB().equals(route.getVilleA()))){
				System.out.println("Cette Route existe déjà. Vous ne pouvez pas la rajouter.");
				break;
			}
		}
		routes.add(route);
		route.getVilleA().setVoisins(route.getVilleB());
		route.getVilleB().setVoisins(route.getVilleA());
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
				recharge.add(villes.get(i));
				rep = false;
				for(Route route : this.routes) {
					if(route.getVilleA().equals(villes.get(i))) {
						route.getVilleB().setVoisins(route.getVilleA());
						System.out.println(route.getVilleB().getNomVille());

					}
					else if(route.getVilleB().equals(villes.get(i))) {
						route.getVilleA().setVoisins(route.getVilleB());
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
				if(this.villes.get(i).getRechargeVoisin()==true) {
					this.villes.get(i).setZoneDeRecharge(false);
					recharge.remove(villes.get(i));
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