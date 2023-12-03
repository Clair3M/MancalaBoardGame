package mancala;

/**
 * A class that extends the exception class to specify a pit does not exist
 */
public class PitNotFoundException extends Exception{
    private static final long serialVersionUID = 7971644609546703985L;

    /**
     * Creates a new PitNotFoundException object
     */
    public PitNotFoundException(){
        super("Pit does not exist");
    }

    /**
     * Creates a new PitNotFoundException
     * 
     * @param message The message held by the exception
     */
    public PitNotFoundException(final String message){
        super(message);
    }

    /**
     * Creates new PitNotFoundException object
     * 
     * @param err The throwable that triggered the exception
     */
    public PitNotFoundException(final Throwable err) {
        super(err.getMessage());
    }
}
