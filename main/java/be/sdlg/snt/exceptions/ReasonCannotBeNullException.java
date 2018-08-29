package be.sdlg.snt.exceptions;

public class ReasonCannotBeNullException extends Exception {
	public ReasonCannotBeNullException() {
		super("Reason for change cannot be null and must exist");
	}

}
