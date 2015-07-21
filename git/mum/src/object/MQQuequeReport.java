package object;

public class MQQuequeReport {
	
	private String date;
	private int hour;
	private long countOperations;
	private float cpuTime;
	private float elapsed;
	private long getBytes;
	private long putBytes;
	private float latencyMSG;
	private String dayOfWeek;

	public MQQuequeReport() {
		// TODO Auto-generated constructor stub
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}



	public long getCountOperations() {
		return countOperations;
	}

	public void setCountOperations(long countOperations) {
		this.countOperations = countOperations;
	}

	public float getCpuTime() {
		return cpuTime;
	}

	public void setCpuTime(float cpuTime) {
		this.cpuTime = cpuTime;
	}

	public long getGetBytes() {
		return getBytes;
	}

	public void setGetBytes(long getBytes) {
		this.getBytes = getBytes;
	}

	public float getElapsed() {
		return elapsed;
	}

	public void setElapsed(float elapsed) {
		this.elapsed = elapsed;
	}

	public float getLatencyMSG() {
		return latencyMSG;
	}

	public void setLatencyMSG(float latencyMSG) {
		this.latencyMSG = latencyMSG;
	}

	public long getPutBytes() {
		return putBytes;
	}

	public void setPutBytes(long putBytes) {
		this.putBytes = putBytes;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

}
