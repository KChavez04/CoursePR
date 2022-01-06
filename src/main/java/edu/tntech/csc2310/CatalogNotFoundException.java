//Written by Kasandra Chavez

package edu.tntech.csc2310;


public class CatalogNotFoundException extends Exception{
    private String message;

    /**
     * Promotes to a super class.
     */
    public CatalogNotFoundException(){
        super();
    }

    /**
     * Promotes to a super class.
     *
     * @param message - Holds a passed in message.
     */
    public CatalogNotFoundException(String message){
        super(message);
        this.message = "Catalog Error: " + message;
    }

    /**
     *
     * @return message - This returns a message.
     */
    @Override
    public String getMessage(){
        return this.message;
    }

    /**
     *
     * @return getMessage() - This returns an error and calls getMessage().
     */
    @Override
    public String toString(){
        return "Catalog Error: " + this.getMessage();
    }
}

