import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;


public class GeneBankCreateBTree {
	
	private static int degree;
	private static File gbf;
	private static int sequenceLength;
	private static int debugLevel;
	private static int cacheOption;
	private static int cacheSize;
	
	
	public static void main(String[] args) {
	
		if (args.length < 3){
			System.err.println ("Incorrect number of arguments./n Please enter in format: <degree>"
					+ "<gbk file> <sequence length> <debug level>");
			System.exit(0);
		}
		if (args.length == 4){
			degree = Integer.parseInt(args[0]);
			gbf = new File(args[1]);
			sequenceLength = Integer.parseInt(args[2]);
			debugLevel = Integer.parseInt(args[3]);
			
			System.out.println(degree + " " + gbf + " " + sequenceLength + " " + debugLevel);
			
		}
		if (args.length == 6){
			cacheOption = Integer.parseInt(args[0]);
			degree = Integer.parseInt(args[1]);
			gbf = new File(args[2]);
			sequenceLength = Integer.parseInt(args[3]);
			cacheSize = Integer.parseInt(args[4]);
			debugLevel = Integer.parseInt(args[5]);
			
			System.out.println(degree + " " + gbf + " " + sequenceLength + " " + debugLevel);
		}
		
		String BTreeFile = gbf + ".btree.data." + sequenceLength + "." + degree;
		
		BTree b = new BTree(degree, BTreeFile);
		
		try{
			BufferedReader in = new BufferedReader(new FileReader("geneBankList"));
			Parser parse = new Parser(in, sequenceLength);
			
			String str;
			
			while((str = parse.nextSubsequence()) != null){
				
				long key;
				key = stringToKey(str);
				TreeObject newObj = new TreeObject(key);
				
				
				b.insert(newObj);
				
			//	System.out.println(key + "  " + str);
			}
		}catch (Exception e){
			e.printStackTrace();

		}
		//write text file "dump"  <frequency> <DNA String> in an inorder traversal

	}

	private static long stringToKey(String str) {
		
		Long keyBits = 0L;  //bit level long
		
		for(int i = 0; i < str.length(); i++){
			char c = str.charAt(i);
			switch(c){
			case 'a':
				keyBits = keyBits <<2;
				keyBits |= 0L;
				break;
			case 't':
				keyBits = keyBits <<2;
				keyBits |= 3L;
				break;
			case 'c':
				keyBits = keyBits <<2;
				keyBits |= 1L;
				break;
			case 'g':
				keyBits = keyBits <<2;
				keyBits |= 2L;
				break;
			
			}
			
		}

		return keyBits;
	}

}
