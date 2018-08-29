package be.sdlg.snt.exceptions;

public class UserCannotBeNullException extends Exception {
	public UserCannotBeNullException() {
		super("User cannot be null and must exist");
	}

}
