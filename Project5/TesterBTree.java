import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TesterBTree {

	public static void main(String[] args) throws FileNotFoundException {
		
		try{

			BufferedReader in = new BufferedReader(new FileReader("genes.txt"));
			Parser parse = new Parser(in, 5);
			
			String str;
			
			while((str = parse.nextSubsequence()) != null){
				
				System.out.println(str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}	

	}
}
