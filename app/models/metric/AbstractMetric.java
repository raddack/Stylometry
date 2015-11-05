package app.models.metric;

import java.util.List;
import app.models.*;

public abstract class AbstractMetric<ResultType> extends AbstractModel {
	
	private ResultType result;
	
	public abstract String versionNumber();
        public abstract String getCurrentVersion();
	public abstract void addLine(String line);
	public abstract ResultType returnData();
	public abstract String getResult();
	public abstract double getSimilarityPercentage(List<Document> list1, List<Document> list2);
        public static void main(String args[]) {
		
	}
	
}
