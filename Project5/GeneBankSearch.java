import java.io.File;

// returns the frequency of occurrences of the query string 
public class GeneBankSearch {

	private static File btree;
	private static File query;
	private static int debugLevel = 0;
	private static int cacheOption;
	private static int cacheSize;
	private static int frequency;
	
	
	public static void main(String[] args) {
		
		if (args.length < 3){
			
			System.err.println ("Incorrect number of arguments./n Please enter in format: "
					+ "<btree file> <query file> <debug level>");
			System.exit(0);
		}
		if (args.length == 3){
			
			btree = new File(args[0]);
			query = new File(args[1]);
			debugLevel = Integer.parseInt(args[2]);
			
			System.out.println(btree + " " + query + " " + debugLevel);
		}
		if (args.length < 5){
			
			System.err.println ("Incorrect number of arguments./n Please enter in format: <0/1(no/with Cache> "
					+ "<btree file> <query file> <cache size> <debug level>");
			System.exit(0);
		}
		//either utilizes a cache or does not
		if (args.length == 5){
			cacheOption = Integer.parseInt(args[0]);
			btree = new File(args[1]);
			query = new File(args[2]);
			cacheSize = Integer.parseInt(args[3]);
			debugLevel = Integer.parseInt(args[4]);
			
			//cacheOption is 0, does not use a cache
			
			System.out.println(cacheOption + " " + btree + " " + query + " " + cacheSize + " " + debugLevel);
		}
		if (cacheOption == 1){   //uses a cache
			
			Cache<BTree.BTreeNode> myCache = new Cache<BTree.BTreeNode>(cacheSize);
			
		}
	}
	
	//based on the pseudocode in the book 

/*	public int BTreeSearch(BTree.BTreeNode n, Long key){
	
		BTree.BTreeNode b = n;
		int i = 0;
		
		while(i <= b.getNumKeys() && key > b.getNumKeys()[i].getKey()){
		
			i = i +1;
		}
		
		if ( i <= b.getNumKeys() && key == b.getNumKeys()[i].getKey()){
			return b.getNumKeys()[i].getFrequencyCount();	
		}else if (b.isLeaf()){
			return 0;
		}else{
//			b = b.ReadNode(b.getChildrenOffset()[i]);  //find by offset
			return BTreeSearch(b, key);  //search through children
		}
		
	} */
	
}
