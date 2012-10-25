
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
		val *= e.getVal();
	}

	// Opération division
	public void div(ObjetEmpilable e) throws CalcException {

		if (e.getVal() == 0)
			throw new CalcException("error: impossible de diviser " + val + " par 0");

		val /=  e.getVal();
	}	
}
