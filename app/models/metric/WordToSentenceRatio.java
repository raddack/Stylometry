package app.models.metric;

import java.util.List;
import java.util.StringTokenizer;

public class WordToSentenceRatio extends AbstractMetric {

	/** Current version number for this metric calculation */
	public static final String currentVersion = "0.1";

	/** Stored version for the most recent calculation of this metric */
	public String version;

	protected String result;

	protected transient int wordCount = 0;
	protected transient int sentenceCount = 0;

	@Override
	public String versionNumber() {
		return this.version;
	}

	@Override
	public String getCurrentVersion() {
		return this.currentVersion;
	}

	@Override
	public String getResult() {
		return this.result;
	}

	@Override
	public void addLine(String line) {
		String sentence = null;
		StringTokenizer st = new StringTokenizer(line,":;, ");
		while(st.hasMoreTokens()){
			StringTokenizer st1 = new StringTokenizer(st.nextToken());
			while(st1.hasMoreTokens()) {
				sentence += st1.nextToken();
				this.wordCount++;
				if (sentence.contains(".") || sentence.contains("!") || sentence.contains("?")) {
					this.sentenceCount++;
					break;
				}
			}
		}
	}

	@Override
	public Double returnData() {
		Double calculateAverage = 0.0;
		
		if (this.wordCount == 0 || this.sentenceCount == 0) {
			return calculateAverage;
		}
		
		calculateAverage = Double.valueOf((this.wordCount)/(this.sentenceCount));

		return calculateAverage;
	}


	@Override
	public void save() {
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