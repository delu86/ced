package object;

public class ProcessInformation {
	private String user;
	private int pid;
	private String cpuPerc;
	private String memoryPerc;
	private String vsz;
	private String rss;
	private String tty;
	private String stat;
	private String start;
	private String time;
	private String command;
	
	public ProcessInformation(String line) {
		String [] split=line.split("\\s+");
		user=split[0];
		pid=Integer.parseInt(split[1]);
		cpuPerc=split[2];
		memoryPerc=split[3];
		vsz=split[4];
		rss=split[5];
		tty=split[6];
		setStat(split[7]);
		start=split[8];
		time=split[9];
		command="";
		for(int i=10;i<split.length;i++)
		    command+=split[i]+" ";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getCpuPerc() {
		return cpuPerc;
	}

	public void setCpuPerc(String cpuPerc) {
		this.cpuPerc = cpuPerc;
	}

	public String getMemoryPerc() {
		return memoryPerc;
	}

	public void setMemoryPerc(String memoryPerc) {
		this.memoryPerc = memoryPerc;
	}

	public String getVsz() {
		return vsz;
	}

	public void setVsz(String vsz) {
		this.vsz = vsz;
	}

	public String getRss() {
		return rss;
	}

	public void setRss(String rss) {
		this.rss = rss;
	}

	public String getTty() {
		return tty;
	}

	public void setTty(String tty) {
		this.tty = tty;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public static void main(String[] args) {
		ProcessInformation proc=new ProcessInformation("root 3595 0.0 0.4 314964 68580 ? S 2014 0:47 perl EPVzParser.PL /home/epv/EPVROOT/USERPROFILE/CED01 SMF AGENT SCAN_DIRECTORY 0");
		System.out.println(proc.getUser());
		System.out.println(proc.getPid());
		System.out.println(proc.getCpuPerc());
		System.out.println(proc.getMemoryPerc());
		System.out.println(proc.getVsz());
		System.out.println(proc.getRss());
		System.out.println(proc.getTty());
		System.out.println(proc.getStat());
		System.out.println(proc.getStart());
		System.out.println(proc.getTime());
		System.out.println(proc.getCommand());
		
	}

}
