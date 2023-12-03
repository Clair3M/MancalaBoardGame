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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

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
        final StringBuilder filepath = new StringBuilder("assets/");
        File directory = new File(filepath.toString());
        if (!directory.exists()) {
            Files.createDirectories(Path.of(filepath.toString()));
        }
        filepath.append(" " + filename);
        try (FileOutputStream fileOutStream = new FileOutputStream(filepath.toString());
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
        final StringBuilder filepath = new StringBuilder("assets/");
        File directory = new File(filepath.toString());
        if (!directory.exists()) {
            Files.createDirectories(Path.of(filepath.toString()));
        }
        filepath.append(" " + filename);
        try (FileInputStream fileIn = new FileInputStream(filepath.toString());
             ObjectInputStream fileInStream = new ObjectInputStream(fileIn);) {
            return (Serializable) fileInStream.readObject();
        } catch (ClassNotFoundException err) {
            throw new IOException();
        }
    }

    /**
     * Loads the contents of a file as a string and returns the string
     * 
     * @param filename The name of the file whose content is to be read
     * @return The text that was in the file
     */
    public static String readTextFile(final String filename) throws IOException {
        final StringBuilder fileContent = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(filename));) {
        String buffer;
        while ((buffer = in.readLine()) != null) {
            fileContent.append(buffer);
        }
        return fileContent.toString();
        }
    }
}