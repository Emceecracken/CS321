import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;


public class GeneBankCreateBTree {
	
	private static int degree;
	private static File gbk;
	private static int sequenceLength;
	private static int debugLevel;
	
	public static void main(String[] args) {
		
		degree = Integer.parseInt(args[0]);
		gbk = new File (args[1]);
		sequenceLength = Integer.parseInt(args[2]);
		debugLevel = Integer.parseInt(args[3]);
		
		if (args.length == 4){
			
			System.out.println(degree + " " + gbk + " " + sequenceLength + " " + debugLevel);
			
		}
		else{
			
			System.out.println("Please enter in this format: <java GeneBankCreateBTree <degree> <gbk file>"
					+ " <sequence length> <debug level>");
		
		}
		
		

	}

}
