package app.models.metric;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Calculates the number of unique words / total number of words.
 * @author Eric Van Gelder
 * @version 0.1 (Sept 7 2013)
 */
public class VocabularyRichness extends AbstractMetric{

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
     * Return number of unique words / total words.
     * @return Vocabulary richness
     */
    @Override
    public Double returnData(){
        Double richness = java.lang.Double.valueOf(0.0);
        if(wordCount != 0){
            Set frequency = wordFrequency.keySet();
            richness = Double.valueOf(frequency.size() / wordCount);
        }
        return richness;
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
