package app.models.metric;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Calculates distribution of word lengths.
 * @author Eric Van Gelder
 * @version 0.1 (Sept 8 2013)
 */
public class DistributionOfWordLengths extends AbstractMetric{

    /** Current version for this metric */
    public static final String currentVersion = "0.1";
    
    /** Stored version number for most recent calculation */
    public String version;
    
    /** Stored result to skip recalculation */
    protected String result;
    
    /** Number of occurrences for each word length. */
    protected transient HashMap<Integer, Double> lengthFrequency = new HashMap();
    
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
     * Calculate the frequency of each word length in a line of text.
     * @param line Line of text from document
     */
    @Override
    public void addLine(String line){
        StringTokenizer st = new StringTokenizer(line);
        while(st.hasMoreTokens()){
            String word = st.nextToken().toLowerCase();
            int length = word.length();
            if(!lengthFrequency.containsKey(length)){
                lengthFrequency.put(length, 1.0);
            }
            else{
                lengthFrequency.put(length, (lengthFrequency.remove(length) + 1));
            }
            wordCount ++;
        }
    }

    /**
     * Return a hash map containing the relative frequency of each word length.
     * @return Frequency of each word length
     */
    @Override
    public HashMap returnData(){
        if(wordCount != 0){
            Set frequency = lengthFrequency.keySet();
            for(Object key : frequency){
                Integer temp = (Integer) key;
                lengthFrequency.put(temp, (lengthFrequency.remove(temp)/wordCount));
            }
        }
        return lengthFrequency;
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
