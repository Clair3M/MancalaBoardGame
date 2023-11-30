/** made with reference to a stackoverflow.com post made by
  * C.Champagne
  * https://stackoverflow.com/a/17297082
  */

package mancala;

import java.io.IOException;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A utility class that provides methods for saving and loading serializable objects
 */
public class Saver {

    /**
     * Saves a serializable object to a file
     * 
     * @param toSave The serializable object to be saved
     * @param filename The file where the object is to be stored
     */
    public static void saveObject(final Serializable toSave, final String filename) throws IOException {
        final String filepath = "./assets/" + filename;
        try (FileOutputStream fileOutStream = new FileOutputStream(filepath);
             ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);) {
            objOutStream.writeObject(toSave);
        }
    }

    /**
     * Loads a serializable object from a file
     * 
     * @param filename The name of the file where the object is held
     * @return The serializable object from the file
     */
    public static Serializable loadObject(final String filename) throws IOException {
        final String filepath = "./assets/" + filename;
        
        try (FileInputStream fileIn = new FileInputStream(filepath);
             ObjectInputStream fileInStream = new ObjectInputStream(fileIn);) {
            return (Serializable) fileInStream.readObject();
        } catch (ClassNotFoundException err) {
            throw new IOException();
        }
    }
}