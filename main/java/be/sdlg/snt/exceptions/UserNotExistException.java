package be.sdlg.snt.exceptions;

public class UserNotExistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4405814820404040358L;

	public UserNotExistException() {
		super ("User does not exist in the system");
	}

}
