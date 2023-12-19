    // Import the File class
// Import the IOException class to handle errors
import java.io.*;
    import java.util.List;
    import java.util.Scanner;
    import java.util.ArrayList;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    public class Main{
        private static Agglomeration AgSauvegarde;
        
        static boolean SoluceAuto;
        static boolean SoluceManuelle;

        public static void main(String[] args) throws IOException{

    // Lire ligne par ligne
            String ligne;
    // Expression régulière pour "ville" "route".
            Pattern patternVille = Pattern.compile("ville\\(([A-Za-z])\\)");
            Pattern patternRoute = Pattern.compile("route\\(([A-Za-z]),([A-Za-z])\\)");

    // Le fichier d'entrée

            File file = new File("file.ca");
    // Créer l'objet FileReader
            FileReader fr = new FileReader(file);
                    
            // Créer l'objet BufferedReader
            BufferedReader br = new BufferedReader(fr);

            Scanner s = new Scanner(System.in);
            int choix;
            boolean boucle =true;

            while(boucle){
                System.out.println("1 : Résoudre Manuellement");
                System.out.println("2 : Résoudre Automatiquement");
                System.out.println("3 : Sauvegarder");
                System.out.println("4 : Fin de Programme");
                choix = 0; // Initialisez choix à une valeur en dehors de la plage attendue pour entrer dans la boucle
                while (choix < 1 || choix > 4) {
                    System.out.println("Entrez votre choix (entre 1 et 4 inclus) : ");
                    if (s.hasNextInt()) {
                        choix = s.nextInt();
                        s.nextLine(); // Ajoutez cette ligne pour consommer la fin de ligne
                        if (choix < 1 || choix > 4) {
                            System.out.println("Veuillez entrer un nombre entre 1 et 4.");
                        }
                    } else {
                        System.out.println("Veuillez entrer un nombre valide.");
                        s.nextLine(); // Consommez l'entrée incorrecte pour éviter une boucle infinie
                    }
                }
                switch(choix){
                    //Résoudre Manuellement 
                    case 1: 
                        int nbVilles=0;
                        int reponse;
                        String route1, route2, borne;
   
                        List<Ville> villes = new ArrayList<Ville>();
                        
                            // Vérifier si le fichier existe
                            if (!file.exists()) {
                                throw new FileNotFoundException("Le fichier file.ca n'a pas été trouvé.");
                            }
                            // // Lire ligne par ligne
                            // String ligne;
                            while ((ligne = br.readLine()) != null){
                                            
                                Matcher matcherVille = patternVille.matcher(ligne.trim());
                                if (matcherVille.matches()) {
                        
                                        String lettreVille = matcherVille.group(1).toUpperCase();
                                        System.out.println("Création de la ville : " + lettreVille);
                                        villes.add(new Ville(String.valueOf(lettreVille.toUpperCase())));
                                }
                            }
                        Agglomeration Ag = new Agglomeration(villes);

                        System.out.println("L'agglomération à " + nbVilles + " villes");
                        System.out.println(Ag.getVilles());
                        
                        boolean rep=true;
                        while(rep){
                            System.out.println("1. Ajouter une route.");
                            System.out.println("2. fin.");
                            
                            reponse = s.nextInt();
                            s.nextLine();
                            
                            switch(reponse){
                            case 1:
                                System.out.println("Dans quelle ville de départ souhaitez-vous ajouter une route ");
                                route1 = s.nextLine();
                                System.out.println("Dans quelle ville de fin souhaitez-vous ajouter une route ");
                                route2 = s.nextLine();
                                try {
                                    Ag.ajoutRoute(route1, route2);
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Erreur : " + e.getMessage());
                                }
                                break;
                            case 2:
                                rep=false;
                                break;
                            }
                        }
                        
                        rep=true;
                        while(rep){
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
                                try {
                                    Ag.recharge(borne);
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Erreur : " + e.getMessage());
                                }
                                break;

                            case 2:
                                System.out.println("Dans quelle ville souhaitez-vous retirer une borne de recharge? ");
                                borne = s.nextLine();
                                 try {
                                    Ag.decharge(borne);
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Erreur : " + e.getMessage());
                                }
                                break;
                            case 3:
                                rep=false;
                                break;
                            }
                        }
                
                        AgSauvegarde=Ag;
                        SoluceAuto=false;
                        SoluceManuelle=true;

                        break;
                        //soloution AUTOMATIQUE a partir du fichier file.txt
///////////////////    fin case 1                      ///////////////////////////////////////
                    case 2:
                        List<Ville> villesAutomatique = new ArrayList<Ville>();
                        Agglomeration AgAutomatique = new Agglomeration(villesAutomatique);
                    
                        // Le fichier d'entrée
                        // File file = new File("file.ca");
                    
                        try {
                            // Vérifier si le fichier existe
                            if (!file.exists()) {
                                throw new FileNotFoundException("Le fichier file.ca n'a pas été trouvé.");
                            }
                            // // Lire ligne par ligne
                            // String ligne;
                            while ((ligne = br.readLine()) != null) {
                                Matcher matcherRoute = patternRoute.matcher(ligne.trim()); 
                                Matcher matcherVille=patternVille.matcher(ligne.trim());
                                // Utilisation des if-else pour déterminer le type de la ligne
                                if (matcherRoute.matches()) {
                                    String lettreRoute1 = matcherRoute.group(1).toUpperCase();
                                    String lettreRoute2 = matcherRoute.group(2).toUpperCase();
                    
                                    // Vérifier si les villes existent dans l'agglomération avant de créer la route
                                    if (AgAutomatique.villeExiste(lettreRoute1) && AgAutomatique.villeExiste(lettreRoute2)) {
                                        AgAutomatique.ajoutRoute(lettreRoute1, lettreRoute2);
                                    } else {
                                        System.out.println(lettreRoute1 + " ou " + lettreRoute2 + " ne se trouve pas dans l'agglomération.");
                                    }
                                } else if (matcherVille.matches()) {
                    
                                    String lettreVille = matcherVille.group(1).toUpperCase();
                                    System.out.println("Création de la ville : " + lettreVille);
                                    villesAutomatique.add(new Ville(String.valueOf(lettreVille.toUpperCase())));
        
                                } else {
                                    System.out.println("Le texte ne correspond pas au format attendu : " + ligne);
                                }
                            }
                    
                            br.close();
                            fr.close();

                            AgAutomatique.imprimerRoutes();
                            System.out.println("voici les villes de l'agglomération : " + AgAutomatique.getVilles());
                            System.out.println("Voici les zones de recharges actuelles : " + AgAutomatique.getVillesRecharge());
                    
                        } catch (FileNotFoundException e) {
                            System.out.println("Erreur : " + e.getMessage());
                        } catch (IOException e) {
                            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
                        }
                        AgSauvegarde=AgAutomatique;
                        SoluceManuelle=false;
                        SoluceAuto=true;

                        //Fonction qui lance l'algorithme.
                        break;
///////////////////                fin case 2                      ///////////////////////////////////////

                    case 3:
                        if(AgSauvegarde==null){
                            System.out.println("Il n'ya aucune solution pour l'instant, veuillez effectuer le choix 1 , 2 ou 4.");
                            break;
                        }
                    // Permet de Sauvegarder dans un fichier txt la solution.
                        try {
                            System.out.println("Veuillez Entrez le nom du Fichier ou contiendra la sauvegarde (sans le '.ca').");
                            String nomFichier = s.nextLine();
                            File sauvegardeFichier = new File(nomFichier+".ca");
                            if (sauvegardeFichier.createNewFile()){
                            System.out.println("Fichier crée : " + sauvegardeFichier.getName());
                            } else {
                            System.out.println("Le Fichier existe déjà.");
                            }

                            FileWriter ecrireFichier = new FileWriter(nomFichier+".ca");
                            if(SoluceAuto=true){
                                ecrireFichier.write(AgSauvegarde.AlgorithmeGlouton());
                            }
                            else if(SoluceManuelle=true){
                                ecrireFichier.write(AgSauvegarde.getVilles()+ "\n");
                                ecrireFichier.write(AgSauvegarde.imprimerRoutes() + "\n");
                            }
                            else{
                                System.out.println("Erreur de Sauvegarde.");
                            }
                            ecrireFichier.close();
                            System.out.println("l'écriture a été effectué avec succès.");

                        } catch (IOException e) {
                            System.out.println("Erreur d'écriture.");
                            e.printStackTrace();
                        }
                        break;
                        
///////////////////                fin case 3                  ///////////////////////////////////////

                    case 4:
                        System.out.println("Fin de programme.");
                        System.exit(0);
                        break;
///////////////////                fin case 4                  ///////////////////////////////////////

                        }
                    }
                // Fermer le BufferedReader
                s.close();
            }
    }
