import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwingGraphExample extends JPanel {
    private Agglomeration agglomeration;
    private Map<Ville, Point> villePositions;

    public SwingGraphExample(Agglomeration agglomeration) {
        this.agglomeration = agglomeration;
        this.villePositions = initializeVillePositions();
    }

    private Map<Ville, Point> initializeVillePositions() {
        // Initialisez les positions des villes
        int x = 100;
        int y = 100;
        for (Ville ville : agglomeration.getVilles()) {
            villePositions.put(ville, new Point(x, y));
            x += 100;
            y += 100;
        }
        return villePositions;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner les routes
        g.setColor(Color.BLACK);
        for (Ville ville : agglomeration.getVilles()) {
            Point posA = getCenter(ville);

            // Dessiner les routes vers les voisins de la ville
            for (Ville voisin : agglomeration.getVoisins(ville)) {
                Point posB = getCenter(voisin);
                g.drawLine(posA.x, posA.y, posB.x, posB.y);
            }
        }

        // Dessiner les villes et indiquer la présence de borne de recharge
        for (Ville ville : agglomeration.getVilles()) {
            g.setColor(Color.BLUE);
            // Dessiner une ellipse pour représenter la ville
            Point pos = getCenter(ville);
            g.fillOval(pos.x - 10, pos.y - 10, 20, 20);

            // Utiliser le nom de la ville pour le texte
            g.setColor(ville.getZoneDeRecharge() ? Color.GREEN : Color.RED);
            g.drawString(ville.getNomVille(), pos.x - 5, pos.y + 5);
        }
    }

    private Point getCenter(Ville ville) {
        return villePositions.get(ville);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graph Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);

            List<Ville> villes = new ArrayList<>();
            villes.add(new Ville("A"));
            villes.add(new Ville("B"));
            villes.add(new Ville("C"));
            villes.add(new Ville("D"));

            List<Route> routes = new ArrayList<>();
            routes.add(new Route(villes.get(0), villes.get(1)));
            routes.add(new Route(villes.get(1), villes.get(2)));

            SwingGraphExample graphPanel = new SwingGraphExample(agglomeration);
            frame.add(graphPanel);

            frame.setVisible(true);
        });
    }
}