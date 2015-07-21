package object;

public class TapeReport {
	
	private String vtid;
	private String epvdate;
	private int epvHour;
	private String system;
	private String type;
	private float avg_mount_time;
	private int numberOfMount;

	public TapeReport() {
		// TODO Auto-generated constructor stub
	}

	public String getVtid() {
		return vtid;
	}

	public void setVtid(String vtid) {
		this.vtid = vtid;
	}

	public String getEpvdate() {
		return epvdate;
	}

	public void setEpvdate(String epvdate) {
		this.epvdate = epvdate;
	}

	public int getEpvHour() {
		return epvHour;
	}

	public void setEpvHour(int epvHour) {
		this.epvHour = epvHour;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getAvg_mount_time() {
		return avg_mount_time;
	}

	public void setAvg_mount_time(float avg_mount_time) {
		this.avg_mount_time = avg_mount_time;
	}

	public int getNumberOfMount() {
		return numberOfMount;
	}

	public void setNumberOfMount(int numberOfMount) {
		this.numberOfMount = numberOfMount;
	}
}
