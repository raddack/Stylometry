package app.models.metric;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Calculates the relative frequency of each letter pair.
 * @author Eric Van Gelder
 * @version 0.1 (Sept 7 2013)
 */
public class LetterPairFrequency extends AbstractMetric{

    /** Current version for this metric */
    public static final String currentVersion = "0.1";
    
    /** Stored version number for most recent calculation */
    public String version;
    
    /** Stored result to skip recalculation */
    protected String result;
    
    /** Number of occurrences for each letter pair. */
    protected transient HashMap<String, Double> pairFrequency = new HashMap();
    
    /** Total number of letter pairs. */
    protected transient int pairCount = 0;
    
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
     * Calculate the frequency of each letter pair in a line of text.
     * @param line Line of text from document
     */
    @Override
    public void addLine(String line){
        StringTokenizer st = new StringTokenizer(line);
        while(st.hasMoreTokens()){
            String word = st.nextToken().toLowerCase();
            int length = word.length();
            for(int i = 0; i < length - 1; i ++){
                for(int j = i + 1; j < length; j++){
                    String first = String.valueOf(word.charAt(i));
                    String second = String.valueOf(word.charAt(j));
                    if(first.matches("[a-zA-Z]") && second.matches("[a-zA-Z]")){
                        String temp = first + second;
                        if(!pairFrequency.containsKey(temp)){
                            pairFrequency.put(temp, 1.0);
                        }
                        else{
                            pairFrequency.put(temp, (pairFrequency.remove(temp)+1));
                        }
                        pairCount ++;
                    }
                }
            }
        }
    }

    /**
     * Return a hash map containing the relative frequency of each letter pair.
     * @return Frequency of each letter
     */
    @Override
    public HashMap returnData(){
        if(pairCount != 0){
            Set frequency = pairFrequency.keySet();
            for(Object key : frequency){
                String temp = String.valueOf(key);
                pairFrequency.put(temp, (pairFrequency.remove(temp)/pairCount));
            }
        }
        return pairFrequency;
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
