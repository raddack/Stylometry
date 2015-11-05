package app.models.metric;

import java.util.List;
import java.util.StringTokenizer;
import rita.RiLexicon;

/**
 * Calculates ratio of nouns, verbs, and adjectives to total words.
 * @author Eric Van Gelder
 * @version 0.1 (Sept 8 2013)
 */
public class LexicalDensity extends AbstractMetric{

    /** Current version for this metric */
    public static final String currentVersion = "0.1";
    
    /** Stored version number for most recent calculation */
    public String version;
    
    /** Stored result to skip recalculation */
    protected String result;
    
    RiLexicon ri = new RiLexicon();
    
    /** Number of nouns, verbs, and adjectives. */
    protected transient int count = 0;
    
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
     * Calculate the number of nouns, verbs, adjectives, and words in a line of
     * text.
     * @param line Line of text from document
     */
    @Override
    public void addLine(String line){
        StringTokenizer st = new StringTokenizer(line);
        while(st.hasMoreTokens()){
            String word = st.nextToken().toLowerCase();
            if(ri.isNoun(word) || ri.isVerb(word) || ri.isAdjective(word)){
                count ++;
            }
            wordCount ++;
        }
    }

    /**
     * Return number of nouns, verbs, and adjectives / total words.
     * @return Frequency of noun, verb, and adjective usage
     */
    @Override
    public Integer returnData(){
        int frequency = 0;
        if(wordCount != 0){
            frequency = count / wordCount;
        }
        return frequency;
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