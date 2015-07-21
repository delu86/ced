package object;

public class BatchReport {

	private String jobName;
	private String system;
	private String dateInterval;
	private String initialTitme;

	private String abend;
	private String conditionCode;
	private String jobClasString;
	private String jesNumber;
	private double jesInputPriorityString;
	private String racfUserIdString;
	private int count;
	private float execTime;
	private float elapsed;
	private double diskio;
	private float diskioTime;
	private float zipTime;
	private float cpuTime;
	private double tapeIO;
	private double tapeIOtime;
	private String readTime;
	private String endTime;
	private String class8;
	private String reportClassString;
	
	public BatchReport() {
		// TODO Auto-generated constructor stub
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getDateInterval() {
		return dateInterval;
	}

	public void setDateInterval(String dateInterval) {
		this.dateInterval = dateInterval;
	}

	public String getAbend() {
		return abend;
	}

	public void setAbend(String abend) {
		this.abend = abend;
	}

	public String getConditionCode() {
		return conditionCode;
	}

	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}

	public String getJobClasString() {
		return jobClasString;
	}

	public void setJobClasString(String jobClasString) {
		this.jobClasString = jobClasString;
	}

	public double getJesInputPriorityString() {
		return jesInputPriorityString;
	}

	public void setJesInputPriorityString(double jesInputPriorityString) {
		this.jesInputPriorityString = jesInputPriorityString;
	}

	public String getRacfUserIdString() {
		return racfUserIdString;
	}

	public void setRacfUserIdString(String racfUserIdString) {
		this.racfUserIdString = racfUserIdString;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getExecTime() {
		return execTime;
	}

	public void setExecTime(float execTime) {
		this.execTime = execTime;
	}

	public float getElapsed() {
		return elapsed;
	}

	public void setElapsed(float elapsed) {
		this.elapsed = elapsed;
	}

	public double getDiskio() {
		return diskio;
	}

	public void setDiskio(double diskio) {
		this.diskio = diskio;
	}

	public float getDiskioTime() {
		return diskioTime;
	}

	public void setDiskioTime(float diskioTime) {
		this.diskioTime = diskioTime;
	}

	public float getZipTime() {
		return zipTime;
	}

	public void setZipTime(float zipTime) {
		this.zipTime = zipTime;
	}

	public float getCpuTime() {
		return cpuTime;
	}

	public void setCpuTime(float cpuTime) {
		this.cpuTime = cpuTime;
	}

	public double getTapeIO() {
		return tapeIO;
	}

	public void setTapeIO(double tapeIO) {
		this.tapeIO = tapeIO;
	}

	public double getTapeIOtime() {
		return tapeIOtime;
	}

	public void setTapeIOtime(double tapeIOtime) {
		this.tapeIOtime = tapeIOtime;
	}

	public String getJesNumber() {
		return jesNumber;
	}

	public void setJesNumber(String jesNumber) {
		this.jesNumber = jesNumber;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getClass8() {
		return class8;
	}

	public void setClass8(String class8) {
		this.class8 = class8;
	}

	public String getReportClassString() {
		return reportClassString;
	}

	public void setReportClassString(String reportClassString) {
		this.reportClassString = reportClassString;
	}

	public String getInitialTitme() {
		return initialTitme;
	}

	public void setInitialTitme(String initialTitme) {
		this.initialTitme = initialTitme;
	}

}
