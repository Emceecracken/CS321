import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {

	private static final char EOF = 0;
	//FileInputStream gbf;
	int length; // length of subsequence
	Queue geneSave;
	boolean firstTime;
	DataInputStream d;

	public Parser(FileInputStream file, int subLength) {

		//gbf = file;
		d = new DataInputStream(file);
		length = subLength;
		geneSave = new LinkedList<Character>();
		firstTime = true;
	}

	String nextSubsequence() {

//		if (firstTime) {
//			skipHeader();
//			firstTime = false;
//		}

		char g;
		String ss = null;

		try {
			while ((g = (char)d.readByte()) != EOF) {
				checkGene(g);
				ss = geneSave.toString();
				if (ss.contains("n") || ss.contains("N")) {
					for (int i = 0; i < ss.length(); i++) {
						geneSave.remove();
					}
				}

				System.out.println(ss.length());
				
				if (ss.length() == length) {
					geneSave.remove();
					return ss;
				}				

			}
			d.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	String getSubsequence(String k) {

		String ss = k;
		return ss;
	}

	char checkGene(char check) {

		if (check == 'a' || check == 'c' || check == 't' || check == 'g' || check == 'n' || check == 'N') {

			geneSave.add(check);
		} else if (check == '/') {
			try {
				if ((char)d.readByte() == '/') {

					check = EOF;
					firstTime = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return check;

	}

	void skipHeader() {
		char temp;
		try {
			while((temp = d.readChar()) != EOF){
				System.out.println(temp);
				if(temp == 'O'){
					temp = d.readChar();
					if(temp == 'R'){
						temp = d.readChar();
						if(temp == 'I'){
							temp = d.readChar();
							if(temp == 'G'){
								temp = d.readChar();
								if(temp == 'I'){
									temp = d.readChar();
									if(temp == 'N'){
										break;
									}
								}
							}
						}
					}
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
