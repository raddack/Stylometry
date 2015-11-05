package app.models.metric;

import java.util.List;
import java.util.StringTokenizer;
import rita.RiLexicon;

/**
 * Calculates the ratio of adjectives to total words.
 * @author Eric Van Gelder
 * @version 0.1 (Sept 9 2013)
 */
public class AdjectiveFrequency extends AbstractMetric{

    /** Current version for this metric */
    public static final String currentVersion = "0.1";
    
    /** Stored version number for most recent calculation */
    public String version;
    
    /** Stored result to skip recalculation */
    protected String result;
    
    RiLexicon ri = new RiLexicon();
    
    /** Number of adjectives. */
    protected transient int adjectiveCount = 0;
    
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
     * Calculate the number of adjectives and words in a line of text.
     * @param line Line of text from document
     */
    @Override
    public void addLine(String line){
        StringTokenizer st = new StringTokenizer(line);
        while(st.hasMoreTokens()){
            String word = st.nextToken().toLowerCase();
            if(ri.isAdjective(word)){
                adjectiveCount ++;
            }
            wordCount ++;
        }
    }

    /**
     * Return number of adjectives / total words.
     * @return Frequency of adjective usage
     */
    @Override
    public Integer returnData(){
        int adjectiveFrequency = 0;
        if(wordCount != 0){
            adjectiveFrequency = adjectiveCount / wordCount;
        }
        return adjectiveFrequency;
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