package object;

public class SystemReport {

	private String system; //sistema di riferimento 
	private String date;  //data di rilevamento
    private String cpuClass;
    private int mips; //Milion Instruction per Second
	private float cpi; //Clock per Instruction
	private float averageUtil; // utilizzo medio della CPU
	private float problemStatePercent; //percentuale di istruzioni eseguite in problem state
	private float l1mp; //Level 1 miss per 100 instruction.
	private float l2p; //Percentuali di update caricati dalla cache di secondo livello
	private float l3p; //Percentuali di update caricati dalla cache di terzo livello
	private float l4lp;//Percentuali di update caricati dalla cache di 4 livello nello stesso book
	private float l4rp;//Percentuali di update caricati dalla cache di 4 livello di un altro book
	private float memp; //Percentuale di caricamenti da memoria
	private final static double RNI_FACTOR=2.3;
	private final static double RNI_FACTOR_L3P=0.4;
	private final static double RNI_FACTOR_L4P=1.2;
	private final static double RNI_FACTOR_L4RP=2.7;
	private final static double RNI_FACTOR_MEMP=8.2;
	public static final int LOW=0;
	public static final int AVERAGE=1;
	public static final int HIGH=2;

	
	   public double getRNI(){
		   return RNI_FACTOR*(RNI_FACTOR_L3P*l3p+RNI_FACTOR_L4P*l4lp+RNI_FACTOR_L4RP*l4rp+RNI_FACTOR_MEMP*memp)/100 ;
	   }
	   public int getWorkloadCategory(){
		   double rni=getRNI();
		   if(l1mp<3){
			   if(rni>=0.75) return AVERAGE;
			   else return LOW;
		   }
		   if(l1mp>=3&&l1mp<=6){
			   if(rni>1) return HIGH;
			   if(rni<=1&&rni>=0.6) return AVERAGE;
			   return LOW;
		   }   
		   else{
			   if(rni>=0.75)
				   return HIGH;
			   else return AVERAGE;
		   }
		   
	   }
	   /*
	    * Getters and setters
	    */
	   
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

	public String getCpuClass() {
		return cpuClass;
	}

	public void setCpuClass(String cpuClass) {
		this.cpuClass = cpuClass;
	}

	public int getMips() {
		return mips;
	}

	public void setMips(int mips) {
		this.mips = mips;
	}

	public float getCpi() {
		return cpi;
	}

	public void setCpi(float cpi) {
		this.cpi = cpi;
	}

	public float getAverageUtil() {
		return averageUtil;
	}

	public void setAverageUtil(float averageUtil) {
		this.averageUtil = averageUtil;
	}

	public float getProblemStatePercent() {
		return problemStatePercent;
	}

	public void setProblemStatePercent(float problemStatePercent) {
		this.problemStatePercent = problemStatePercent;
	}

	public float getL1mp() {
		return l1mp;
	}

	public void setL1mp(float l1mp) {
		this.l1mp = l1mp;
	}

	public float getL2p() {
		return l2p;
	}

	public void setL2p(float l2p) {
		this.l2p = l2p;
	}

	public float getL3p() {
		return l3p;
	}

	public void setL3p(float l3p) {
		this.l3p = l3p;
	}

	public float getL4lp() {
		return l4lp;
	}

	public void setL4lp(float l4lp) {
		this.l4lp = l4lp;
	}

	public float getL4rp() {
		return l4rp;
	}

	public void setL4rp(float l4rp) {
		this.l4rp = l4rp;
	}

	public float getMemp() {
		return memp;
	}

	public void setMemp(float memp) {
		this.memp = memp;
	}

	public SystemReport() {
		// TODO Auto-generated constructor stub
	}

}
