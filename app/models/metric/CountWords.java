package app.models.metric;

import java.util.List;
import java.util.StringTokenizer;

public class CountWords extends AbstractMetric {

    /** Current version number for this metric calculation */
 	public static final String currentVersion = "0.1";

        /** Stored version for the most recent calculation of this metric */
	public String version;

	/** Persisted result to skip recalculation */
 	protected String result;
	
	protected transient int numberOfWords = 0;

	@Override
	public String versionNumber() {
		return this.version;
	}

	@Override
        public String getCurrentVersion() {
		return this.currentVersion;
	}

	@Override
	public String getResult()
	{
		return this.result;
	}

	@Override
	public void addLine(String line) {
		StringTokenizer st = new StringTokenizer(line);
		while(st.hasMoreTokens()){
			st.nextToken();
			numberOfWords++;
		}
		
	}

	@Override
	public Integer returnData() {
		return numberOfWords;
	}

	@Override
	public void save()
	{
		if(this.result == null)
		{
			this.result = returnData() + "";
		}
		this.version = this.currentVersion;
		super.save();
	}

	@Override
	public double getSimilarityPercentage(List list1, List list2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
