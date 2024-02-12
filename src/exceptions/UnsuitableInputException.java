package exceptions;

public class UnsuitableInputException extends RuntimeException {
	public UnsuitableInputException() {};
	public UnsuitableInputException(String message) {super(message);}
}