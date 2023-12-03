package mancala;

/**
 * A class that extends the exception class to specify a player does not exist
 */
public class NoSuchPlayerException extends Exception{
    private static final long serialVersionUID = 7791337417655979794L;

    /**
     * Creates a new NoSuchPlayerException object
     */
    public NoSuchPlayerException() {
        super("Player Does Not Exist");
    }

    /**
     * Creates a new NoSuchPlayerException object
     * 
     * @param message The message held by the exception
     */
    public NoSuchPlayerException(final String message) {
        super(message);
    }

    /**
     * Creates new NoSuchPlayerException object
     * 
     * @param err The throwable that triggered the exception
     */
    public NoSuchPlayerException(final Throwable err) {
        super(err.getMessage());
    }
}