import java.util.List;
import java.util.ArrayList;

public class TestAgglomeration {
	public static void main(String[] args) {
		Ville v = new Ville("Paris");
		Ville v1 = new Ville("Marseille");
		List<Ville> a = new ArrayList<Ville>();
		a.add(v);
		a.add(v1);
		Agglomeration Ag = new Agglomeration(a);
		System.out.println(v.getNomVille());
		System.out.println(Ag.getVilles());
	}
}
