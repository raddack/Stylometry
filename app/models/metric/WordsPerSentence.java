package app.models.metric;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class WordsPerSentence extends AbstractMetric {

	/** Current version number for this metric calculation */
	public static final String currentVersion = "0.1";

	/** Stored version for the most recent calculation of this metric */
	public String version;

	/** Persisted result to skip recalculation */
	protected String result;

	protected transient int count = 0;
        
	protected transient ArrayList<Integer> average = new ArrayList<Integer>();

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
		String sentence = null;
		StringTokenizer st = new StringTokenizer(line,".!?:");
		while(st.hasMoreTokens()){
			StringTokenizer st1 = new StringTokenizer(st.nextToken());
			while(st1.hasMoreTokens()){
				sentence = st1.nextToken();
				count++;
			}
		
			if(sentence != null && !sentence.contains("\n")){	
				average.add((Integer) java.lang.Integer.valueOf(count));
				count = 0;
			}
		}
	}

	@Override
	public Double returnData() {
		int calculateAverage = 0;
		Integer array[] = new Integer[average.size()];
		array = average.toArray(array);
		for(int i = 0;i < array.length;i++){
			calculateAverage += array[i];
		}
		
		Double returnme = 0.0;
		if(array.length == 0)
			return returnme;
		else
			return Double.valueOf(calculateAverage / array.length);
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
