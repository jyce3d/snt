package be.sdlg.snt.exceptions;

public class InsufficientAccessRightsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529495122079761042L;

	public InsufficientAccessRightsException() {
		super ("InsufficientAccessRightsException");
	}

	public InsufficientAccessRightsException(String msg) {
		super(msg);
	}

}
