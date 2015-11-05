package app.models.PeteDb;
import java.lang.*;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;

/**
 * Simple file system database for prototyping
 * <p>
 * Object serialization and file output/input learned from TutorialsPoint<br/>
 * <a href="http://www.tutorialspoint.com/java/java_serialization.htm" target="_blank">http://www.tutorialspoint.com/java/java_serialization.htm</a>
 * <p>
 * Some reflection techniques learned from ObjectProps on Google Code<br/>
 * <a href="https://code.google.com/p/objectprops/" target="_blank">https://code.google.com/p/objectprops/</a>
 *
 * @author Pete Schmidt
 * @version 0.1 [August 30, 2013]
 */
public class Db
{
    /** Path to database files */
    protected static final String PATH_TO_DATA = "database";

    /** File suffix for serialized objects */
    protected static final String SERIALIZED_OBJECT_EXTENSION = ".ser";

    /**
     * Save record to the database
     */
    public static void save(DbRecord record)
    {
        if(record == null)
        {
            return; // if no record entity exists, don't save
        }

        File table = getTableForRecord(record);
        File row = new File(table, record.getDbRecordId() + SERIALIZED_OBJECT_EXTENSION);

        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(row);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(record);
            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch(IOException e){ e.printStackTrace(); }
    }

    /**
     * Load record from the database
     * @param record DbRecord object to be loaded
     */
    public static <T extends DbRecord> T load(DbRecord record)
    {
        File table = getTableForRecord(record);
        File row = new File(table, record.getDbRecordId() + SERIALIZED_OBJECT_EXTENSION);

        try
        {
            FileInputStream fileInputStream = new FileInputStream(row);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            record = (DbRecord) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }
        catch(FileNotFoundException e){ /*e.printStackTrace();*/ }
        catch(IOException e){ e.printStackTrace(); }
        catch(ClassNotFoundException e){ e.printStackTrace(); }

        return (T) record;
    }

    /**
     * Load all records from the database
     * @param record DbRecord of class whose collection should be loaded
     */
    public static <T extends DbRecord> List<T> loadCollection(DbRecord record)
    {
        List<T> collection = new ArrayList<T>();
        File table = getTableForRecord(record);

        for (File entity : table.listFiles())
        {
            if (entity.isFile() && entity.getName().endsWith(SERIALIZED_OBJECT_EXTENSION))
            {
                try
                {
                    T nextRecord = (T) record.getClass().getConstructor().newInstance();
                    nextRecord.setDbRecordId(entity.getName().replace(SERIALIZED_OBJECT_EXTENSION, ""));
                    nextRecord = load(nextRecord);
                    collection.add(nextRecord);
                }
                catch(NoSuchMethodException e){ e.printStackTrace(); }
                catch(InstantiationException e){ e.printStackTrace(); }
                catch(IllegalAccessException e){ e.printStackTrace(); }
                catch(InvocationTargetException e){ e.printStackTrace(); }
            }
        }

        return collection;
    }

    /**
     *
     */
    public static <T extends DbRecord> List<T> loadCollectionWhere(DbRecord record, String attribute, String value)
    {
        List<T> collection = loadCollection(record);
        List<T> filteredCollection = new ArrayList<T>();
        for(T item : collection)
        {
            try
            {
                Field field = item.getClass().getDeclaredField(attribute);
                field.setAccessible(true);
                
                if(field.get(item) != null && field.get(item).equals(value))
                {
                    filteredCollection.add(item);
                }
            }
            catch(NoSuchFieldException e){ e.printStackTrace(); }
            catch(IllegalAccessException e){ e.printStackTrace(); }
        }
        return filteredCollection;
    }

    /**
     * Get directory for this specific model
     * @param record name of model class
     * @return      file object representing directory
     */
    private static File getTableForRecord(DbRecord record)
    {
        String className = record.getClass().getName();
        return getTableForRecord(className);
    }

    /**
     * Get directory for this specific model name
     * @param tableName name of model class
     * @return      file object representing directory
     */
    private static File getTableForRecord(String tableName)
    {
        File file = new File(PATH_TO_DATA + "/" + tableName);

        if(!file.exists())
        {
            file.mkdirs();
        }

        return file;
    }
}
