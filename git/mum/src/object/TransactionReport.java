package object;

public class TransactionReport {
	
	 private String system;
         private String cics;
	 private String transaction;
	 private String dateString;
	 private String userID;
         private String suserID;
         private String cmduserID;
         private String ouserID;
	 private int transactionCount;
	 private float cpuSecond;
	 private float elapsed;
	 private float j8;
	 private float k8;
	 private float l8;
	 private float ms;
	 private long mem;
	 private float s8;
	 private float qr;
	 private int db2req;
	 private String abend1;
	 private String abend2;
	 

	public TransactionReport() {
		
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public int getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(int transactionCount) {
		this.transactionCount = transactionCount;
	}
	public float getCpuSecond() {
		return cpuSecond;
	}

	public void setCpuSecond(float cpuSecond) {
		this.cpuSecond = cpuSecond;
	}

	public float getElapsed() {
		return elapsed;
	}
	public void setElapsed(float elapsed) {
		this.elapsed = elapsed;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public float getJ8() {
		return j8;
	}
	public void setJ8(float j8) {
		this.j8 = j8;
	}
	public float getK8() {
		return k8;
	}
	public void setK8(float k8) {
		this.k8 = k8;
	}
	public float getL8() {
		return l8;
	}
	public void setL8(float l8) {
		this.l8 = l8;
	}
	public float getMs() {
		return ms;
	}
	public void setMs(float ms) {
		this.ms = ms;
	}
	public long getMem() {
		return mem;
	}
	public void setMem(long mem) {
		this.mem = mem;
	}
	public float getS8() {
		return s8;
	}
	public void setS8(float s8) {
		this.s8 = s8;
	}
	public float getQr() {
		return qr;
	}
	public void setQr(float qr) {
		this.qr = qr;
	}
	public int getDb2req() {
		return db2req;
	}
	public void setDb2req(int db2req) {
		this.db2req = db2req;
	}
	public String getAbend1() {
		return abend1;
	}
	public void setAbend1(String abend1) {
		this.abend1 = abend1;
	}
	public String getAbend2() {
		return abend2;
	}
	public void setAbend2(String abend2) {
		this.abend2 = abend2;
	}
        
        public String getSuserID() {
            return suserID;
        }

        public String getCmduserID() {
            return cmduserID;
        }

        public String getOuserID() {
            return ouserID;
        }
        
    public void setSuserID(String suserID) {
        this.suserID = suserID;
    }

    public void setCmduserID(String cmduserID) {
        this.cmduserID = cmduserID;
    }

    public void setOuserID(String ouserID) {
        this.ouserID = ouserID;
    }

    public String getCics() {
        return cics;
    }
        public void setCics(String cics) {
        this.cics = cics;
    }

}
