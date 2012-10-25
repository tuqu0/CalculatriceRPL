
public class PileRPL {
	
	private int NBOBJSMAX = 4;
	private int nbObjs = 0;
	private ObjetEmpilable[] pile;
	
	// Constructeur par défaut
	public PileRPL() {
		pile = new ObjetEmpilable[NBOBJSMAX];
	}
	
	// Constructeur permettant de définir la taille de la pile
	public PileRPL(int size) {
		NBOBJSMAX = size;
		pile = new ObjetEmpilable[NBOBJSMAX];
	}

	// Surchage de la méthode toString()
	public String toString() {
		String s = new String();
		
		for (int i=0; i < nbObjs; i++)
			s += (pile[i]).toString() + '\n';
		return s;
	}
	
	// Ajoute un ObjetEmpilable sur la pile
	public void push(ObjetEmpilable elt) throws CalcException {
		if (nbObjs == NBOBJSMAX) {
			throw new CalcException("La pile est pleine");
		}
		pile[nbObjs++] = elt;
	}
	
	// Renvoie le dernier élément de la pile
	public ObjetEmpilable pop() throws CalcException {
		if (nbObjs == 0) {
			throw new CalcException("La pile est vide");
		}
		return pile[nbObjs - 1];
	}
	
	// Enlève le dernier élément de la pile
	public void drop() throws CalcException {
		if (nbObjs == 0) {
			throw new CalcException("La pile est vide");
		}
		pile[--nbObjs] = null;	
	}
	
	// Contrôle si la pile est vie
	public Boolean isEmpty() {
		return (pile.length == 0);
	}
	
	// Contrôle si la pile est pleine
	public Boolean isFull() {
		return (pile.length == NBOBJSMAX);
	}
	
	// Permute les deux derniers éléments de la pile
	public void swap() throws CalcException {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;
		
		if (nbObjs == 0) {
			throw new CalcException("La pile est vide");
		}
		else if (nbObjs > 1) {
			tmp1 = pop();
			drop();
			tmp2 = pop();
			drop();
			push(tmp1);
			push(tmp2);
		}
	}
	
	// Opération addition
	public void add() throws CalcException {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;
		
		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp1.add(tmp2);
		push(tmp1);
	}

	// Opération soustraction
	public void sub() throws CalcException {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;
		
		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp2.sub(tmp1);
		push(tmp2);		
	}
	
	// Opération multiplication	
	public void mult() throws CalcException {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;
		
		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp1.mult(tmp2);
		push(tmp1);			
	}
	
	// Opération division
	public void div() throws CalcException {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;
		
		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp2.div(tmp1);
		push(tmp2);			
	}
}
