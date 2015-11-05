package app.models.metric;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Calculates the relative frequency of each word.
 * @author Eric Van Gelder
 * @version 0.1 (Sept 7 2013)
 */
public class WordFrequency extends AbstractMetric{

    /** Current version for this metric */
    public static final String currentVersion = "0.1";
    
    /** Stored version number for most recent calculation */
    public String version;
    
    /** Stored result to skip recalculation */
    protected String result;
    
    /** Number of occurrences for each word. */
    protected transient HashMap<String, Double> wordFrequency = new HashMap();
    
    /** Total number of words. */
    protected transient int wordCount = 0;
    
    /**
     * Get the stored version number of the most recent calculation of this
     * metric.
     * @return Stored version number
     */
    @Override
    public String versionNumber(){
        return version;
    }

    /**
     * Get current version number of this metric.
     * @return Current version number
     */
    @Override
    public String getCurrentVersion(){
        return currentVersion;
    }

    /**
     * Calculate the frequency of each word in a line of text.
     * @param line Line of text from document
     */
    @Override
    public void addLine(String line){
        StringTokenizer st = new StringTokenizer(line);
        while(st.hasMoreTokens()){
            String word = st.nextToken().toLowerCase();
            if(!wordFrequency.containsKey(word)){
                wordFrequency.put(word, 1.0);
            }
            else{
                wordFrequency.put(word, (wordFrequency.remove(word) + 1));
            }
            wordCount ++;
        }
    }

    /**
     * Return a hash map containing the relative frequency of each word.
     * @return Frequency of each letter
     */
    @Override
    public HashMap returnData(){
        if(wordCount != 0){
            Set frequency = wordFrequency.keySet();
            for(Object key : frequency){
                String temp = String.valueOf(key);
                wordFrequency.put(temp, (wordFrequency.remove(temp)/wordCount));
            }
        }
        return wordFrequency;
    }

    /**
     * Get the stored result of the last calculation of this metric.
     * @return Stored average word length
     */
    @Override
    public String getResult(){
        return result;
    }
    
    /**
     * Save most recent result to database.
     */
    @Override
    public void save(){
        if(result == null){
            result = returnData() + "";
        }
        version = currentVersion;
        super.save();
    }

    @Override
    public double getSimilarityPercentage(List list1, List list2){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
