
public class TreeObject<T> {
	
	private T key;
	private int frequencyCount;
	
	public TreeObject(T a){	
		key = a;
		frequencyCount = 1;
		
	}
	
	public T getKey(){
	
		return key;
	}
	public void setkey(T k){
		
		key = k;
			
	}
	public int getFrequencyCount(){
		return frequencyCount;
		
	}
	
	public void setFrequencyCount(){
		frequencyCount ++;
	}
	public boolean equals(TreeObject k) {
		
		if (k.getKey().equals(key)){
			return true;
		}
		return false;
		
	}
}
