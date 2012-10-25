
public class FooPileRPL {


	public static void main(String[] args) {
		
		PileRPL pile = new PileRPL();

		try {
			pile.push(new ObjetEmpilable(1));
			pile.push(new ObjetEmpilable(2));
			pile.add();
			pile.push(new ObjetEmpilable(5));
			pile.sub();
			pile.push(new ObjetEmpilable(5));
			pile.mult();
			pile.push(new ObjetEmpilable(2));
			pile.div();
			System.out.println(pile);
		}
		catch(Exception e) { 
			System.out.println(e.getMessage());
		}
	}
}
