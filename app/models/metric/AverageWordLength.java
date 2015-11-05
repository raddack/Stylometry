package app.models.metric;

import java.util.List;
import java.util.StringTokenizer;


/**
 * Average word length metric
 * @author Eric Van Gelder
 * @version 0.1 (Sept 7 2013)
 */
public class AverageWordLength extends AbstractMetric{
    
    /** Current version for this metric */
    public static final String currentVersion = "0.1";
    
    /** Stored version number for most recent calculation */
    public String version;
    
    /** Stored result to skip recalculation */
    protected String result;
    
    /** Number of words in document */
    protected transient int numberOfWords = 0;
    
    /** Sum of length of words in document */
    protected transient int length = 0;
    
    /**
     * Get the stored version number of the most recent calculation of this
     * metric.
     * @return Stored version number
     */
    @Override
    public String versionNumber() {
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
     * Calculate number of words and their length for a given line of text.
     * @param line Line of text from document
     */
    @Override
    public void addLine(String line){
        StringTokenizer st = new StringTokenizer(line);
        while(st.hasMoreTokens()){
            String word = st.nextToken();
            length += word.length();
            numberOfWords ++;
        }
    }

    /**
     * Return average word length for this document.
     * @return 0 or average word length
     */
    @Override
    public Double returnData(){
        if(numberOfWords == 0){
            return Double.valueOf(0.0);
        }
        return Double.valueOf(length / numberOfWords);
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
