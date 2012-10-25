
public class ObjetEmpilable {

	private int val;
	
	public String toString() {
		return Integer.toString(val);
	}
	
	public ObjetEmpilable(int i) {
		val = i;
	}
	
	public int getVal() {
		return val;
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
		val = val * e.getVal();
	}
	
	// Opération division
	public void div(ObjetEmpilable e) {
		val = val / e.getVal();
	}	
}
