import java.io.*;
import java.text.DecimalFormat;




public class Input {
	
	public Input(){
		
	}
	
	private static BufferedReader reader;

//all variables needed for the class input
	static String nameOfFile = "A Midsummer Night's Dream.txt";
	WordInfo[] word_info = new WordInfo[12000];
	WordInfo newWord;
	int index;
	float totalNumberOfWords;
	float zipf;


	private BufferedWriter output;


	
	public void inputMain() throws IOException{
		
		try{
	

			
				//inputs the file
			reader = new BufferedReader(new FileReader("Texts/Plays/" + nameOfFile));
			String initialContents = reader.readLine();
			String finalContents = initialContents;
			
			while (finalContents != null){
				
				
			//stores the line that has been read in, and splits it up into individual words
				String[] lineEntered = finalContents.replace("--", " ").split(" ");
				
				
			//	begins a loop to clean up the individual words
				for(int arrayPosition = 0; arrayPosition < lineEntered.length; arrayPosition++){
				
				
					String currentWord = lineEntered[arrayPosition];
					
					cleanWord(currentWord);
					if(lineEntered[arrayPosition].equals("*") || lineEntered[arrayPosition].equals("") || lineEntered[arrayPosition].equals("***")){
											
							continue;
										
						}
							if(currentWord != null){          
								newWord = new WordInfo(currentWord);
								int location = wordExists();
								
								
								if(word_info[location] != null){	//if the location in the array has a word stored in it, then the count for that word increases 
									word_info[location].increaseCount();
									totalNumberOfWords++;
									
								}else{
							//if the location in the array does not have anything stored in it, then  this replaces the nothingness with the most recent word
									word_info[location] = newWord;
									totalNumberOfWords++;
									
								}
							
							
						}
					

					
				}
				finalContents = reader.readLine();
			}
			
			
			sortWords();
			reader.close();
		
	
//catches problems and such

}catch(ArrayIndexOutOfBoundsException e){
	System.out.println("Attempting to process that which is out of bounds");
	e.printStackTrace();
}catch(StringIndexOutOfBoundsException e){
	System.out.println("Attempting to process an out of bounds string");
	e.printStackTrace();
}catch(NullPointerException e){
	System.out.println("Attempting to process a null pointer");
	e.printStackTrace();
}catch(IndexOutOfBoundsException e){
	System.out.println("Attempting to process an invalid index");
	e.printStackTrace();
}
			
			
			
		}
	
	//predetermined method
	
	private static String cleanWord(String word) {
		
		String cleaned_word = word.toLowerCase();
		
		// Remove punctuation from the start of the word.
		while (cleaned_word.length() > 0 && !isLetter(cleaned_word.charAt(0))) {
			cleaned_word = cleaned_word.substring(1);
		}

		// Remove punctuation from the end of the word.
		while (cleaned_word.length() > 0 && !isLetter(cleaned_word.charAt(cleaned_word.length()-1))) {
			cleaned_word = cleaned_word.substring(0,cleaned_word.length()-1);
		}
		return cleaned_word;
	}

	private static boolean isLetter(char c) {
		return c >= 'a' && c <= 'z';
	}
	
	//method which checks if a word already exists, if it does it returns the location of the word so that its count can be updated, otherwise it returns the value which is null, or in other words the next space in the array available to store a new word
	public int wordExists(){
		int contained = 0;
		for(int l = 0; l < word_info.length; l++){
			
			if(word_info[l] == null){
				contained = l;
				break;
			}
				if(word_info[l].equals(newWord)){
					contained = l;
					break;
				}
		}
		return contained;
		
	}
	
	public void output() throws IOException{


		
		float probability;
		
		//writes the details of the file to a new csv file
		try{
		File newFile = new File("W05 Output");
		 output = new BufferedWriter(new FileWriter(newFile + " " + nameOfFile +".csv"));
		 for(int i = 0; i < word_info.length; i++){ 	
			 int rank = i + 1;
			 
			 if(word_info[i] == null){
					continue;
				}
			 float individualCount = word_info[i].getCount();
			 probability = (individualCount / totalNumberOfWords);
			 zipf = (rank*probability);
			 
			 DecimalFormat numberFormat = new DecimalFormat("0.000000");
       	  output.write(word_info[i].getWord() + ", " + word_info[i].getCount() + ", " + rank  + ", " + numberFormat.format(probability)  + ", " + numberFormat.format(zipf) + "\n");
         }
		
		 } catch ( IOException e ) {
	        	System.out.println("Output not available currently");
	           e.printStackTrace();
	        }
	}
	

	//predetermined methods
	private boolean outOfOrder(int j) {
		
		// We want to sort with highest count first, so two adjacent elements are out of order
		// if the count of the first one is less than that of the second one.
		return word_info[j] != null && word_info[j+1] != null && word_info[j].getCount() < word_info[j+1].getCount();
	}

	private void swap(int j) {
		WordInfo temp = word_info[j];
		word_info[j] = word_info[j+1];
		word_info[j+1] = temp;
	}
	
	private void sortWords() throws IOException {
		
		// This does a bubble sort.
		// Each time round the outer loop an element moves up the array to its correct position.
		for (int i = 0; i < word_info.length; i++) {
			for (int j = 0; j < word_info.length - i - 1; j++) {
				if (outOfOrder(j)) swap(j);
			}
		}
		output();
	}


		
}



