import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class BTree implements Serializable{
	
	private int degree;
	private BTreeNode myRoot;
	private String fileName;
	private RandomAccessFile bfile;
	private long fileSize;
	
	/**
	 * Constructor
	 * Creates a new instance of BTree with empty root node
	 * 
	 * @param degree of the tree
	 * @param file name of of binary BTree file
	 */
	public BTree(int degree, String fileName) {
		
		this.degree = degree;
		this.fileName = fileName;
				
		myRoot = new BTreeNode();

		try {
			bfile = new RandomAccessFile(fileName, "rws");
			fileSize = bfile.length();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Insert a new tree object into the BTree
	 * 
	 * @param obj tree object to be inserted
	 * @throws IOException
	 */
	public void insert(TreeObject obj) throws IOException {

/*		BTreeNode r = myRoot;
		
		if(r.numKeys == 2*degree - 1){ //if r is full
			BTreeNode s = new BTreeNode();
			myRoot = s;
			s.leaf = false;
			s.numKeys = 0;
			s.childrenOffset[0] = r.myFileOffset;
			split(s,1,r);
			insertNonFull(s, obj);
		}else{
			insertNonFull(r, obj);
		}*/
	}
	
	/**
	 * Splits a full node in the BTree 
	 * 
	 * @param x parent of full node
	 * @param position
	 * @param y full node
	 */
	private void split(BTreeNode x, int position, BTreeNode y) {
		
	/*	BTreeNode z = new BTreeNode();
		z.leaf = y.leaf;
		
		z.numKeys = degree -1;
		
		for(int j = 1; j < degree - 1; j++){
			z.keys[j] = y.keys[j + degree];
		}
		
		if(!y.leaf){
			for(int j = 1; j < degree; j++){
				z.childrenOffset[j] = y.childrenOffset[j+degree];
			}
		}
		
		y.numKeys = degree - 1;
		
		for(int j = x.numKeys + 1; j > position + 1; j--){
			x.childrenOffset[j+1] = x.childrenOffset[j];
		}
		
		x.childrenOffset[position + 1] = z.myFileOffset;

		for(int j = x.numKeys; j > position; j--){
			x.keys[j + 1] = x.keys[j];
		}
		
		x.keys[1] = y.keys[degree];
		x.numKeys = x.numKeys + 1;
		
		*/
	}

	public void insertNonFull(BTreeNode r, TreeObject newObj){
		
	/*	int i = r.numKeys;
		if(r.leaf){
			while(i >= 1 && newObj.getKey() < r.keys[i].getKey()){
				r.keys[i + 1] = r.keys[i];
				i = i - 1;
			}
			r.keys[i + 1] = newObj;
			r.numKeys = r.numKeys + 1;
			//disk write(r)
		} else {
			while(i >= 1 && newObj.getKey() < r.keys[i].getKey()){
				i = i - 1;
			}
			i = i + 1;
			//child = disk read(r.child[i])
			BTreeNode child = new BTreeNode();								// 	<------ FIX ME
			if(child.numKeys == 2 * degree -1) {
				split(r, i, child);
				if(newObj.getKey() > r.keys[i].getKey()){
					i = i + 1;
				}
			}
			
			insertNonFull(child, newObj);
				
		}*/
	}


	
	public class BTreeNode{
		
		private TreeObject[] keys;
		private int numKeys;
		private long[] childrenOffset;
		private int numChildren;
		private long parentOffset;
		private long myFileOffset;
		private boolean leaf;
		
		
		public BTreeNode(){
			
			keys = new TreeObject[2*degree - 1]; 
			numKeys = 0;
			
			childrenOffset = new long[2*degree];
			numChildren = 0;

			//Generate file offset when new node is created
			
		}
		
		public void writeNode(TreeObject obj) throws IOException{
			
			bfile.seek(myFileOffset);
			
			
			ByteArrayOutputStream byt = new ByteArrayOutputStream();
			ObjectOutputStream out2 = new ObjectOutputStream(byt);
			out2.writeObject(obj);
			bfile.write(byt.toByteArray());
			
		}
		
		public void readNode(){
			
		}
			
	}
	
	
	
}

