package object;

public class VolumeTimeInformation {

	private String system; //sistema di riferimento
	private String data;   //data di rilevamento
	private int hour;      //ora di rilevamento
	private String applvtname; //nome del cics
	private float cpuTime; //tempo di CPU consumato
	private int volume; //numero di transazioni eseguite
	
	public VolumeTimeInformation() {
		
	}
    
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public float getCpuTime() {
		return cpuTime;
	}
	public void setCpuTime(float cpuTime) {
		this.cpuTime = cpuTime;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getApplvtname() {
		return applvtname;
	}

	public void setApplvtname(String applvtname) {
		this.applvtname = applvtname;
	}

	public float getEfficienza() {
		return cpuTime/volume;
	}

	

}
