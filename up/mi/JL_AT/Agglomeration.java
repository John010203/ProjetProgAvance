import java.util.ArrayList;
import java.util.List;

public class Agglomeration { //Attributs de la classe Agglomeration.

	private List <Ville> villes;
	private List<Route> routes;
	private List<Ville> recharge;
	
	public Agglomeration(List<Ville> villes) {//Initialise une agglomération avec une liste de villes.
		this.villes = new ArrayList<Ville>(villes);
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
		if(routeVille.getVilleA().equals(routeVille.getVilleB())){//Si la Ville choisi est la même ville.
			System.out.println("Vous ne pouvez pas rajouter une route dans la ville elle-même.");
		}//if
		for (Route routes2 : this.routes){//si une route existe déjà.
			if ((routes2.getVilleA().equals(routeVille.getVilleA())) && (routes2.getVilleB().equals(routeVille.getVilleB())) || (routes2.getVilleA().equals(routeVille.getVilleB())) && (routes2.getVilleB().equals(routeVille.getVilleA()))){
				System.out.println("Cette Route existe déjà. Vous ne pouvez pas la rajouter.");
				break;
			}//if
		}//for
		routes.add(routeVille);//ajoute la Ville dans la liste routes.
		routeVille.getVilleA().setVoisins(routeVille.getVilleB());
		routeVille.getVilleB().setVoisins(routeVille.getVilleA());
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
	
	private boolean aBorneDeRechargeOuVoisin(Agglomeration agglomeration, Ville ville) {
		if(ville.getZoneDeRecharge()==true && ville.getRechargeVoisin()==true) {//Vérifie si la ville ou l'un de ses voisins a une borne de recharge
			return true;//retourne vraie si une borne de recharge est présente dans la ville ou un de ses voisins
		}
		return false;//sinon faux
	}

	//void decharge(String ville),
	//Désactive la zone de recharge pour une ville spécifiée, 
	//en la retirant de la liste des points de recharge, sous réserve de certaines conditions.
	public void decharge(String ville){
		boolean rep = true;
		int i = 0;
		while(rep && i < this.villes.size()) { // Recherche la ville spécifiée dans la liste des villes.
			if(this.villes.get(i).getNomVille().equals(ville)) {
				if(this.villes.get(i).getZoneDeRecharge()){
				 // Désactive la zone de recharge si la ville a des voisins avec une zone de recharge.
					if(aBorneDeRechargeOuVoisin(this,this.villes.get(i))) {
						this.villes.get(i).setZoneDeRecharge(false);
						recharge.remove(villes.get(i));//met a jour les voisins dans la liste de recharge
						rep = false;
					}//if
				
					else {
						// Affiche un message si la ville n'a pas de voisins avec une zone de recharge.	
						System.out.println("Vous ne pouvez pas retirer la borne de recharge dans " + ville + ". Il n'y a pas de borne de recharge aux alentours.");
						rep=false;
					}//else
				}//if
				else {
					System.out.println(ville + " ne possede pas de borne de recharge.");
					rep=false;
				}
			}//if
			else {
				i++;
			}//else
		}//while
		if(rep){
			// Affiche un message si la ville n'est pas trouvée dans l'agglomération.
			System.out.println(ville + " ne se trouve pas dans l'agglomération.");
		}//if
	}//decharge()
}// Class Agglomeration