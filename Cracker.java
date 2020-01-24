/*The Cracker class will implement a password cracker that 
does a brute force attack on a set of passwords using some 
rules.


Jason Adler & Matt Anuszkiewics
1/23/20

*/
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;





public class Cracker {
	//create outputfile to use
	static File outputFile = new File("./output.txt");
	
	
	
	public static void main(String[] args) throws IOException{
	String menuChoice = "";
	String input = "";
	String fileContents = "";
	
	//get inputfile to use
	File inputFile = new File(args[0]);
	
	//if inputFile doesn't exist or is a directory rather than file
	if (!inputFile.exists() || inputFile.isDirectory()) {
		System.out.println("Sorry that won't work.");
		System.exit(0);
		
	}
		
		
	Scanner myScanner = new Scanner(System.in);
	
	//Decide which rule to implement
	while (menuChoice.compareToIgnoreCase("x")!=0) {
		
	
	System.out.println("Which rule would you like to implement? or 'x to exit");
	System.out.println("1: 7 char 1st cap 1 dig append");
	System.out.println("2: 5 dig with special character");
	System.out.println("3: 5 char with a or l instead of @ or 1");
	System.out.println("4: up to 7 digits only");
	System.out.println("5: any num chars single word from dictionary");
	
	
	
	//scan the user's input
	menuChoice = myScanner.next();
	//as long as the menu choice is 1-5 enter if statement
	if (menuChoice.equals("1") || menuChoice.equals("2") || menuChoice.equals("3") || menuChoice.equals("4") || menuChoice.equals("5")){
		//begin to read the file that is passed	
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			//as long as there is more to read in the file
			while ((fileContents = br.readLine()) != null) {
				//split the line by the following symbols
				String[] split = fileContents.split("[\\:\\s]+");
				String currentHash = split[1];
				System.out.println(currentHash);
				timeToCrack(Integer.parseInt(menuChoice), currentHash);
				
			}//end while
			
		}//end try
		
	}//end if
	
	}//end while loop
	

	myScanner.close();
	System.out.println("Finished");
	
	
	}//end main
	
	private static void timeToCrack(int ruleNum, String hash) {
		
		//Rule 1: seven char word from /usr/share/dict/words (linux or mac)
		//which gets 1st letter capitalized and 1 digit number appended
		if (ruleNum == 1) {
			
		}
		//Rule 2: Five digit password with at least one of the following in beginning: *,~,!,#
		else if (ruleNum == 2) {
			
		}
		//Rule 3: Five char word from /usr/share/dict/words with the letter
		//'a' in it which gets replaced with '@' and '1' is subbed with 'l'
		else if (ruleNum == 3) {
			
		}
		//Rule 4: Any word that is made up with up to 7 digits length
		else if (ruleNum == 4) {
			
		}
		//Rule 5: Any number of chars single word from /usr/share/dict/words
		else {
			
		}
		
		
		
	}
	
	
}//end class Cracker
