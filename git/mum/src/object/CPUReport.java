package object;

public class CPUReport extends SystemReport {

	private int cpuID;//identificativo della CPU
	private float durationTime;//durata dell'intervallo di rilevazione

	public CPUReport() {
		
	}

	public int getCpuID() {
		return cpuID;
	}

	public void setCpuID(int cpuID) {
		this.cpuID = cpuID;
	}

	public float getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(float durationTime) {
		this.durationTime = durationTime;
	}

}
