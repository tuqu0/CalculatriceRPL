import java.util.Scanner;
import java.util.StringTokenizer;

public class FooPileRPL {

	public static void main(String[] args) {
		int val;
		String line, token;
		StringTokenizer st;
		Scanner in = new Scanner(System.in);
		PileRPL pile = new PileRPL();

		try {
			OptionsManager opt = new OptionsManager(pile, in, System.out);
			opt.usageLocal();

			while (!(line = in.nextLine()).equals("exit")){
				st = new StringTokenizer(line);
				
				while (st.hasMoreTokens()) {
					token = st.nextToken();	

					if (pile == null) {
						pile = new PileRPL();
						opt.setPileRPL(pile);
					}			

					if (OptionsManager.tryParseInt(token)) {
						val = Integer.parseInt(token);

						switch (val) {
						case 0:
							System.out.println("* Intéraction avec la pile *");						
							opt.modStack();
							break;
						case 1:
							opt.setLogSession(!opt.getLogSession());		
							if (opt.getLogSession()) {
								System.out.println("* Log de session activé *");
								System.out.print("fichier : ");
								opt.setLogFile(in.next());
								pile = null;
							}
							else
								System.out.println("* Log de session désactivé *");
							break;
						case 2:
							System.out.println("* Rejeu de session *");
							System.out.print("fichier : ");
							pile = new PileRPL();
							opt.setPileRPL(pile);
							opt.modReplay(in.next());
							pile = null;
							break;
						case 3:
							System.out.println("* Mode Réseau *");
							System.out.print("port : ");
							token = in.next();
							if (OptionsManager.tryParseInt(token))
								opt.modNetwork(Integer.parseInt(token));
							break;
						case 4:
							System.out.println("* Mode Console *");
							opt.modConsole();
							break;
						case 5:
							System.out.println("* Fermeture *");							
							System.exit(0);
						}
						System.out.println("\n*** MENU PRINCIPAL ***");													
					}
					else if (token.equals("help")) {
						opt.usageLocal();
						break;
					}
					else
						System.out.println("erreur : option incorrecte");
				}
			}
		}	
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			in.close();
		}
	}

}