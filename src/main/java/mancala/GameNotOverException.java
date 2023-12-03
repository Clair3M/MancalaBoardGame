package mancala;

/**
 * A class that extends the exception class to specify the game is not over
 */
public class GameNotOverException extends Exception{
    private static final long serialVersionUID = -1894883535467494497L;

    /**
     * Creates new GameNotOverException object
     */
    public GameNotOverException(){
        super("The Game is Not Over");
    }

    /**
     * Creates new GameNotOverException object
     * 
     * @param message The message held by the exception
     */
    public GameNotOverException(final String message){
        super(message);
    }

    /**
     * Creates new GameNotOverException object
     * 
     * @param err The throwable that triggered the exception
     */
    public GameNotOverException(final Throwable err) {
        super(err.getMessage());
    }
}