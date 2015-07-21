package object;

public class WorkloadInterval {
	
	private String system;
	private String sysplex;
	private String workloadName;
	private String date_interval_start;
	private float mipsCpu;
	private float mipsZiip;

	public WorkloadInterval() {
		// TODO Auto-generated constructor stub
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSysplex() {
		return sysplex;
	}

	public void setSysplex(String sysplex) {
		this.sysplex = sysplex;
	}

	public String getDate_interval_start() {
		return date_interval_start;
	}

	public void setDate_interval_start(String date_interval_start) {
		this.date_interval_start = date_interval_start;
	}

	public float getMipsCpu() {
		return mipsCpu;
	}

	public void setMipsCpu(float mipsCpu) {
		this.mipsCpu = mipsCpu;
	}

	public float getMipsZiip() {
		return mipsZiip;
	}

	public void setMipsZiip(float mipsZiip) {
		this.mipsZiip = mipsZiip;
	}

	public String getWorkloadName() {
		return workloadName;
	}

	public void setWorkloadName(String workloadName) {
		this.workloadName = workloadName;
	}

}
