import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

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
		for (int i=0; i< villes.size();i++) {//parcours la liste de ville
			if(villes.get(i).getZoneDeRecharge()) {//Vérifie si la ville a une borne
				sb.append(villes.get(i).getNomVille());
				sb.append(" ");
			}
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
		for (Route routes2 : this.routes){//Vérifie si la route existe déjà
			if ((routes2.getVilleA().equals(routeVille.getVilleA())) && (routes2.getVilleB().equals(routeVille.getVilleB())) || (routes2.getVilleA().equals(routeVille.getVilleB())) && (routes2.getVilleB().equals(routeVille.getVilleA()))){
				System.out.println("La route reliant " + routeVille.getVilleA().getNomVille() + " à " + routeVille.getVilleB().getNomVille() + " existe déjà.");			
				existe=true;
				break;
			}//if
		}//for
		if(!existe){
			routes.add(routeVille);//ajoute la Ville dans la liste routes.
			routeVille.getVilleA().setVoisins(routeVille.getVilleB());
			routeVille.getVilleB().setVoisins(routeVille.getVilleA());
		}
	}
	//setRoute()
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

	//Méthode qui vérifie si la ville spécifié existe dans l'agglomération
	private boolean villeExistante(String ville) {
		boolean rep = true;
		int i = 0;
		while(rep && i < this.villes.size()) {//Parcours la liste de ville jusqu'à trouver la ville ou atteindre la fin
			if(this.villes.get(i).getNomVille().equals(ville)){
				return true;
			}
			i++;
		}
		rep=false;
		return false;
	}

	//Méthode qui renvoie true si la ville n'a aucun voisin sinon false
	private boolean sansVoisins(Ville ville) {
		return ville.voisinsSansRecharge().isEmpty() && ville.voisinsAvecRecharge().isEmpty();
	}

	//Méthode qui renvoie true si la ville a au moins un voisin avec une borne de recharge sinon false
	private boolean voisinsRecharge(Ville ville) {
		List<Ville> voisinsSansRecharge = ville.voisinsSansRecharge();
		List<Ville> voisinsAvecRecharge = ville.voisinsAvecRecharge();

		if(voisinsSansRecharge.isEmpty() && !voisinsAvecRecharge.isEmpty()) {//tous les voisins ont une borne
			return true;
		}
		else if(!ville.voisinsSansRecharge().isEmpty() && !ville.voisinsAvecRecharge().isEmpty()){
			for(Ville voisinSansRecharge : voisinsSansRecharge) {
				if(voisinSansRecharge.voisinsAvecRecharge().size()==0 || voisinSansRecharge.voisinsAvecRecharge().size()==1) {
					System.out.println("possède un voisin sans recharge.");
					return false;
				}
			}
			return true;
		}
		else {
			return false;//Tous les voisins n'ont pas de borne
		}
	}
	
	//Méthode qui désactive la borne de recharge d'une ville spécifié si tous les voisins respectent la condition d'accessibilité
	private void setDecharge(Ville ville) {
		if(sansVoisins(ville)) {
			System.out.println(ville.getNomVille() + " n'a pas de voisins, vous ne pouvez pas retirer sa borne de recharge.");
		}
		else if(voisinsRecharge(ville)){
			//Désactive la borne de recharge et met à jour la liste des bornes de recharges construites
			ville.setZoneDeRecharge(false);
			this.recharge.remove(ville);
			System.out.println(ville.getNomVille() + " a retirer sa borne de recharge.");
		}
	}
	//void decharge(String ville),
	//Désactive la zone de recharge pour une ville spécifiée, 
	//en la retirant de la liste des points de recharge, sous réserve de certaines conditions.
	public void decharge(String ville) {
		if(!villeExistante(ville)) {//Vérifie si la ville existe dans l'agglomération
			System.out.println(ville + " ne se trouve pas dans l'agglomération.");
		}
		else {
			for(int i=0;i < this.villes.size();i++) {//Parcours la liste des villes
				if(this.villes.get(i).getNomVille().equals(ville)){//Vérifie si la ville correspond à la ville spécifié
					if(!this.villes.get(i).getZoneDeRecharge()){//Vérifie si la ville n'a pas de borne de recharge
						System.out.println(ville + " n'a pas de borne de recharge.");	
					}
					else {
						System.out.println("La borne a bien été dechargé.");
						setDecharge(this.villes.get(i));//Apelle de la méthode qui vérifie si la borne peut être retiré
					}
				}
			}
		}
	}//decharge()

	public String AlgorithmeGlouton(){
		//Etape 1 : Tri des routes par nombre total de voisins
		Collections.sort(routes, (ville1,ville2) -> Integer.compare(ville1.getVilleA().getListVoisins().size(), ville2.getVilleA().getListVoisins().size()));
		//Etape 2 : Initialisation
		for(Ville ville : villes) {
			ville.setZoneDeRecharge(false);
		}
		List<Ville> bornesConstruites = new ArrayList<>();
		//Etape 3 : Parcours des routes triées
		for(Route route : routes) {
			Ville villeA = route.getVilleA();
			Ville villeB = route.getVilleB();
			//Vérifie si au moins une des villes est déjà couverte
			if((!villeA.getZoneDeRecharge() && villeA.voisinsAvecRecharge().size()==0) || (!villeB.getZoneDeRecharge() && villeB.voisinsAvecRecharge().size()==0)) {
				//Aucune des villes n'est couverte, on ajoute une borne dans la ville avec le plus de voisin
				Ville villeAvecBorne = (villeA.getListVoisins().size() > villeB.getListVoisins().size()) ? villeA : villeB;
				villeAvecBorne.setZoneDeRecharge(true);
				bornesConstruites.add(villeAvecBorne);
				System.out.println("Borne de recharge contruite à : " + villeAvecBorne.getNomVille());
			}
		}
		//Ajout de bornes aux villes non reliés à d'autres villes par une route
		for(Ville ville : villes) {
			if(!ville.getZoneDeRecharge() && ville.getListVoisins().isEmpty()) {
				ville.setZoneDeRecharge(true);
				bornesConstruites.add(ville);
				System.out.println("Borne de recharge contruite à : " + ville.getNomVille());
			}
		}
		return this.getRecharge();
	}

	// Méthode qui renvoie un boolean pour vérifier l'existence d'une ville
	public boolean villeExiste(String nomVille) {
		// Obtient une chaîne contenant les noms de toutes les villes dans l'agglomération.
		String nomsVilles = getVilles();
		// Vérifie si la chaîne des noms de villes contient le nom de la ville spécifiée.
		return nomsVilles.toUpperCase().contains(nomVille.toUpperCase());
	}

	// Méthode qui permet d'imprimer les routes de l'agglomération.
	public String imprimerRoutes() {
		StringBuffer sb = new StringBuffer("Routes de l'agglomération : \n ");
		// Parcours toutes les routes de l'agglomération.
		for (Route route : this.routes){
			// Imprime les villes reliées par chaque route.
			sb.append("["+ route.getVilleA().getNomVille() + "," + route.getVilleB().getNomVille()+"]" + "\n");
		}
		return sb.toString();
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
	public List<Route> getRoutes() {
		return this.routes;
	}

    public static void SauvergarderFichier(Agglomeration Agglo,int choix){
        try {
        Scanner scan = new Scanner(System.in);

        System.out.println("Veuillez Entrez le nom du Fichier ou contiendra la sauvegarde (sans le '.ca').");

        String nouveauFichier = scan.nextLine();

        File sauvegardeFichier = new File(nouveauFichier+".ca");
        
        if (sauvegardeFichier.createNewFile()){
        System.out.println("Fichier crée : " + sauvegardeFichier.getName());
        } else {
        System.out.println("Le Fichier existe déjà.");
        }
        scan.close();
        FileWriter ecrireFichier = new FileWriter(nouveauFichier+".ca");
		if(choix==2){
			ecrireFichier.write("La Solution de l'Algorithme glouton est  : "+ Agglo.AlgorithmeGlouton());
		}
		else{

			ecrireFichier.write("Les Villes sont: " + Agglo.getVilles());
			ecrireFichier.write("La Solution est : ");

			ecrireFichier.write(" " + Agglo.getVillesRecharge());
		}
        ecrireFichier.close();
        System.out.println("l'écriture a été effectué avec succès.");
	}
     catch (IOException e) {
        System.out.println("Erreur d'écriture.");
        e.printStackTrace();
    }                        
    }

    public static Agglomeration NouveauAgglomeration(Agglomeration Ag,String cheminFichier){
        try{
            String ligne;
            List<Ville> villes = new ArrayList<Ville>();
            Ag = new Agglomeration(villes);
            File file = new File(cheminFichier); // Utilise le chemin du fichier fourni en paramètre
            FileReader fileRead = new FileReader(file);
            // Créer l'objet BufferedReader
            BufferedReader bufferRead = new BufferedReader(fileRead);
            // Expression régulière pour "ville"
            Pattern patternVille = Pattern.compile("ville\\(([A-Za-z])\\)");
            Pattern patternRoute = Pattern.compile("route\\(([A-Za-z]),([A-Za-z])\\)");
            Pattern patternRecharge = Pattern.compile("recharge\\(([A-Za-z])\\)");
            Pattern patternDecharge = Pattern.compile("decharge\\(([A-Za-z])\\)");

                    while ((ligne = bufferRead.readLine()) != null){
                            // Vérifier si la ligne correspond au format attendu
                            Matcher matcherRoute=patternRoute.matcher(ligne.trim());
                            Matcher matcherVille=patternVille.matcher(ligne.trim());
                            Matcher matcherRecharge=patternRecharge.matcher(ligne.trim());
                            Matcher matcherDecharge=patternDecharge.matcher(ligne.trim());

                            // Utilisation des if-else pour déterminer le type de la ligne
                            if (matcherRoute.matches()) {

                                String lettreRoute1=matcherRoute.group(1).toUpperCase();
                                String lettreRoute2=matcherRoute.group(2).toUpperCase();

                                // Vérifier si les villes existent dans l'agglomération avant de créer la route
                                if (Ag.villeExiste(lettreRoute1) && Ag.villeExiste(lettreRoute2)){
                                    System.out.println("Création d'une route entre les villes " + lettreRoute1 + " et " + lettreRoute2);
                                    Ag.ajoutRoute(lettreRoute1, lettreRoute2);
                                } else {
                                    System.out.println(lettreRoute1 + " ou " + lettreRoute2 + " ne se trouve pas dans l'agglomération.");
                                }
                            //creation de la ville.

                            } else if (matcherVille.matches()) {

                                String lettreVille = matcherVille.group(1).toUpperCase();
                                System.out.println("Création de la ville : "+ lettreVille);
                                villes.add(new Ville(String.valueOf(lettreVille.toUpperCase())));

                            }else if (matcherRecharge.matches()) {

                                String lettreRecharge = matcherRecharge.group(1).toUpperCase();
                                boolean villeTrouvee = false;

                                for (Ville ville : villes) {
                                    if (ville.getNomVille().equalsIgnoreCase(lettreRecharge)) {
                                        villeTrouvee = true;
                                        if (!ville.getZoneDeRecharge()){
                                            Ag.recharge(lettreRecharge);
                                        } else {
                                            System.out.println(lettreRecharge + " a déjà une borne de recharge.");
                                        }
                                        break;  // Sortir de la boucle une fois la ville trouvée
                                    }
                                }
                                if (!villeTrouvee) {
                                    System.out.println(lettreRecharge + " ne se trouve pas dans l'agglomération.");
                                }
                            } 

                            //pour decharger la ville.
                            else if (matcherDecharge.matches()) {

                                String lettreDecharge = matcherDecharge.group(1).toUpperCase();
                                boolean villeTrouvee = false;

                                for (Ville ville : villes) {
                                    if (ville.getNomVille().equalsIgnoreCase(lettreDecharge)) {
                                        villeTrouvee = true;
                                        if (ville.getZoneDeRecharge()){
                                            Ag.decharge(lettreDecharge);
                                        } else {
                                            System.out.println(lettreDecharge + "n'a pas de borne de recharge.");
                                        }
                                        break;  // Sortir de la boucle une fois la ville trouvée
                                    }
                                }
                                if (!villeTrouvee) {
                                    System.out.println(lettreDecharge + " ne se trouve pas dans l'agglomération.");
                                }
                            } 
                            else {
                                System.out.println("Le texte ne correspond pas au format attendu : "+ "<"+ ligne +">");
                            }
                        }
                        bufferRead.close();
                    }
                    catch(Exception e){
                        e.getMessage();
                    }
        return Ag;               
    }
}
