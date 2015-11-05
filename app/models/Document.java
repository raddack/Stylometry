package app.models;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import app.models.metric.*;

/**
 * Model representing a piece of literary work
 * @author Pete Schmidt
 * @version 0.1 [August 30, 2013]
 */
public class Document extends AbstractModel
{
    /** Serial Version UID */
    protected static final long serialVersionUID = 0L;

    /** Relative path to files containg literary works */
    protected static final String FILES_PATH = "files/documents";

    /** Relative path to Metric classes */
    protected static final String METRICS_PATH = "app/models/metric";

    /** Package containing Metric classes */
    protected static final String METRICS_PACKAGE = "app.models.metric";

    /** Java class file extension */
    protected static final String CLASS_EXTENSION = ".class";

    /** Document title */
    protected String title;

    /** File where literary text is stored */
    protected String filename;

    /** Document author */
    protected String author_id;

    /** Date of authorship */
    // Is it possible to make this a metric instead of a document attribute?
    protected String date;

    /** test transient object */
    protected transient Author author;

    /**
     * Testing the document class
     */
    public static void main(String... args)
    {
        /*Document testDoc = new Document().load("doing-and-daring");
        testDoc.title = "Doing and Daring";
        testDoc.filename = "doing-and-daring.txt";
        testDoc.save();
        System.out.println("test doc: " + testDoc);*/

        if(args.length > 2)
        {
            Document testDoc = new Document().load(args[0]);
            testDoc.filename = args[0] + ".txt";
            testDoc.title = args[1];
            Author a = new Author().load(args[2]);
            a.save();
            testDoc.setAuthor(a);
            testDoc.save();
            System.out.println("Added document: " + testDoc);
        }

        System.out.println("Documents list: ");
        for(AbstractModel d : new Document().getCollection())
        {
            System.out.println(d);
            ((Document) d).calculateMetrics();
        }
    }

    /**
     * Create new document object
     */
    public Document(){}

    /**
     * Load document by id (probably should be the file where the document is saved)
     * @param id    unique id identifying the record to load
     */
    public Document(String id)
    {
        this.load(id);
    }

    /**
     * Retrieve author object
     */
    public Author getAuthor()
    {
        if(this.author == null)
        {
            this.author = new Author(this.author_id);
        }
        return this.author;
    }

    /**
     * Retrieve author object
     * @param a     the Author object
     */
    public void setAuthor(Author a)
    {
        this.author = a;
        this.author_id = a.id;
    }

    /**
     * Calculate metrics for this document
     */
    public void calculateMetrics()
    {
        List<AbstractMetric> metrics = getMetricsForCalculation();

        if(this.filename == null)
        {
            return; // no filename, nothing to calculate
        }

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(FILES_PATH + "/" + this.filename));
            String line;

            while ((line = reader.readLine()) != null)
            {
                // pass individual lines of text to Metric objects
                for(AbstractMetric metric : metrics)
                {
                    metric.addLine(line);
                }
            }
            reader.close();
        }
        catch(IOException e){ e.printStackTrace(); }
    
        // finalize results for this document in each Metric object
        for(AbstractMetric metric : metrics)
        {
System.out.println("Done calculating metric.");
            metric.endOfDocument();
        }
    }

    /**
     * Get list of Metric objects that need to be calculated
     */
    private List<AbstractMetric> getMetricsForCalculation()
    {
        List<AbstractMetric> metrics = new ArrayList<AbstractMetric>();
        // remove any Metric objects with matching versions
        for(AbstractMetric m : getMetrics())
        {
System.out.println("Data version: " + m.getDataVersion() + " :: Current version: " + m.versionNumber());
            if(m.getDataVersion() == null || !m.getDataVersion().equals(m.versionNumber()))
            {
                metrics.add(m);
            }
        }
        return metrics;
    }

    /**
     * Get list of all Metric objects
     * @return list of all objects found in the Metric directory
     */
    public List<AbstractMetric> getMetrics()
    {
        List<AbstractMetric> collection = new ArrayList<AbstractMetric>();
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        String filename, className;

        for (File file : new File(METRICS_PATH).listFiles())
        {
            if (file.isFile())
            {
                filename = file.getName();
                if (filename.endsWith(CLASS_EXTENSION))
                {
                    className = filename.replace(CLASS_EXTENSION,"");
                    try
                    {
                        Class c = loader.loadClass(METRICS_PACKAGE + "." + className);
                        if(!Modifier.isAbstract(c.getModifiers()))
                        {
                            try
                            {
                                AbstractMetric metric = (AbstractMetric) c.getConstructor().newInstance();
                                metric.load(this.id);
                                collection.add(metric);
                            }
                            catch(NoSuchMethodException e){ e.printStackTrace(); }
                        }
                    }
                    catch(ClassNotFoundException e){ e.printStackTrace(); }
                    catch(InvocationTargetException e){ e.printStackTrace(); }
                    catch(InstantiationException e){ e.printStackTrace(); }
                    catch(IllegalAccessException e){ e.printStackTrace(); }
                }
            }
        }

        return collection;
    }

    /**
     * Represent this model as a string
     * @return      string representing this model
     */
    @Override
    public String toString()
    {
        return this.title;
    }

}
