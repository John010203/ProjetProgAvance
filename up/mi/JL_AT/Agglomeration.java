import java.util.ArrayList;
import java.util.List;

public class Agglomeration { //Attributs de la classe Agglomeration.

	private List <Ville> villes;
	private List<Route> routes;
	private List<Ville> recharge;
	
	public Agglomeration(List<Ville> villes) {//Initialise une agglomération avec une liste de villes.
		this.villes = villes;
		this.routes = new ArrayList<Route>();	
		this.recharge = new ArrayList<Ville>(villes);
	}//Agglomeration()
	
	public String getVilles() {//methode qui retourne les villes.
		StringBuffer sb = new StringBuffer();
		for (int i =0; i< villes.size();i++) {
			sb.append(villes.get(i).getNomVille());
			sb.append(" ");
		}//for 
		return sb.toString();
	}//getVilles()
	
	public String getRecharge() {//Methode qui retourne une chaîne de caractères représentant les noms des villes où la recharge est possible.
		StringBuffer sb = new StringBuffer();
		for (int i =0; i< recharge.size();i++) {
			sb.append(recharge.get(i).getNomVille());
			sb.append(" ");
		}//for
		return sb.toString();
	}//getRecharge()
	

	//void setRoute(Route route),
	// Ajoute une route à l'agglomération,
	// en vérifiant si elle existe déjà et si elle ne relie pas la même ville à elle-même. 

	public void setRoute(Route routeVille){//méthode qui relie la ville avec une autre ville.
		boolean existe = false;
		if(routeVille.getVilleA().equals(routeVille.getVilleB())){//Si la Ville choisi est la même ville.
			System.out.println("Vous ne pouvez pas rajouter une route dans la ville elle-même.");
		}//if
		for (Route routes2 : this.routes){//si une route existe déjà.
			if ((routes2.getVilleA().equals(routeVille.getVilleA())) && (routes2.getVilleB().equals(routeVille.getVilleB())) || (routes2.getVilleA().equals(routeVille.getVilleB())) && (routes2.getVilleB().equals(routeVille.getVilleA()))){
				System.out.println("La route reliant " + routeVille.getVilleA().getNomVille() + " à " + routeVille.getVilleB().getNomVille() + " existe déjà.");			
				existe=true;
				break;
			}//if
		}//for
		if(!existe) {
			routes.add(routeVille);//ajoute la Ville dans la liste routes.
			routeVille.getVilleA().setVoisins(routeVille.getVilleB());
			routeVille.getVilleB().setVoisins(routeVille.getVilleA());
			System.out.println("La route reliant " + routeVille.getVilleA().getNomVille() + " à " + routeVille.getVilleB().getNomVille() + " a bien été ajouté.");
		}
	}//setRoute()
	
	// void ajoutRoute(String ville1,String ville2),
	// Ajoute une route entre deux villes spécifiées.
	public void ajoutRoute(String ville1, String ville2){
		Ville v1 = null,v2 = null;//initiliase v1 et v2 a null.
		for (Ville ville : this.villes) {//parcourt la liste des villes et recherche les objets Ville correspondants aux noms spécifiés.

			if(ville.getNomVille().equals(ville1)){
				v1=ville;
			}//if
			if(ville.getNomVille().equals(ville2)){
				v2=ville;
			}//if
		}//for
		
		if(v1!=null && v2!= null) { // Ajoute une nouvelle route si les deux villes existent.
			this.setRoute(new Route(v1,v2));
		}//if
		else{ // Affiche un message si l'une des 2 villes n'existe pas.		
			if(v1==null){
				System.out.println(ville1+ " ne se trouve pas dans l'agglomération.");
			}//if
			if(v2==null){
				System.out.println(ville2+ " ne se trouve pas dans l'agglomération.");
			}//if
		}//else
	}//ajouteRoute()
	
	//void recharge(String ville),
	//Active la zone de recharge pour une ville spécifiée, 
	//en ajoutant la ville à la liste des points de recharge et en mettant à jour les voisins des villes en conséquence.
	public void recharge(String ville){
		boolean rep = true;
		int i = 0;
		while(rep && i < this.villes.size()) {// Recherche la ville spécifiée dans la liste des villes.
			if(this.villes.get(i).getNomVille().equals(ville)){ // Active la zone de recharge pour la ville.
				if(!this.villes.get(i).getZoneDeRecharge()){//Vérifie si la ville a déjà une borne de recharge
					this.villes.get(i).setZoneDeRecharge(true);
					recharge.add(villes.get(i));
					rep = false;
					System.out.println(ville + " a maintenant une borne de recharge.");

		
					for(Route route : this.routes){// Met à jour les voisins des routes.
						if(route.getVilleA().equals(villes.get(i))){
							route.getVilleB().setVoisins(route.getVilleA());
						}//if
						else if(route.getVilleB().equals(villes.get(i))) {
							route.getVilleA().setVoisins(route.getVilleB());
						}//else if
					}//for
				}//if
				else {
					System.out.println(ville + " a deja une borne de recharge.");
					rep = false;
				}//else
			}//if
			else{
				i++;
			}//else
		}//while
		if(rep){// Affiche un message si la ville n'est pas trouvée dans l'agglomération.
			System.out.println(ville + " ne se trouve pas dans l'agglomération.");
		}//if
	}//recharge()

	private boolean villeExistante(String ville) {
		boolean rep = true;
		int i = 0;
		while(rep && i < this.villes.size()) {
			if(this.villes.get(i).getNomVille().equals(ville)){
				return true;
			}
			i++;
		}
		rep=false;
		return false;
	}

	private boolean sansVoisins(Ville ville) {
		return ville.voisinsSansRecharge().isEmpty() && ville.voisinsAvecRecharge().isEmpty();
	}

	private boolean voisinsRecharge(Ville ville) {
		boolean voisinsRecharge=false;
		if(ville.voisinsSansRecharge().isEmpty() && !ville.voisinsAvecRecharge().isEmpty()) {
			voisinsRecharge=true;
		}
		else if(!ville.voisinsSansRecharge().isEmpty() && !ville.voisinsAvecRecharge().isEmpty()){
			for(int j=0;j<ville.voisinsSansRecharge().size();j++){
				if(ville.voisinsSansRecharge().get(j).voisinsSansRecharge().size()==0){
					ville.voisinsSansRecharge().get(j).removeVoisins(ville);
					if(voisinsRecharge(ville.voisinsSansRecharge().get(j)) && ville.voisinsSansRecharge().size()>0) {
						System.out.println(ville.getNomVille() + " a des voisins avec des bornes de recharges, vous pouvez retirer sa borne de recharge.");
						voisinsRecharge=true;
					}
					else if(voisinsRecharge(ville.voisinsSansRecharge().get(j)) && ville.voisinsSansRecharge().size()==0) {
						System.out.println(ville.getNomVille() + " a des voisins sans borne de recharge, vous ne pouvez pas retirer sa borne de recharge.");
						voisinsRecharge=false;
						return voisinsRecharge;
					}
					else {
						System.out.println(ville.getNomVille() + " a des voisins sans borne de recharge, vous ne pouvez pas retirer sa borne de recharge.");
					}
					ville.voisinsSansRecharge().get(j).setVoisins(ville);
				}
			}
		}
		return voisinsRecharge;
	}
	
	private void setDecharge(Ville ville) {
		if(sansVoisins(ville)) {
			System.out.println(ville.getNomVille() + " n'a pas de voisins, vous ne pouvez pas retirer sa borne de recharge.");
		}
		else if(voisinsRecharge(ville)){
			ville.setZoneDeRecharge(false);
			this.recharge.remove(ville);
			System.out.println(ville.getNomVille() + " a retirer sa borne de recharge.");
		}
	}
	//void decharge(String ville),
	//Désactive la zone de recharge pour une ville spécifiée, 
	//en la retirant de la liste des points de recharge, sous réserve de certaines conditions.
	public void decharge(String ville) {
		if(!villeExistante(ville)) {
			System.out.println(ville + " ne se trouve pas dans l'agglomération.");
		}
		else {
			for(int i=0;i < this.villes.size();i++) {
				if(this.villes.get(i).getNomVille().equals(ville)){
					if(!this.villes.get(i).getZoneDeRecharge()){
						System.out.println(ville + " n'a pas de borne de recharge.");	
					}
					else {
						setDecharge(this.villes.get(i));
					}
				}
			}
		}
	}//decharge()

	// Méthode qui renvoie un boolean pour vérifier l'existence d'une ville
	public boolean villeExiste(String nomVille) {
		// Obtient une chaîne contenant les noms de toutes les villes dans l'agglomération.
		String nomsVilles = getVilles();
		// Vérifie si la chaîne des noms de villes contient le nom de la ville spécifiée.
		return nomsVilles.toUpperCase().contains(nomVille.toUpperCase());
	}

	// Méthode qui permet d'imprimer les routes de l'agglomération.
	public void imprimerRoutes() {
		System.out.println("Routes de l'agglomération :");
		// Parcours toutes les routes de l'agglomération.
		for (Route route : this.routes) {
			// Imprime les villes reliées par chaque route.
			System.out.println("(" + route.getVilleA().getNomVille() + "," + route.getVilleB().getNomVille() + ")");
		}
	}

	// Méthode qui retourne une chaîne de caractères représentant les noms des villes en zone de recharge.
	public String getVillesRecharge() {
		StringBuffer sb = new StringBuffer();
		// Parcours toutes les villes de l'agglomération.
		for (Ville ville : villes) {
			// Vérifie si la ville a une zone de recharge.
			if (ville.getZoneDeRecharge()) {
				// Ajoute le nom de la ville.
				sb.append(ville.getNomVille());
				sb.append(" ");
			}
		}
		// Retourne la chaîne résultante
		return sb.toString();
	}
}