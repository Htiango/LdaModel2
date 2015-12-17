package preprocessing;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Preprocessing {
    
    /**
     * the number of all unique words and user
     */
    public static int uniqueWordsNum, uniqueUserNum;
    
    public static Map<Integer, Map<String,String>> segDocMapMap = 
			new HashMap<Integer, Map<String,String>>();
    
    public static Map<Integer, Map<String,String>> docMapMap = 
			new HashMap<Integer, Map<String,String>>();
    
    /**
     * the child mode of the segged map
     */
    private static String[] CHILDREN2 = 
    	{"问题内容","回复人1分析", "回复人2分析","回复人3分析","回复人4分析", "回复人5分析"};
    
    /**
     * the child mode which represent the user
     */
    private static String[] CHILDREN3 = 
    	{"回复人1姓名","回复人2姓名","回复人3姓名","回复人4姓名","回复人5姓名"};
    
    /**
     * main method
     */
    public static void preprocess(){
    	    	
    	/**
         * the class that translate the xml file
         */
        XMLReader xmlReader;
        
        /**
         * the class that seg the words and rid off the stopwords
         */
        SegWords segWords;
        
        String filepath = "testing data.xml";
    	
    	
    	/**
    	 * load xml file
    	 */
    	xmlReader = new XMLReader();
		xmlReader.readXml(filepath);			
		docMapMap = xmlReader.docMapMap;
		
		/**
		 * seg the words
		 */
		segWords = new SegWords();
		segWords.segWords(xmlReader.docMapMap, xmlReader.docNum);
		segDocMapMap = segWords.segDocMapMap;
		
		
		/**
		 * get the number of the words' info after segged
		 */
		TreeSet<String> wordsSet = new TreeSet<String>();
		uniqueWordsNum = 0;		
		String[] temp;		
		for(Map.Entry<Integer, Map<String,String>> entry : segWords.segDocMapMap.entrySet()){
			for (int i = 0; i < CHILDREN2.length; i++){
				temp = entry.getValue().get(CHILDREN2[i]).split("\\s");
				for(int k = 0; k < temp.length; k ++){
					wordsSet.add(temp[k]);                                          
				}
			}
		}		
		uniqueWordsNum =  wordsSet.size();
		
		/**
		 * get the number of the unique answerer
		 */
		TreeSet<String> userSet = new TreeSet<String>();
		String temp2;	
		uniqueUserNum = 0;
		for(Map.Entry<Integer, Map<String,String>> entry : xmlReader.docMapMap.entrySet()){
			for (int i = 0; i < CHILDREN3.length; i++){
				temp2 = entry.getValue().get(CHILDREN3[i]);
				userSet.add(temp2);                	
			}
		}
		uniqueUserNum =  userSet.size();		
    }
	
}
