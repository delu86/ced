package object;

public class StepReport extends BatchReport {

	private String stepNameString;
	private String stepNumber;
	private String programName;
	public StepReport() {
		
	}
	public String getStepNameString() {
		return stepNameString;
	}
	public void setStepNameString(String stepNameString) {
		this.stepNameString = stepNameString;
	}
	public String getStepNumber() {
		return stepNumber;
	}
	public void setStepNumber(String stepNumber) {
		this.stepNumber = stepNumber;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}

}
