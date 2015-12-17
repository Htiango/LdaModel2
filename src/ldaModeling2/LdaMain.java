package ldaModeling2;

import java.util.Map;

import preprocessing.Preprocessing;

public class LdaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int topicNumber = 5;    		//测试时进行修改 
		int expertiseNumber = 5;		//测试时进行修改 
		int LIMIT = 10; 				//测试时进行修改 
		
		double alpha = 50 ; 
		double beta = 0.5;
		double gamma = 50;
		/**
	     * phi multinomial mixture of topic words (K x E x V) <br>
	     * it's the possibility of each topic, expertise and words
	     */
		double[][][] phi;
		
		/**
	     * topic, expertise的一个2-D数组 (K * E) <br>
	     * 每个都是一个Map, Map中的每个元素都是top possible的词语和对应的概率
	     */
	    Map<String, Double>[][] topicExpertiseMap;				
		Corpus corpus;
		LdaGibbsSampler ldaGibbsSampler;

		// Main 
		System.out.println("preprocessing the data......");
		Preprocessing.preprocess();		
		System.out.println("preprocessing success!");
		System.out.println("\n");
		System.out.println("Forming corpus......");
		corpus = Corpus.loading(Preprocessing.segDocMapMap, Preprocessing.docMapMap, 
				Preprocessing.uniqueWordsNum, Preprocessing.uniqueUserNum);
		System.out.println("Forming corpus success!");
		System.out.println("");
		System.out.println("Initialize the model......");
		ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getDoc2UserMap(),
				corpus.getVocabularySize(), corpus.getUserSize());
		System.out.println("Initialize success!");
		System.out.println("");
		System.out.println("Gibbs sampling the model......");
		ldaGibbsSampler.gibbs(topicNumber, expertiseNumber, alpha, beta, gamma);
		System.out.println("Gibbs sampling the model success!");
		System.out.println("");
		System.out.println("Printing the result......");
		phi = ldaGibbsSampler.getPhi();
		topicExpertiseMap = LdaUtil.translate(phi, corpus.getVocabulary(), LIMIT);
		LdaUtil.explain(topicExpertiseMap);
	}	
}
