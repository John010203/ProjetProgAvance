    // Import the File class
// Import the IOException class to handle errors
import java.io.*;
    import java.util.List;
    import java.util.Scanner;
    import java.util.ArrayList;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;


    public class MainFusion{
        public static void main(String[] args) throws IOException {

            Scanner s = new Scanner(System.in);
            int choix;
            boolean boucle =true;

            while(boucle){
                System.out.println("1 : Résoudre Manuellement");
                System.out.println("2 : Résoudre Automatiquement");
                System.out.println("3 : Sauvegarder");
                System.out.println("4 : Fin de Programme");

                choix = s.nextInt();
                s.nextLine();

                switch(choix){
                    case 1: 
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
                                System.out.println("La route reliant " + route1 + " à " + route2 + " a bien été ajouté.");
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
                            System.out.println("");
                            System.out.println("Voici les zones de recharges actuelles : " + Ag.getRecharge());
                            reponse = s.nextInt();
                            s.nextLine();
                            
                            switch(reponse) {
                            case 1:
                                System.out.println("Dans quelle ville souhaitez-vous ajouter une borne de recharge? ");
                                borne = s.nextLine();
                                Ag.recharge(borne);
                                System.out.println("la borne " + borne + " a bien été rechargé");
                                break;
                            case 2:
                                System.out.println("Dans quelle ville souhaitez-vous retirer une borne de recharge? ");
                                borne = s.nextLine();
                                Ag.decharge(borne);
                                System.out.println("la borne " + borne + " a bien été déchargé");
                                break;
                            case 3:
                                rep=false;
                                break;
                            }
                        }
                    case 2:
                        List<Ville> villesAutomatique = new ArrayList<Ville>();
                        Agglomeration AgAutomatique = new Agglomeration(villesAutomatique);
                        // Le fichier d'entrée
                        File file = new File("file.txt");

                        // Créer l'objet FileReader
                        FileReader fr = new FileReader(file);

                        // Créer l'objet BufferedReader
                        BufferedReader br = new BufferedReader(fr);

                        // Expression régulière pour "ville"
                        Pattern patternVille = Pattern.compile("ville\\(([A-Za-z])\\)");
                        Pattern patternRoute = Pattern.compile("route\\(([A-Za-z]),([A-Za-z])\\)");
                        Pattern patternRecharge = Pattern.compile("recharge\\(([A-Za-z])\\)");
                        Pattern patternDecharge = Pattern.compile("decharge\\(([A-Za-z])\\)");

                        // Lire ligne par ligne
                        String ligne;
                        while ((ligne = br.readLine()) != null){
                            
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
                                if (AgAutomatique.villeExiste(lettreRoute1) && AgAutomatique.villeExiste(lettreRoute2)){
                                    System.out.println("Création d'une route entre les villes " + lettreRoute1 + " et " + lettreRoute2);
                                    AgAutomatique.ajoutRoute(lettreRoute1, lettreRoute2);
                                } else {
                                    System.out.println(lettreRoute1 + " ou " + lettreRoute2 + " ne se trouve pas dans l'agglomération.");
                                }
                            //creation de la ville.

                            } else if (matcherVille.matches()) {

                                String lettreVille = matcherVille.group(1).toUpperCase();
                                System.out.println("Création de la ville : "+ lettreVille);
                                villesAutomatique.add(new Ville(String.valueOf(lettreVille.toUpperCase())));

                            }else if (matcherRecharge.matches()) {

                                String lettreRecharge = matcherRecharge.group(1).toUpperCase();
                                boolean villeTrouvee = false;

                                for (Ville ville : villesAutomatique) {
                                    if (ville.getNomVille().equalsIgnoreCase(lettreRecharge)) {
                                        villeTrouvee = true;
                                        if (!ville.getZoneDeRecharge()){
                                            AgAutomatique.recharge(lettreRecharge);
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

                                for (Ville ville : villesAutomatique) {
                                    if (ville.getNomVille().equalsIgnoreCase(lettreDecharge)) {
                                        villeTrouvee = true;
                                        if (ville.getZoneDeRecharge()){
                                            AgAutomatique.decharge(lettreDecharge);
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
                                System.out.println("Le texte ne correspond pas au format attendu : "+ligne);
                            }
                        }

                        AgAutomatique.imprimerRoutes();
                        System.out.println("voici les villes de l'agglomeration : " + AgAutomatique.getVilles());        
                        System.out.println("Voici les zones de recharges actuelles : " + AgAutomatique.getVillesRecharge());
                        break;
                    

                case 3:
                    try {
                        System.out.println("Veuillez Entrez le nom du Fichier ou contiendra la sauvegarde (sans le .txt).");
                        String nomFichier = s.nextLine();
                        File sauvegardeFichier = new File(nomFichier+".txt");
                        if (sauvegardeFichier.createNewFile()){
                          System.out.println("File created: " + sauvegardeFichier.getName());
                        } else {
                          System.out.println("Le Fichier existe déjà.");
                        }
                        FileWriter ecrireFichier = new FileWriter(nomFichier+".txt");
                        ecrireFichier.write("Vincent t'es trop beau!");
                        ecrireFichier.close();
                        System.out.println("l'écriture a été effectué avec succès.");

                      } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                      }
                      
                    break;
                    
                case 4:
                    System.out.println("Fin de programme.");
                    System.exit(0);
                    break;
                    }
                }
            // Fermer le BufferedReader
            s.close();
        }
    }
