package eu.bsc.homework.reader;

/**
 * Service responsible for reading user input about packages from console.
 */
public interface ConsoleInputReader {

    /**
     * Waits for user input and tries to process it as an package.
     * This method is terminated by quit command from user.
     */
    void readConsoleInputs();

}
