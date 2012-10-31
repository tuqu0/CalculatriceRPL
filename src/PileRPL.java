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

	// Surchage de la méthode toString
	public String toString() {
		String s = new String();

		s = "\n-- PILE --\n";
		for (int i=0; i < nbObjs; i++)
			s += "index" + i + " |" + (pile[i]).toString() + "|\n";
		return s;
	}

	/*
	 *  Ajoute un ObjetEmpilable sur la pile.
	 *  Si la pile est pleine, on tente de réallouer
	 *  une pile par bloc de 30 éléments
	 */
	public boolean push(ObjetEmpilable elt) {
		if (isFull() && !realloc(30))
			return false;
		pile[nbObjs++] = elt;
		return true;
	}

	// Renvoie le dernier élément de la pile
	public ObjetEmpilable pop() {
		if (isEmpty())
			return null;
		return pile[nbObjs - 1];
	}

	// Enlève le dernier élément de la pile
	public boolean drop() {
		if (isEmpty())
			return false;
		pile[--nbObjs] = null;
		return true;
	}

	// Réalloue une nouvelle pile
	public boolean realloc(int nbmore) {
		try {
			ObjetEmpilable[] tmp = new ObjetEmpilable[pile.length + nbmore];

			for (int i = 0; i < pile.length; i++)
				tmp[i] = pile[i];

			NBOBJSMAX += nbmore;
			pile = tmp;	
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	// Contrôle si la pile est vie
	public boolean isEmpty() {
		return (nbObjs == 0);
	}

	// Contrôle si la pile est pleine
	public boolean isFull() {
		return (nbObjs == NBOBJSMAX);
	}

	// Permute les deux derniers éléments de la pile
	public boolean swap() {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;

		if (nbObjs > 1) {
			tmp1 = pop();
			drop();
			tmp2 = pop();
			drop();
			push(tmp1);
			push(tmp2);
			return true;
		}
		return false;
	}

	// Opération addition
	public boolean add() {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;

		if (nbObjs < 2)
			return false;

		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp1.add(tmp2);
		push(tmp1);
		return true;
	}

	// Opération soustraction
	public boolean sub() {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;

		if (nbObjs < 2)
			return false;

		swap();
		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp1.sub(tmp2);
		push(tmp1);	
		return true;
	}

	// Opération multiplication	
	public boolean mult() {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;

		if (nbObjs < 2)
			return false;

		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();
		tmp1.mult(tmp2);
		push(tmp1);
		return true;
	}

	// Opération division
	public boolean div() {
		ObjetEmpilable tmp1;
		ObjetEmpilable tmp2;

		if (nbObjs < 2)
			return false;

		swap();
		tmp1 = pop();
		drop();
		tmp2 = pop();
		drop();

		if (tmp2.getVal() == 0) {
			push(tmp1);
			push(tmp2);
			return false;
		}

		tmp1.div(tmp2);
		push(tmp1);
		return true;
	}
}
