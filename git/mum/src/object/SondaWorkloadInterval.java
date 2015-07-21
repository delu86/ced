package object;

public class SondaWorkloadInterval {
	
	private String system;
	private String date;
	private String interval;
    private int state; //0 semaforo verde,1 semaforo arancione,2 semaforo rosso
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
