import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client extends Thread {
	private static int nbClients = 0;
	private int id;
	private Socket socket;

	// Constructeur premenant en paramètre une socket cliente
	public Client(Socket s) {
		socket = s;
		id = ++nbClients;
		this.start();
	}

	// Surcharge de la méthode run
	public void run() {
		int val;
		String line, token;
		StringTokenizer st;

		try {						
			PrintStream out = new PrintStream(socket.getOutputStream());
			Scanner in = new Scanner(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			PileRPL pile = new PileRPL();

			OptionsManager opt = new OptionsManager(pile, in, out);

			while (!opt.authentification());

			opt.usageRemote();
			while (!(line = in.nextLine()).equals("exit")){
				st = new StringTokenizer(line);

				while (st.hasMoreTokens()) {
					token = st.nextToken();	
					System.out.println("client" + id + ": option " + token);

					if (OptionsManager.tryParseInt(token)) {
						val = Integer.parseInt(token);

						switch (val) {
						case 0:
							out.println("* Intéraction avec la pile *");						
							opt.modStack();
							break;
						case 1:
							out.println("* Mode Console *");
							opt.modConsole();
							break;
						}
						out.println("\n*** MENU PRINCIPAL ***");													
					}
					else if (token.equals("help")) {
						opt.usageRemote();
						break;
					}
					else
						out.println("erreur : option incorrecte");
				}
			}
			in.close();
			out.close();
			socket.close();
		}	
		catch (Exception e) {
			System.out.println("client " + id + " : " +e.getMessage()); 
		}
	}
}
