package app.models;
import java.lang.*;
import java.util.*;
import app.models.metric.*;

/**
 * Model representing an author
 * @author Pete Schmidt
 * @version 0.1 [September 2, 2013]
 */
public class Author extends AbstractModel
{
    /** Serial Version UID */
    protected static final long serialVersionUID = 0L;

    /** Author name */
    protected String name;

    /**
     * Testing the author class
     * @param arg[0] the author's name
     */
    public static void main(String... args)
    {
        if(args.length > 0)
        {
            Author author = new Author().load(args[0]);

System.out.println("Loaded author: " + author.id);
            if(args.length > 1)
            {
System.out.println("Trying to add author name: " + args[1]);
                author.name = args[1];
                author.save();
            }
        }

        System.out.println("Author List:");
        for(AbstractModel m : new Author().getCollection())
        {
            System.out.println(m);

            for(AbstractModel d : ((Author) m).getDocuments())
            {
                System.out.println("   " + d);
            }

            /*Map<String, float[]> metricData = ((Author) m).getMetricData();
            for(String key : metricData.keySet())
            {
                float[] data = metricData.get(key);
                System.out.println(key);
                System.out.println("   average: " + data[0]);
                System.out.println("   std dev: " + data[1]);
            }*/
        }
    }

    /**
     * Create new author object
     */
    public Author(){}

    /**
     * Load author by name
     * @param name    the author's name
     */
    public Author(String name)
    {
        this.load(name.toLowerCase().replaceAll(" +","-"));
        this.name = name;
    }

    /**
     * Get set of metric data for this author
     * @return set of metric data with both average and standard deviation
     */
    // this is probably unnecessary since we are using each metric to compare documents
    /*public Map<String, float[]> getMetricData()
    {
        Map<String, List<String>> metricsByType = getMetricsByType();
        Map<String, float[]> metricData = new HashMap<String, float[]>();

        for(String key : metricsByType.keySet())
        {

            if(metricsByType.get(key).size() < 1)
            {
                continue; // skip if there are no metrics for this type
            }

            float aggregateValue = 0;
            for(String value : metricsByType.get(key))
            {
                aggregateValue += Float.parseFloat(value);
            }
            float mean = aggregateValue / metricsByType.get(key).size();

            float aggregateDeviation = 0;
            for(String value : metricsByType.get(key))
            {
                aggregateDeviation += Math.pow(Float.parseFloat(value) - mean, 2);
            }
            float standardDeviation = (float) Math.sqrt( aggregateDeviation / (metricsByType.get(key).size() - 1) );

            float[] meanAndStdDev = { mean, standardDeviation };

            metricData.put(key, meanAndStdDev);
        }

        return metricData;
    }*/

    // this is probably unnecessary since we are using each metric to compare documents
    /*protected Map<String, List<String>> getMetricsByType()
    {
        Map<String, List<String>> metricsByType = new HashMap<String, List<String>>();
        for(Document d : getDocuments())
        {
            d.calculateMetrics(); // ensure all metrics are calculated

            for(AbstractMetric m : d.getMetrics())
            {

                String metricName = m.getClass().getName();
                if(metricsByType.get(metricName) == null)
                {
                    metricsByType.put(metricName, new ArrayList<String>());
                }
                metricsByType.get(metricName).add(m.getResult());
            }
        }
        return metricsByType;
    }*/

    /**
     * Retrieve collection of documents attributed to this author
     * @return a List of Document objects belonging to this Author
     */
    public List<Document> getDocuments()
    {
        return new Document().getCollectionWhere("author_id",this.id);
    }

    /**
     * Get total number of documents by this author
     * @return number of Document objects belonging to this Author
     */
    public int getDocumentCount()
    {
        return getDocuments().size();
    }

    /**
     * Represent this model as a string
     * @return      string representing this model
     */
    @Override
    public String toString()
    {
        return this.name + " (" + getDocuments().size() + " documents)";
    }

}
