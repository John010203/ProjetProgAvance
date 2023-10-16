import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class TestAgglomeration {
	public static void main(String[] args) {
		Ville v = new Ville("Paris");
		Ville v1 = new Ville("Marseille");
		List<Ville> a = new ArrayList<Ville>();
		a.add(v);
		a.add(v1);
		Agglomeration Ag = new Agglomeration(a);
		System.out.println(Ag.getVilles());
		
		Scanner s = new Scanner(System.in);
		int i, reponse;
		String borne;
		do {
			System.out.println("Entrez le nombre de villes (entre 1 et 26 inclus) : ");
			i = s.nextInt();
		}
		while(i>26 && i < 0);
		System.out.println("L'agglomération à " + i + " villes");
		
		boolean rep=true;
		while(rep) {
			System.out.println("1. Ajouter une route.");
			System.out.println("2. fin.");
			reponse = s.nextInt();
			switch(reponse) {
			case 1:
				Ag.setRoute(new Route(v,v1));
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
			reponse = s.nextInt();
			switch(reponse) {
			case 1:
				System.out.println("Dans quelle ville souhaitez-vous ajouter une borne de recharge? ");
				borne = s.nextLine();
				v.setZoneDeRecharge(true);
				break;
			case 2:
				v.setZoneDeRecharge(false);
				break;
			case 3:
				rep=false;
				break;
			}
		}
	}
}
