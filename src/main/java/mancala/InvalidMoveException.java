package mancala;

/**
 * A class that extends the exception class to specify a move is invalid
 */
public class InvalidMoveException extends Exception{
    private static final long serialVersionUID = 4053673262215290176L;

    /**
     * Creates new InvalidMoveExcepetion object
     */
    public InvalidMoveException(){
        super("Invalid Move");
    }

    /**
     * Creates new InvalidMoveException object
     * 
     * @param message The message held by the exception
     */
    public InvalidMoveException(final String message){
        super(message);
    }

    /**
     * Creates new InvalidMoveException object
     * 
     * @param err The throwable that triggered the exception
     */
    public InvalidMoveException(final Throwable err) {
        super(err.getMessage());
    }
}