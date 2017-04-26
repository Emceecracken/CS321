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
	
	public void setFrequencyCount(int frequency){

		frequencyCount = frequency;
	}
	
	public void increaseFrequencyCount(){

		frequencyCount++;
	}
	
	public boolean equals(TreeObject k) {
		
		return (k.getKey() == key);
		
	}

}
