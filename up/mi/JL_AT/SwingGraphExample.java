import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SwingGraphExample extends JPanel {
    List<Ville> villes;
    List<Route> routes;
    Map<Ville, Point> villePositions;

    public SwingGraphExample(List<Ville> villes, List<Route> routes) {
        this.villes = villes;
        this.routes = routes;
        this.villePositions = new HashMap<>();

        // Initialisez les positions des villes
        int x = 100;
        int y = 100;
        for (Ville ville : villes) {
            villePositions.put(ville, new Point(x, y));
            x += 100;
            y += 100;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner les routes
        g.setColor(Color.BLACK);
        for (Route route : routes) {
            Ville villeA = route.getVilleA();
            Ville villeB = route.getVilleB();

            Point posA = getCenter(villeA);
            Point posB = getCenter(villeB);

            // Dessiner la route entre villeA et villeB
            g.drawLine(posA.x, posA.y, posB.x, posB.y);
        }

        // Dessiner les villes et indiquer la présence de borne de recharge
        for (Ville ville : villes) {
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
        Point pos = villePositions.get(ville);
        return new Point(pos.x, pos.y);
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

            SwingGraphExample graphPanel = new SwingGraphExample(villes, routes);
            frame.add(graphPanel);

            frame.setVisible(true);
        });
    }
}