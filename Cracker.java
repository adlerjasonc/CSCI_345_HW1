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
import java.io.FileNotFoundException;
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
		
	System.out.println();
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
				System.out.println("this is the current hash: " + currentHash);
				System.out.println("attempting rule # " + menuChoice);
				timeToCrack(Integer.parseInt(menuChoice), currentHash);
				
				
			}//end while
			
		}//end try
		
	}//end if
	
	}//end while loop
	

	myScanner.close();
	System.out.println("Finished");
	
	
	}//end main
	
	private static void timeToCrack(int ruleNum, String hash) throws FileNotFoundException, IOException {
		File dictionary;
		
		//Rule 1: seven char word from /usr/share/dict/words (linux or mac)
		//which gets 1st letter capitalized and 1 digit number appended
		if (ruleNum == 1) {
			
			//display target hash
			System.out.println("Actual Hash: " + hash);
			//load dictionary file
			dictionary = new File("/Users/jasonadler/Desktop/words.txt");
			try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
			
			//initiate dictionaryWord variable to be hashed and compared with hash passed in	
			String dictionaryWord = "";
			
			//loop through dictionary until the end of it
			while ((dictionaryWord = br.readLine()) != null) {
			
			//only try words with exactly 7 letters
			if(dictionaryWord.length() == 7) {
				
				//display which word we are using
				//System.out.println("dictionaryWord: " + dictionaryWord);
				
				//loop for appending values 1-9 and then hashing each to compare.
				for(int i = 0; i<=9; i++){  
					
					//create fully appended and upper cased word
					String upperedWord = dictionaryWord.substring(0,1).toUpperCase() + dictionaryWord.substring(1);
					String appendedUpperedWord = (upperedWord + i);
					//System.out.println("dictionaryWord uppercased and appended: " + appendedUpperedWord);

					//compare the two hashes with the if statement
					try {

						if (toHexString(getSHA(appendedUpperedWord)).equalsIgnoreCase(hash)) {
							System.out.println("Matched Hash: "  + (toHexString(getSHA(appendedUpperedWord))));
							System.out.println("Password has been found!");
							System.out.println("Dictionary word: " + dictionaryWord);
							System.out.println("Password: " + appendedUpperedWord);
										
						}
						
					}
										
			    	// For specifying wrong message digest algorithms  
			        catch (NoSuchAlgorithmException e) {  
			            System.out.println("Exception thrown for incorrect algorithm: " + e);  
			        }  
					
				}
			}
			
			}//end while
		   	       
			}//end try
		}
		//Rule 2: Five digit password with at least one of the following in beginning: *,~,!,#
		else if (ruleNum == 2) {
			System.out.println("rule number: " + ruleNum);
			if(hash.length()<=6) {
				if (hash.matches("\\*\\d+")) {
					try {
						toHexString(getSHA(hash));
					}
			    	 // For specifying wrong message digest algorithms  
			        catch (NoSuchAlgorithmException e) {  
			            System.out.println("Exception thrown for incorrect algorithm: " + e);  
			        }  
				}
				else if (hash.matches("~\\d+")) {
					try {
						toHexString(getSHA(hash));
					}
			    	 // For specifying wrong message digest algorithms  
			        catch (NoSuchAlgorithmException e) {  
			            System.out.println("Exception thrown for incorrect algorithm: " + e);  
			        }  
				}
				else if (hash.matches("!\\d+")) {
					try {
						toHexString(getSHA(hash));
					}
			    	 // For specifying wrong message digest algorithms  
			        catch (NoSuchAlgorithmException e) {  
			            System.out.println("Exception thrown for incorrect algorithm: " + e);  
			        }  
				}
				else if (hash.matches("#\\d+")) {
					try {
						toHexString(getSHA(hash));
					}
			    	 // For specifying wrong message digest algorithms  
			        catch (NoSuchAlgorithmException e) {  
			            System.out.println("Exception thrown for incorrect algorithm: " + e);  
			        }  
				}
			}
		}
		//Rule 3: Five char word from /usr/share/dict/words with the letter
		//'a' in it which gets replaced with '@' and '1' is subbed with 'l'
		else if (ruleNum == 3) {
			System.out.println("rule number: " + ruleNum);
			if(hash.length()<=5) {

				hash.replace("a","@");
				hash.replace("l","1");

				try {
					toHexString(getSHA(hash));
				}
		    	 // For specifying wrong message digest algorithms  
		        catch (NoSuchAlgorithmException e) {  
		            System.out.println("Exception thrown for incorrect algorithm: " + e);  
		        }  
			}
		}
		//Rule 4: Any word that is made up with up to 7 digits length
		else if (ruleNum == 4) {
			//String digits = "";
			boolean found = false;
			//display target hash
			System.out.println("Actual Hash: " + hash);
			
			if(!found) {		
			//for loop to try words of length 1-7
			for ( int i = 1; i <= 123456; i++ ) {
			//digits = i.toString();
			String digits = String.format("%d",i);	
							
				//display which word we are using
				System.out.println("digits: " + digits);
			
				
					//compare the two hashes with the if statement
					try {
						System.out.println("Matched Hash: "  + (toHexString(getSHA(digits))));

						if (toHexString(getSHA(digits)).equalsIgnoreCase(hash)) {
							System.out.println("Matched Hash: "  + (toHexString(getSHA(digits))));
							System.out.println("Password has been found!");
							System.out.println("Digits/password: " + digits);
							found = true;
							
						}//close if
					
					}//close try				
			    	// For specifying wrong message digest algorithms  
			        catch (NoSuchAlgorithmException e) {  
			            System.out.println("Exception thrown for incorrect algorithm: " + e);  
			          
					}//close catch
						
		}//close if 
					
			}//close for
		}//close else if
		// the .matches method was found:
		// https://stackoverflow.com/questions/15111420/how-to-check-if-a-string-contains-only-digits-in-java/15111450


		//Rule 5: Any number of chars single word from /usr/share/dict/words
		else {
			System.out.println("rule number: " + ruleNum);
			

			//display target hash
			System.out.println("Actual Hash: " + hash);
			//load dictionary file
			dictionary = new File("/Users/jasonadler/Desktop/words.txt");
			try (BufferedReader br = new BufferedReader(new FileReader(dictionary))) {
			
			//initiate dictionaryWord variable to be hashed and compared with hash passed in	
			String dictionaryWord = "";
			
			//loop through dictionary until the end of it
			while ((dictionaryWord = br.readLine()) != null) {
			
				if (toHexString(getSHA(dictionaryWord)).equalsIgnoreCase(hash)) {
					System.out.println("Matched Hash: "  + (toHexString(getSHA(dictionaryWord))));
					System.out.println("Password has been found!");
					System.out.println("Dictionary word/Password: " + dictionaryWord);

								
				}//end if
				}//end while
			}//end try
				
				
			
	    	 // For specifying wrong message digest algorithms  
	        catch (NoSuchAlgorithmException e) {  
	            System.out.println("Exception thrown for incorrect algorithm: " + e);  
	        }//end catch 
	    	
		}//end else
	}//end timeToCrack
			
		

		
		
		
	
	
	// Java program to calculate SHA hash value found on GeeksforGeeks.org
	//@ https://www.geeksforgeeks.org/sha-256-hash-in-java/
	  
	
	    public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
	    {  
	    		        // Static getInstance method is called with hashing SHA  
	        MessageDigest md = MessageDigest.getInstance("SHA-256");  
	  
	        // digest() method called  
	        // to calculate message digest of an input  
	        // and return array of byte 
	        return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
	    	

	    	
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