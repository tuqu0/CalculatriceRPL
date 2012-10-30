
public class ObjetEmpilable {

	private int val;

	// Constructeur
	public ObjetEmpilable(int i) {
		val = i;
	}

	// Surcharge de la méthode toString
	public String toString() {
		return Integer.toString(val);
	}

	// Getter for val
	public int getVal() {
		return val;
	}

	// Setter for val
	public void setVal(int i) {
		this.val = i;
	}
	
	// Opération addition
	public void add(ObjetEmpilable e) {
		val += e.getVal();
	}

	// Opération soustraction
	public void sub(ObjetEmpilable e) {
		val -= e.getVal();
	}

	// Opération multiplication	
	public void mult(ObjetEmpilable e) {
		val *= e.getVal();
	}

	// Opération division
	public void div(ObjetEmpilable e) {
		val /=  e.getVal();
	}	
}