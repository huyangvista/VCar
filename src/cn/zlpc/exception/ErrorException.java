package cn.zlpc.exception;

/**
 * 抛错
 * @author Hocean
 *
 */
public class ErrorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
