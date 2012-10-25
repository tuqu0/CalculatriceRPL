import java.util.Scanner;
import java.util.StringTokenizer;


public class FooPileRPL {

	static boolean tryParseInt(String st)  
	{  
	     try  {  
	         Integer.parseInt(st);  
	         return true;  
	      } catch(NumberFormatException e) {  
	          return false;  
	      }  
	}
	
	public static void main(String[] args) {
	
		String line, token;
		StringTokenizer st;
		PileRPL pile = new PileRPL();
		Scanner sc = new Scanner(System.in);
		
		try {
			while (!(line = sc.nextLine()).equals("")){
				st = new StringTokenizer(line);
		
				while (st.hasMoreTokens()) {
					  token = st.nextToken();
					  
					  if (tryParseInt(token))
						  pile.push(new ObjetEmpilable(Integer.parseInt(token)));
					  else if (token.equals("+"))
						  pile.add();
					  else if (token.equals("-"))
						  pile.sub();
					  else if (token.equals("*"))
						  pile.mult();
					  else if (token.equals("/"))
						  pile.div();					  
					}
			}	
			System.out.println(pile);
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			sc.close();
		}
	}
	
}
