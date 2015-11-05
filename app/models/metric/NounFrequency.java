package app.models.metric;

import java.util.List;
import java.util.StringTokenizer;
import rita.RiLexicon;


/**
 * Calculates ratio of nouns to words.
 * @author Eric Van Gelder
 * @version 0.1 (Sept 8 2013)
 */
public class NounFrequency extends AbstractMetric{

    /** Current version for this metric */
    public static final String currentVersion = "0.1";
    
    /** Stored version number for most recent calculation */
    public String version;
    
    /** Stored result to skip recalculation */
    protected String result;
    
    RiLexicon ri = new RiLexicon();
    
    /** Number of nouns. */
    protected transient int nounCount = 0;
    
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
     * Calculate the number of nouns and words in a line of text.
     * @param line Line of text from document
     */
    @Override
    public void addLine(String line){
        StringTokenizer st = new StringTokenizer(line);
        while(st.hasMoreTokens()){
            String word = st.nextToken().toLowerCase();
            if(ri.isNoun(word)){
                nounCount ++;
            }
            wordCount ++;
        }
    }

    /**
     * Return number of nouns / total words.
     * @return Frequency of noun usage
     */
    @Override
    public Integer returnData(){
        int nounFrequency = 0;
        if(wordCount != 0){
            nounFrequency = nounCount / wordCount;
        }
        return nounFrequency;
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