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
			r.keys[i] = newObj;
			r.numKeys = r.numKeys + 1;
			r.writeNode();
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
		
		public void writeNode() throws IOException{
			
			bfile.seek(myFileOffset);
			
			bfile.writeLong(myFileOffset);
			
			bfile.writeLong(parentOffset);
			
			//Write array of children offsets
			for(int i = 0; i < 2*degree; i++){
				bfile.writeLong(childrenOffset[i]); 
			}
			
			// Write array of tree objects
			for(int i = 0; i < 2*degree - 1; i++){
				bfile.writeLong(keys[i].getKey());
				bfile.writeInt(keys[i].getFrequencyCount());
			}
						
		}
		
		public BTreeNode readNode(long fileOffset) throws IOException{

			BTreeNode myNode = new BTreeNode();
			
			bfile.seek(fileOffset);
			
			long offset = bfile.readLong();
			myNode.setMyFileOffset(offset);
			
			long parent = bfile.readLong();
			myNode.setParentOffset(parent);
			
			long childOffset;
			
			for(int i = 0; i < 2*degree; i++){
				childOffset = bfile.readLong();
				myNode.setChildOffset(i, childOffset);
			}
			
			for(int i = 0; i < 2*degree - 1; i++){
				long key = bfile.readLong();
				myNode.setTreeObjKey(i, key);
				
				int count = bfile.readInt();
				myNode.setTreeObjFrequency(i, count);
			}
			
			return myNode;
		}

		private void setTreeObjFrequency(int index, int count) {
			
			keys[index].setFrequencyCount(count);
			
		}

		private void setTreeObjKey(int index, long key) {

			keys[index].setkey(key);
			
		}

		private void setChildOffset(int index, long childOffset) {
			
			childrenOffset[index] = childOffset;
			
		}

		private void setParentOffset(long parent) {

			parentOffset = parent;
			
		}

		private void setMyFileOffset(long myOffset) {
		
			myFileOffset = myOffset;
			
		}
			
	}
	
}

