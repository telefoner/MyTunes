package be;

/**
 * Our own error type.
 * When something goes wrong (the database can't be reached, a title is missing, ...)
 * the DAL and BLL throw a MyTunesException with a message,
 * and the GUI shows that message to the user.
 */
public class MyTunesException extends Exception {

    public MyTunesException(String message) {
        super(message);
    }
}
