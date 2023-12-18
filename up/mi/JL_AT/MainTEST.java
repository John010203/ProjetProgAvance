import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTEST {
    public static void main(String[] args) throws IOException {
        // Scanner s = new Scanner(System.in);
        List<Ville> villes = new ArrayList<Ville>();
        Agglomeration Ag = new Agglomeration(villes);
    
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
                System.out.println("Le texte ne correspond pas au format attendu : "+ligne);
            }
        }
        Ag.imprimerRoutes();
        System.out.println("voici les villes de l'agglomeration : " + Ag.getVilles());        
        System.out.println("Voici les zones de recharges actuelles : " + Ag.getVillesRecharge());
        
        // Fermer le BufferedReader
        br.close();

        // SwingUtilities.invokeLater(() -> {
        //     JFrame frame = new JFrame("Graph Visualization");
        //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //     frame.setSize(400, 400);

        //     // SwingGraphExample graphPanel = new SwingGraphExample(agglomeration);
        //     frame.add(graphPanel);

        //     frame.setVisible(true);
        // });
}


}
