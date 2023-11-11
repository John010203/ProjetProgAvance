public class Route {//Attribut de Route.
	private Ville A;
	private Ville B;
	
	public Route(Ville A,Ville B){//Constructeur de Route.
		this.A=A;
		this.B=B;
	}
	
	public Ville getVilleA() {
		return A;
	}
	public Ville getVilleB() {
		return B;
	}
}
