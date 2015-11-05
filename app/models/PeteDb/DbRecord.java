package app.models.PeteDb;
import java.io.*;

/**
 * Interface for any record to be saved in PeteDb
 * @author Pete Schmidt
 * @version 0.1 [August 30, 2013]
 */
public interface DbRecord
{

    /**
     * Get the unique id for this record
     * @return      a unique string to identify this record
     */
    public String getDbRecordId();

    /**
     * Set the unique id for this record
     * @param dbRecordId the unique string to identify this record
     */
    public void setDbRecordId(String dbRecordId);

}
