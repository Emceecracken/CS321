import java.io.File;

public class BTree {
	
	private int degree;
	private BTreeNode myroot;
	File BTreeFile;
	
	public BTree(int degree) {
		this.degree = degree;
		

	}

	public void insert(long key) {
		
	}
	
	public class BTreeNode{
		
		long[] keys;
		long[] childrenOffset;
		long parentOffset;
		
		long myFileOffset;
		
		public BTreeNode(){
			
			//Generate file offset when new node is created
			
		}
		
		public void writeNode(){
			
		}
		
		public void readNode(){
			
		}
		
		
	}

}
