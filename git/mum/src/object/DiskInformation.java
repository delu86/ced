package object;

public class DiskInformation {
	private String fileSystem;
	private int diskSpace; //dimensione del disco in blocchi di un KB
	private int usedSpace;
	private int availableSpace;
	private String mountedOn;
	
	
	public DiskInformation(String line) {
		String [] split=line.split("\\s+");
		fileSystem=split[0];
		if(!split[1].equals(""))diskSpace=Integer.valueOf(split[1]); else diskSpace=0;
		if(!split[2].equals(""))usedSpace=Integer.valueOf(split[2]); else usedSpace=0;
		if(!split[3].equals(""))availableSpace=Integer.valueOf(split[3]); else availableSpace=0;
		mountedOn=split[5];
	}


	public String getFileSystem() {
		return fileSystem;
	}

	public void setFileSystem(String fileSystem) {
		this.fileSystem = fileSystem;
	}

	public int getDiskSpace() {
		return diskSpace;
	}

	public void setDiskSpace(int diskSpace) {
		this.diskSpace = diskSpace;
	}

	public int getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(int usedSpace) {
		this.usedSpace = usedSpace;
	}

	public int getAvailableSpace() {
		return availableSpace;
	}

	public void setAvailableSpace(int availableSpace) {
		this.availableSpace = availableSpace;
	}

	public String getMountedOn() {
		return mountedOn;
	}

	public void setMountedOn(String mountedOn) {
		this.mountedOn = mountedOn;
	}
	public float getUsedPercent(){
		return (float) (diskSpace-availableSpace)/diskSpace*100;
	}
	public static void main(String[] args) {
		DiskInformation disk=new DiskInformation("tmpfs 8224784 72 8224712 1% /dev/shm");
		System.out.println(disk.getFileSystem());
		System.out.println(disk.getDiskSpace());
		System.out.println(disk.getUsedSpace());
		System.out.println(disk.getAvailableSpace());
		System.out.println(disk.getMountedOn());
		
	}
}
