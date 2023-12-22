import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class nouveauMain {
    public static void main(String[] args){
        try{        
        List<Ville> villes = new ArrayList<Ville>();
        Agglomeration Ag = new Agglomeration(villes);
        if(args.length==0){
            System.out.println("Il n'y a aucun fichier pris en paramètre, Veuillez en choisir un.");
            System.exit(1);
        }

        String cheminFichier = args[0];
        Path chemin = Paths.get(cheminFichier);

        if(Files.notExists(chemin)){
            System.out.println("Le fichier n'existe pas,Veuillez entrer un fichier existant.");
            System.exit(2);
        }
        File file = new File(cheminFichier); // Utiliser le chemin du fichier fourni en paramètre
        FileReader fileRead = new FileReader(file);
        // Créer l'objet BufferedReader
        BufferedReader bufferRead = new BufferedReader(fileRead);
        Scanner scanner = new Scanner(System.in);
        int choixSwitch;
        do {
            System.out.println("1 : Résoudre Manuellement");
            System.out.println("2 : Résoudre Automatiquement");
            System.out.println("3 : Sauvegarder");
            System.out.println("4 : Fin de Programme");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre entier.");
                scanner.next(); // consommer l'entrée invalide
            }

            choixSwitch = scanner.nextInt();
            int choix=0;

            scanner.nextLine(); // Consommer la nouvelle ligne
            switch (choixSwitch){
                case 1:
                    Ag = Agglomeration.NouveauAgglomeration(Ag, cheminFichier);
                    Scanner s = new Scanner(System.in);
                    int reponse;
                    String borne;
                
                    do {
                        System.out.println("1. Ajouter une borne de recharge.");
                        System.out.println("2. Retirer une borne de recharge.");
                        System.out.println("3. fin.");
                        System.out.println("");
                        System.out.println("Voici les zones de recharges actuelles : " + Ag.getRecharge());

                        while (!s.hasNextInt()) {
                            System.out.println("Veuillez entrer un nombre entier.");
                            s.next(); // consommer l'entrée invalide
                        }

                        reponse = s.nextInt();
                        s.nextLine(); // Consommer la nouvelle ligne

                        if (reponse == 1) {
                            System.out.println("Dans quelle ville souhaitez-vous ajouter une borne de recharge? ");
                            borne = s.nextLine();
                            Ag.recharge(borne);
                            break;
                        } else if (reponse == 2) {
                            System.out.println("Dans quelle ville souhaitez-vous retirer une borne de recharge? ");
                            borne = s.nextLine();
                            Ag.decharge(borne);
                            break;
                        }
                    } while (reponse != 3);
                    choix=1;
            case 2:
                Ag=Agglomeration.NouveauAgglomeration(Ag,cheminFichier);
                System.out.println(Ag.AlgorithmeGlouton());
                choix=2;
                break;
            case 3:
                Agglomeration.SauvergarderFichier(Ag,choix);
                break;
            case 4:
                System.out.println("Fin de Programme.");
                break;
            default:
                System.out.println("Erreur, l'option n'est pas valide.");
            }
            
        } while (choixSwitch != 4);

        scanner.close(); // Fermez le scanner après avoir terminé
        bufferRead.close();
        }
        catch(Exception e){
            e.getMessage();
        }
    }



    }  


