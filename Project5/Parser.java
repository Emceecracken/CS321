import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {

	private static boolean EOF = false;		// Condition for end of file
	int length; 							// Length of the gene subsequence
	myQueue geneSave;						// Queue used for saving the gene sequences
	boolean firstTime;						// Condition used to skip everything unimportant
	BufferedReader b;

	public Parser(BufferedReader in, int subLength) {

		b = in;								// Initializes all the class variables
		length = subLength;
		geneSave = new myQueue();
		firstTime = true;
	}

	String nextSubsequence() {

		if (firstTime) {					// Skips the unimportant stuff until "ORIGIN" is found
			skipHeader();	
			firstTime = false;
		}

		char gene;							// Variables used to create the subsequence
		int geneInt;
		String ss = "";

		try {								// Try-catch for bufferedreader.read()
			while ((geneInt =  b.read()) != -1) {				// Keeps reading until read() returns -1 (END OF FILE)
				gene = (char) geneInt;							// Casts the read into a character
				checkGene(gene);								// Checks if the gene is valid
				ss = geneSave.getSubsequence();					// Copies the queue into a string

				if (ss.contains("n") || ss.contains("N")) {		// Gets rid of the queue and string if it contains 'n'
					for (int i = 0; i < ss.length(); i++) {
						geneSave.poll();
						ss = "";
					}
				}
				
				if (ss.length() == length) {					// Returns the subsequence (ss) if it has the correct length
					geneSave.poll();							// Removes the head of the queue so that 
					return ss;
				}

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	void checkGene(char input) {

		char check = Character.toLowerCase(input);

		if (check == 'a' || check == 'c' || check == 't' || check == 'g' || check == 'n') {

			geneSave.add(check);
			
		} else if (check == '/') {
			try {
				if (b.read() == '/') {
					firstTime = true;
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	void skipHeader() {

		try {
			while(b.read() != -1){
				if(b.read() == 'O'){
					
					if(b.read() == 'R'){
						
						if(b.read() == 'I'){
							
							if(b.read() == 'G'){
								
								if(b.read() == 'I'){
									
									if(b.read() == 'N'){
										
										break;
									}
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	public class myQueue extends LinkedList<Character> {
		
		public String getSubsequence(){
			
			String str = "";
			
			Iterator<Character> itr; 
			itr = geneSave.iterator();
			
			int count = 0;
			
			while(count < length){
				
				if(itr.hasNext()){
					str += itr.next();
				}else{
					break;
				}
			}
			
			return str;
			
		}
	}

}
