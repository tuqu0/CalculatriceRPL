import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;

public class OptionsManager {
	private final  static String auth_file = "users.config";
	private static Hashtable<String, String> users;
	private boolean log_session = false;
	private static String log_file = new String();
	private PileRPL pile;
	private PrintStream out;
	private Scanner in;

	// Teste si une chaîne peut être parsée en entier
	public static Boolean tryParseInt(String st)  {  
		try  {  
			Integer.parseInt(st);
			return true;  
		} catch(NumberFormatException e) {  
			return false;  
		}  
	}

	// Retourne le hash md5 d'une chaîne de caractères
	public static String md5Encode(String str) {
		String result = "";
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(str.getBytes("UTF8"));
			byte s[] = m.digest();

			for (int i = 0; i < s.length; i++)
				result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
			return result;  
		}
		catch (Exception e) { 
			return result;
		}
	}

	/*
	 *  Constructeur prenant en paramètres une pile, un flux d'entrée 
	 *	et un flux de sortie
	 */
	public OptionsManager(PileRPL p, Scanner input, PrintStream out) {
		this.in = input;
		this.out = out;
		this.pile = p;
	}

	// Getter for pile
	public PileRPL getPileRPL() {
		return pile;
	}

	// Setter for pile
	public void setPileRPL(PileRPL p) {
		pile = p;
	}

	// Getter for logSession
	public boolean getLogSession() {
		return log_session;
	}

	// Setter for logSession
	public void setLogSession(boolean b) {
		log_session = b;
	}

	// Getter for logSessionFilename
	public String getLogFile() {
		return log_file;
	}

	// Setter for logSessionFilename
	public void setLogFile(String s) {
		log_file = s;
	}

	// Affiche les options disponibles en mode local
	public void usageLocal() {
		out.println("\n*** MENU PRINCIPAL ***");
		out.println("0	Intéragir avec la pile");
		out.println("1	Log de session");
		out.println("2	Rejouer une session");
		out.println("3	Mode réseau");
		out.println("4	Mode console");
		out.println("5	Quitter\n");		
	}

	// Affiche les options disponibles pour un client
	public void usageRemote() {
		out.println("\n*** MENU PRINCIPAL ***");
		out.println("0	Intéragir avec la pile");
		out.println("1	Mode console");
	}

	/*
	 *  Mode console : Permet de saisir des opérations
	 *  et affiche chaque changement d'etat de la pile
	 */
	public void modConsole() {
		String line, token;
		StringTokenizer st;
		PrintWriter file = null;

		try {
			if (log_session)
				file = new PrintWriter(new FileWriter(log_file, true));

			while (!(line = in.nextLine()).equals("exit")){
				st = new StringTokenizer(line);

				while (st.hasMoreTokens()) {
					token = st.nextToken();

					if (tryParseInt(token)) {
						if (!pile.push(new ObjetEmpilable(Integer.parseInt(token))))
							out.println("erreur: impossible d'ajouter un nouvel élément");
					}
					else if (token.equals("+")) {
						if (!pile.add())
							out.println("erreur: paramètre manquant pour l'addition");
					}
					else if (token.equals("-")) {
						if (!pile.sub())
							out.println("erreur: paramètre manquant pour la soustraction");
					}
					else if (token.equals("*")) {
						if (!pile.mult())
							out.println("erreur: paramètre manquant pour la multiplication");
					}
					else if (token.equals("/")) {
						if (!pile.div())
							out.println("erreur: division impossible");
					}
					else {
						out.println("erreur: saisie incorrecte");
						break;
					}
					if (log_session)
						file.println(token);
					out.println(pile);				
				}
			}	
		}
		catch (Exception e) {
			out.println(e.getMessage());
		}
		finally {
			if (file != null)
				file.close();
		}
	}

	/*
	 * Mode Intélraction avec la pile.
	 * Permet de saisir des opérations, d'afficher chaque
	 * changement d'état de la pile et de saisir des commandes
	 * pour manipuler la pile ("push", "pop", "drop", "swap", "print")
	 */
	public void modStack() {
		String line, token;
		StringTokenizer st;
		Boolean push = false;
		PrintWriter file = null;

		try {			
			if (log_session)
				file = new PrintWriter(new FileWriter(log_file, true));

			while (!(line = in.nextLine()).equals("exit")){
				st = new StringTokenizer(line);

				while (st.hasMoreTokens()) {
					token = st.nextToken();

					if (token.equals("push") && !push)
						push = true;
					else if (push && tryParseInt(token)) {
						if (!pile.push(new ObjetEmpilable(Integer.parseInt(token))))
							out.println("erreur: impossible d'ajouter un nouvel élément");
						push = false;
					}
					else if (!push && tryParseInt(token)) {
						if (!pile.push(new ObjetEmpilable(Integer.parseInt(token))))
							out.println("erreur: impossible d'ajouter un nouvel élément");
					}
					else if (token.equals("+")) {
						if (!pile.add())
							out.println("erreur: paramètre manquant pour l'addition");		
					}
					else if (token.equals("-")) {
						if (!pile.sub())
							out.println("erreur: paramètre manquant pour la soustraction");
					}
					else if (token.equals("*")) {
						if (!pile.mult())
							out.println("erreur: paramètre manquant pour la multiplication");
					}
					else if (token.equals("/")) {
						if (!pile.div())
							out.println("erreur: division impossible");
					}
					else if (token.equals("drop")) {
						if (!pile.drop())
							out.println("erreur: la pile est vide");
					}
					else if (token.equals("swap")) {
						if (!pile.swap())
							out.println("erreur: il y a moins de deux éléments dans la pile");
					}
					else if (token.equals("pop")) {
						if (pile.pop() == null)
							out.println("erreur: la pile est vide");
						else
							out.println("dernier élément : " + (pile.pop()).getVal());
					}
					else if (!token.equals("print")) {
						out.println("erreur: saisie incorrecte");
						if (push)
							push = false;
						break;
					}
					if (log_session)
						file.println(token);
				}
				out.println(pile);				
			}	
		}
		catch (Exception e) {
			out.println(e.getMessage());
		}
		finally {
			if (file != null)
				file.close();
		}
	}

	/*
	 * Mode Rejeu de session : Permet de rejouer à partir d'un fichier
	 * une liste d'opérations et d'instructions avec la pile.
	 * Une pile unique est allouée pour le rejeu de la session
	 */
	public void modReplay(String file) { 
		String line, token;
		StringTokenizer st;
		Boolean push = false;
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(file));

			while ((line = reader.readLine()) != null){
				st = new StringTokenizer(line);

				while (st.hasMoreTokens()) {
					token = st.nextToken();
					out.println("\ntoken : " + token);

					if (token.equals("push") && !push)
						push = true;
					else if (push && tryParseInt(token)) {
						if (!pile.push(new ObjetEmpilable(Integer.parseInt(token))))
							out.println("erreur: impossible d'ajouter un nouvel élément");
						push = false;
					}
					else if (!push && tryParseInt(token)) {
						if (!pile.push(new ObjetEmpilable(Integer.parseInt(token))))
							out.println("erreur: impossible d'ajouter un nouvel élément");
					}
					else if (token.equals("+")) {
						if (!pile.add())
							out.println("erreur: paramètre manquant pour l'addition");		
					}
					else if (token.equals("-")) {
						if (!pile.sub())
							out.println("erreur: paramètre manquant pour la soustraction");
					}
					else if (token.equals("*")) {
						if (!pile.mult())
							out.println("erreur: paramètre manquant pour la multiplication");
					}
					else if (token.equals("/")) {
						if (!pile.div())
							out.println("erreur: division impossible");
					}
					else if (token.equals("drop")) {
						if (!pile.drop())
							out.println("erreur: la pile est vide");
					}
					else if (token.equals("swap")) {
						if (!pile.swap())
							out.println("erreur: il y a moins de deux éléments dans la pile");
					}
					else if (token.equals("pop")) {
						if (pile.pop() == null)
							out.println("erreur: la pile est vide");
						else
							out.println("dernier élément : " + (pile.pop()).getVal());
					}
					else if (!token.equals("print")) {
						out.println("erreur: saisie incorrecte");
						if (push)
							push = false;
						break;
					}
				}
				out.println(pile);				
			}
			reader.close();
		}
		catch (Exception e) {
			out.println(e.getMessage());
		}
	}

	/*
	 * Mode réseau :
	 */
	public void modNetwork(Integer port) {
		ServerSocket socketsrv = null;
		Socket socketclt = null;

		try {
			if (!loadUsers())
				return;

			socketsrv = new ServerSocket(port);
			out.println("Le serveur est à l'écoute du port "+ socketsrv.getLocalPort());

			while (true){
				socketclt = socketsrv.accept();
				out.println("Connexion d'un nouveau client depuis " + socketclt.getRemoteSocketAddress());
				new Client(socketclt);
			}
		}
		catch (Exception e) {
			out.println(e.getMessage());
		}
		finally {
			try { 
				if (socketsrv != null)
					socketsrv.close(); 
			}
			catch (Exception e) { 
				out.println(e.getMessage());
			}
		}
	}

	/*
	 * Charge depuis un fichier des comptes au format :
	 * "username:password_hashmd5"
	 */
	private boolean loadUsers() {
		String line;
		String[] tmp;
		BufferedReader reader = null;
		users = new Hashtable<String, String>();

		try {
			reader = new BufferedReader(new FileReader(auth_file));
			while ((line = reader.readLine()) != null){
				tmp = line.split(":");
				if (tmp.length == 2)
					users.put(tmp[0], tmp[1]);
				else
					out.println("le tuple login/password " + line + " est invalide");
			}
			
			if (users.size() == 0) {
				out.println("erreur: aucun login/password définis");
				return false;
			}
			
			return true;
		}
		catch (Exception e) {
			out.println(e.getMessage());
			return false;
		}
		finally {
			try {
				if (reader != null)
					reader.close(); 
			}
			catch (Exception e) {
				out.println(e.getMessage());
			}
		}
	}

	/*
	 * Vérifie que le login saisi est dans la hashtable
	 * et que le md5 du password donné est associé au login
	 * de la hashtable
	 */
	public boolean authentification() {
		String login, passwd;

		out.print("login : ");
		login = in.next();
		out.print("password : ");
		passwd = in.next();
		out.println();
		
		if (users != null &&
				users.containsKey(login) && 
				users.get(login).equals(md5Encode(passwd)))
			return true;
		return false;
	}
}
