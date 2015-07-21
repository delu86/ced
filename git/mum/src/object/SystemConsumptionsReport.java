package object;

public class SystemConsumptionsReport {

	private String date;
	private int hour;
	private String system;
	private float mipsCpu;
	private float mipsZiip;
	private float msu4hra; // media mobile msu 4 ore
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public float getMipsCpu() {
		return mipsCpu;
	}
	public void setMipsCpu(float mipsCpu) {
		this.mipsCpu = mipsCpu;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public float getMipsZiip() {
		return mipsZiip;
	}
	public void setMipsZiip(float mipsZiip) {
		this.mipsZiip = mipsZiip;
	}
	public float getMsu4hra() {
		return msu4hra;
	}
	public void setMsu4hra(float msu4hra) {
		this.msu4hra = msu4hra;
	}
}
