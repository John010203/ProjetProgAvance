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
	
	public void recharge(String ville) {
		boolean rep = true;
		int i = 0;
		while(rep) {
			if(this.villes.get(i).getNomVille() == ville) {
				
				rep = false;
			}
		}
	}
}