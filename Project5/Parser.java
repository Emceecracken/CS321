import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {

	private static boolean EOF = false;
	int length; // length of subsequence
	myQueue geneSave;
	boolean firstTime;
	BufferedReader b;

	public Parser(BufferedReader in, int subLength) {

		b = in;

		length = subLength;
		geneSave = new myQueue();
		firstTime = true;
	}

	String nextSubsequence() {

		if (firstTime) {
			skipHeader();
			firstTime = false;
		}

		char g;
		String ss = "";

		try {
			while (!EOF) {
				g = (char) b.read();
				checkGene(g);
				ss = geneSave.getSubsequence();


				if (ss.contains("n") || ss.contains("N")) {
					for (int i = 0; i < ss.length(); i++) {
						geneSave.poll();
					}
				}
				if (ss.length() == length) {
					geneSave.remove();
					return ss;
				}

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	void checkGene(char check) {

		if (Character.isUpperCase(check)){
            Character.toLowerCase(check);

        }
		
		if (check == 'a' || check == 'c' || check == 't' || check == 'g' || check == 'n' || check == 'N') {

			geneSave.add(check);
		} else if (check == '/') {
			try {
				if (b.read() == '/') {

					EOF = true;
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	void skipHeader() {
		
		char c;
		try {
			while(!EOF){
				c = (char) b.read();
				if(c == 'O'){
					c = (char) b.read();
					if(c == 'R'){
						c = (char) b.read();
						if(c == 'I'){
							c = (char) b.read();
							if(c == 'G'){
								c = (char) b.read();
								if(c == 'I'){
									c = (char) b.read();
									if(c == 'N'){
										c = (char) b.read();
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
