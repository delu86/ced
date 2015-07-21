package object;

public class SystemHourReport extends SystemReport {
	private String from_hour;
	private String to_hour;
	private int numberCPU;

	public SystemHourReport() {
		// TODO Auto-generated constructor stub
	}

	public String getFrom_hour() {
		return from_hour;
	}

	public void setFrom_hour(String from_hour) {
		this.from_hour = from_hour;
	}

	public String getTo_hour() {
		return to_hour;
	}

	public void setTo_hour(String to_hour) {
		this.to_hour = to_hour;
	}

	public int getNumberCPU() {
		return numberCPU;
	}

	public void setNumberCPU(int numberCPU) {
		this.numberCPU = numberCPU;
	}
}