import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class TestAgglomeration {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int nbVilles, i, reponse;
		String route1, route2, borne;
		do {
			System.out.println("Entrez le nombre de villes (entre 1 et 26 inclus) : ");
			nbVilles = s.nextInt();
		}
		while(nbVilles>26 && nbVilles <= 0);
		
		List<Ville> villes = new ArrayList<Ville>();
		
		for(i=0;i<nbVilles;i++) {
			char nomVille = (char)('A'+i);
			villes.add(new Ville(String.valueOf(nomVille)));
		}
		
		Agglomeration Ag = new Agglomeration(villes);
		System.out.println("L'agglomération à " + nbVilles + " villes");
		System.out.println(Ag.getVilles());
		
		boolean rep=true;
		while(rep) {
			System.out.println("1. Ajouter une route.");
			System.out.println("2. fin.");
			
			reponse = s.nextInt();
			s.nextLine();
			
			switch(reponse) {
			case 1:
				System.out.println("Dans quelle ville de départ souhaitez-vous ajouter une route ");
				route1 = s.nextLine();
				System.out.println("Dans quelle ville de fin souhaitez-vous ajouter une route ");
				route2 = s.nextLine();
				Ag.ajoutRoute(route1, route2);
				break;
			case 2:
				rep=false;
				break;
			}
		}
		
		rep=true;
		while(rep) {
			System.out.println("1. Ajouter une borne de recharge.");
			System.out.println("2. Retirer une borne de recharge.");
			System.out.println("3. fin.");
			System.out.println("Voici les zones de recharges actuelles : " + Ag.getRecharge());
			reponse = s.nextInt();
			s.nextLine();
			
			switch(reponse) {
			case 1:
				System.out.println("Dans quelle ville souhaitez-vous ajouter une borne de recharge? ");
				borne = s.nextLine();
				Ag.recharge(borne);
				break;
			case 2:
				System.out.println("Dans quelle ville souhaitez-vous retirer une borne de recharge? ");
				borne = s.nextLine();
				Ag.decharge(borne);
				break;
			case 3:
				rep=false;
				break;
			}
		}
		s.close();
	}
}