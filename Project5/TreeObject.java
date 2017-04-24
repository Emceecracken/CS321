import java.io.Serializable;

public class TreeObject implements Serializable{
	
	private long key;
	private int frequencyCount;
	
	public TreeObject(long key){	
		this.key = key;
		frequencyCount = 1;
		
	}
	
	public long getKey(){
	
		return key;
	}
	
	public void setkey(long k){
		
		key = k;
			
	}
	public int getFrequencyCount(){
		return frequencyCount;
		
	}
	
	public void setFrequencyCount(){
		frequencyCount ++;
	}
	
	public boolean equals(TreeObject k) {
		
		return (k.getKey() == key);
		
	}
	
/*	public int compareTo(TreeObject k){
		
		if(key < k.getKey()){
			return -1;
		}else if(key > k.getKey()){
			return 1;
		}else
			return 0;
	}*/
}
