
public class PileRPL {

	private int NBOBJSMAX = 30;
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
			s += "index" + i + " : | " + (pile[i]).toString() + " | \n";
		return s;
	}

	// Ajoute un ObjetEmpilable sur la pile
	public void push(ObjetEmpilable elt) throws CalcException {
		if (nbObjs == NBOBJSMAX) {
			throw new CalcException("error: impossible d'ajouter un nouvel élément car la pile est pleine");
		}
		pile[nbObjs++] = elt;
	}

	// Renvoie le dernier élément de la pile
	public ObjetEmpilable pop() throws CalcException {
		if (nbObjs == 0) {
			throw new CalcException("error: impossible de récupérer le dernier élélement car la pile est vide");
		}
		return pile[nbObjs - 1];
	}

	// Enlève le dernier élément de la pile
	public void drop() throws CalcException {
		if (nbObjs == 0) {
			throw new CalcException("error: impossible de supprimer le dernier élement car la pile est vide");
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
			throw new CalcException("error: impossible de swaper les deux derniers éléments car la pile est vide");
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

		if (nbObjs < 2)
			throw new CalcException("error: paramètre manquant pour l'addition");

		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp1.add(tmp2);
		push(tmp1);
	}

	// Opération soustraction
	public void sub() throws CalcException  {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;

		if (nbObjs < 2)
			throw new CalcException("error: paramètre manquant pour la soustraction");

		swap();
		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp1.sub(tmp2);
		push(tmp1);		
	}

	// Opération multiplication	
	public void mult() throws CalcException {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;

		if (nbObjs < 2)
			throw new CalcException("error: paramètre manquant pour la multiplication");

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

		if (nbObjs < 2)
			throw new CalcException("error: paramètre manquant pour la division");

		swap();
		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp1.div(tmp2);
		push(tmp1);			
	}
}
