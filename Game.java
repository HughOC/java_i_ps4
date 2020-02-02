import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;

public class Game
{
	public static void main(String [] args)
	{
		//	Exit program if 2 arguments not supplied:
		if (args.length!=2)
		{
			System.out.println("Need 2 arguments");
			System.exit(1);
		}
		
		//	Exit program if second argument not an integer:
		if (!checkInt(args[1]))
		{
			System.out.println("Second argument must be an integer");
			System.exit(2);
		}
		
		//	Check if the word file is empty:
		File file = new File(args[0]);
		if ( file.length() == 0 )
		{
			System.out.println(" Your word file is empty, please check this. " );
			System.exit(3);
		}
		
		// check for fnfe error:
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(args[0]));
		}
		
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Error on opening file data");
			System.exit(4);
		}
		
		//	While there are words left in the document containing words, add said words to 
		//	arraylist (also check for ioe errors for each word) 
		String inputLine = null;
		boolean done = false;
		ArrayList<String> wordarraylist = new ArrayList<String>();
		while (!done)
		{
			try
			{
					inputLine = reader.readLine();
			}
			
			catch (IOException ioe)
			{
					System.out.println("Error on opening file data");
					System.exit(5);
			}
			
			if (inputLine == null)
			{
				done = true;
				
			}
				else
				{	//	if the line of the document in question survives the ioe check, 
					//	we spilt it into individual words, set them as strings,
					//	and then add them one by one to the arraylist:
					StringTokenizer tokenizer = new StringTokenizer( inputLine );
					while( tokenizer.hasMoreTokens() ) 
						{
						String word = tokenizer.nextToken().toLowerCase();
						//	Before we add a word to wordarraylist, check if that word is 
						// 	alphabetic:
						if (!Game.isAlpha(word))
								{
								System.out.println("We found an non-alphabetic word in your file of words. Please check this.");
								System.exit(6);
								}
						wordarraylist.add(word);
						}
										
	
				}
			
				
		}
	
		//	We randomly select a word from wordarraylist and call it randomWord. 
		//	We put the letters of randomWord into a char array.
		//	We create a char array of the same length as randomWord (but comprised of asterisks)
		//	We name the asterisk array as wordHidden:
		int lengthofarray = wordarraylist.size();
		Random x = new Random();
		int randomInteger = x.nextInt(lengthofarray);
		String randomWordCases = wordarraylist.get(randomInteger);
		String randomWord = randomWordCases.toLowerCase();
		char[] randomWordArray = randomWord.toCharArray();
		char[] wordHidden = new char[randomWordArray.length];
		
		//	Create word hidden:
		for (int k = 0; k < randomWordArray.length; k++)
			{
			wordHidden[k] = '*';
			}
		
		//	Before we implement our game, we will close the file as we no longer need it. 
		
		try
		{
			reader.close();
		}
		catch( IOException ioe )
		{
			System.out.println( "Error closing word file! Please check your word file." );
			System.exit(7);
		}
		
		//	The core code for the game. Take in the number of guesses.
		//	While the person has guesses left, show hiddenWord and take in a guess. 
		//	Tell them if there guess was successful or not.
		int numOfGuesses = Integer.parseInt(args[1]);
		while( numOfGuesses > 0 )
		{
				System.out.println("");
				System.out.println("You have " + numOfGuesses + " guesses left.");
				System.out.print("Secret word:  ");
				for(int l = 0; l < wordHidden.length; l++)
				{
					System.out.print(wordHidden[l]);
				}
				
				
				System.out.println("");
				System.out.println("Guess a letter:");
				BufferedReader reader2 = new BufferedReader(new InputStreamReader( System.in ));
				String guess = "";
				try
				{
					guess = reader2.readLine().toLowerCase();
				}
				catch( IOException ioe )
				{
					System.out.println( ioe );
					System.exit(8);
				}
				
			
				
				//	they put nothing, tell them:
				if (guess.length() == 0)
				{
					System.out.println("You hit enter but didn't put a guess!");
					System.out.println("__________________________");
					continue;
				}
				
				//	They put too many things, tell them:
				if (guess.length() > 1)
					{
					System.out.println("You may only guess one letter at a time!");
					System.out.println("__________________________");
					continue;
					}
				
				//	if they put the right amount of things, but it wasn't alphabetic,
				//	tell them:
				if (Game.isAlpha(guess) == false)
				{
					System.out.println("You must only guess with letters!");
					System.out.println("__________________________");
					continue;
				}
				
				//	Tell them if they were correct or not:
				if (randomWord.contains(guess))
				{
					System.out.println("Good guess! You didn't lose a guess for that.");
				}
				else
				{
					System.out.println("Bad luck! Sorry, you lost a guess for that.");
					numOfGuesses--;
				}
				
				
				
				//	Now that we have a valid guess, we edit the hidden word,
				//	provided the guess was correct:
				for (int q = 0; q < randomWordArray.length; q++)
					
				{
					if (randomWordArray[q] == guess.charAt(0))
					{
						wordHidden[q] = guess.charAt(0);
					}
				}
				
				//	Find out if there are letters left to guess:
				boolean isThereAst = false;
				for (int g = 0; g < wordHidden.length; g++)
				{
					if (wordHidden[g] == '*')
					{
						isThereAst = true;
					}
						
				}
				
				if ( !isThereAst )
				{
					
					System.out.println("Well done! The word was: " +  randomWord + "." );
					System.out.println("__________________________");
					System.exit(9);
				}
				System.out.println("__________________________");
	
		}
	System.out.println(wordHidden);
	System.out.println("Hard luck. you ran out of guesses. The word was: " + randomWordCases );
	System.out.println("__________________________");
	System.exit(10);
	}
	
	

	// create method to check if something is alphabetic and only alphabetic:
	public static boolean isAlpha(String name) 
	{
	    return name.matches("[a-zA-Z]+");
	}
	
	//Create static method to check if something is an integer:
	public static boolean checkInt( String input )
	{
		boolean valid = true;
		int length = input.length();
		for ( int i = 0; i < length; i++ )
		{
			String character = input.substring( i, i+1 );
			if ( character.compareTo( "0" ) < 0 ||
					character.compareTo( "9" ) > 0 )
				valid = false;
		}
		return valid;
	}
	
	
}