import java.util.ArrayList;
import java.util.List;

public class Agglomeration {
	private List <Ville> villes;
	private List<Route> routes;
	
	public Agglomeration(List<Ville> Villes) {
		this.villes = new ArrayList<Ville>();
		this.routes = null;
	}
	
	public String getVilles() {
		StringBuffer sb = new StringBuffer();
		for (int i =0; i< villes.size();i++) {
			sb.append(villes.get(i).getNomVille());
		}
		
		return sb.toString();
	}
}

