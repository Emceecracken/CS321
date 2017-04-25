import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

import java.io.*;
import java.lang.*;

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
				
		try {
			bfile = new RandomAccessFile(fileName, "rws");
			fileSize = bfile.length();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		myRoot = new BTreeNode();
		myRoot.leaf = true;
		
	}

	/**
	 * Insert a new tree object into the BTree
	 * 
	 * @param obj tree object to be inserted
	 * @throws IOException
	 */
	public void insert(TreeObject obj) throws IOException {

		BTreeNode r = myRoot;
		
		if(r.numKeys == 2*degree - 1){ //if r is full
			BTreeNode s = new BTreeNode();
			myRoot = s;
			s.leaf = false;
			s.numKeys = 0;
			s.childrenOffset[0] = r.myFileOffset;
			split(s,0,r); //maybe position 1
			insertNonFull(s, obj);
		}else{
			insertNonFull(r, obj);
		}
	}
	
	/**
	 * Splits a full node in the BTree 
	 * 
	 * @param x parent of full node
	 * @param position
	 * @param y full node
	 */
	private void split(BTreeNode x, int position, BTreeNode y) {
		
		BTreeNode z = new BTreeNode();
		z.leaf = y.leaf;
		
		z.numKeys = degree - 1;
		
		for(int j = 0; j < degree - 1; j++){ //maybe degree - 2 
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
		
		x.keys[position] = y.keys[degree - 1];
		x.numKeys = x.numKeys + 1;
		
		
	}

	public void insertNonFull(BTreeNode r, TreeObject newObj) throws IOException{
		
		int i = r.numKeys;
		if(r.leaf){
			while(i >= 1 && newObj.getKey() < r.keys[i - 1].getKey()){  //increase freq when keys are equal
				r.keys[i] = r.keys[i - 1];
				i = i - 1;
			}
			r.keys[i] = newObj; // r.keys[i] = newObj;
			r.numKeys = r.numKeys + 1;
			r.writeNode(r);
		} else {
			while(i >= 1 && newObj.getKey() < r.keys[i].getKey()){
				i = i - 1;
			}
			i = i + 1;
			BTreeNode child = r.readNode(r.childrenOffset[i]);
			if(child.numKeys == 2 * degree -1) {
				split(r, i, child);
				if(newObj.getKey() > r.keys[i].getKey()){
					i = i + 1;
				}
			}
			
			insertNonFull(child, newObj);
				
		}
	}


	
	public class BTreeNode implements Serializable{
		
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
			try {
				myFileOffset = bfile.length();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			leaf = true;
			
		}
		
		public void writeNode(BTreeNode obj) throws IOException{
			
//			bfile.seek(myFileOffset);
//			
//			
//			ByteArrayOutputStream byt = new ByteArrayOutputStream();
//			ObjectOutputStream out2 = new ObjectOutputStream(byt);
//			out2.writeObject(obj);
//			bfile.write(byt.toByteArray());
			
			bfile.writeLong(obj.myFileOffset);
			
		}
		
		public BTreeNode readNode(long fileOffset){

			byte[] b = new byte[4096];
			BTreeNode myNode = null;
			try {
				bfile.readFully(b, (int)fileOffset, 4096);
				ByteArrayInputStream test = new ByteArrayInputStream(b);
				ObjectInputStream in2 = new ObjectInputStream(test);
				myNode = (BTreeNode) in2.readObject();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return myNode;
		}
			
	}
	
	
	
}

