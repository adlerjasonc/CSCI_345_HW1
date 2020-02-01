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
import java.math.BigInteger;  
import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException; 




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

			if(hash.length()<= 7) {
				hash.toUpperCase(); //Cap sting

				for(int i = 0; i<=9; i++){  //loop for appending the int and hashing.
					String appendedHash = hash+i;
					getSHA(appendedHash);
				}
			}
		   	       
			
		}
		//Rule 2: Five digit password with at least one of the following in beginning: *,~,!,#
		else if (ruleNum == 2) {
			if(hash.length()<=6) {
				if (hash.matches("\\*\\d+")) {
					getSHA(hash);
				}
				else if (hash.matches("~\\d+")) {
					getSHA(hash);
				}
				else if (hash.matches("!\\d+")) {
					getSHA(hash);
				}
				else if (hash.matches("#\\d+")) {
					getSHA(hash);
				}
			}
		}
		//Rule 3: Five char word from /usr/share/dict/words with the letter
		//'a' in it which gets replaced with '@' and '1' is subbed with 'l'
		else if (ruleNum == 3) {
			if(hash.length()<=5) {

				hash.replace(String"a", String"@");
				hash.replace(String"l", String"1");

				getSHA(hash);
			}
		}
		//Rule 4: Any word that is made up with up to 7 digits length
		else if (ruleNum == 4) {
			if (hash.length() < 8 && (hash.matches("\\d+") ))//checking for digits)
			{
				getSHA(hash);
			}
		}
		// the .matches method was found:
		// https://stackoverflow.com/questions/15111420/how-to-check-if-a-string-contains-only-digits-in-java/15111450


		//Rule 5: Any number of chars single word from /usr/share/dict/words
		else {
			getSHA(hash);
		}
			
		}
		
		
		
	
	
	// Java program to calculate SHA hash value found on GeeksforGeeks.org
	//@ https://www.geeksforgeeks.org/sha-256-hash-in-java/
	  
	
	    public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
	    {  
	    	try {
	        // Static getInstance method is called with hashing SHA  
	        MessageDigest md = MessageDigest.getInstance("SHA-256");  
	  
	        // digest() method called  
	        // to calculate message digest of an input  
	        // and return array of byte 
	        return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
	    	}
	    	 // For specifying wrong message digest algorithms  
	        catch (NoSuchAlgorithmException e) {  
	            System.out.println("Exception thrown for incorrect algorithm: " + e);  
	        }  
	    	return null;
	    	
	    } 
	    
	    public static String toHexString(byte[] hash) 
	    { 
	        // Convert byte array into signum representation  
	        BigInteger number = new BigInteger(1, hash);  
	  
	        // Convert message digest into hex value  
	        StringBuilder hexString = new StringBuilder(number.toString(16));  
	  
	        // Pad with leading zeros 
	        while (hexString.length() < 32)  
	        {  
	            hexString.insert(0, '0');  
	        }  
	  
	        return hexString.toString();  
	    } 
	
	
	
}//end class Cracker
