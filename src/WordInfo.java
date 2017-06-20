
public class WordInfo {

	private int count;
	private String word;
	
	//constuctor for this class
	public WordInfo(String word){
		count = 1;
		this.word = word;
		
		
	}
	
	//method to increase the count of the word
	public int increaseCount(){
		count++;
		return count;
	}

	public int getCount() {
		return count;
	}

	public String getWord() {
		return word;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	//works so that the word object stored in the array can be compared to the word object in the word that is being compared
	@Override
	public boolean equals(Object x){
		boolean yarp = false;
		WordInfo newObject = (WordInfo)x;
		if(word.equals(newObject.word)){
			yarp = true;
		}
		
		return yarp;
		
		
	}
	
}
