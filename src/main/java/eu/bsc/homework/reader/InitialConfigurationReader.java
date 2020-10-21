package eu.bsc.homework.reader;

/**
 * Service responsible for reading initial data for application from files.
 */
public interface InitialConfigurationReader {

    /**
     * Reads file on given path and tries to parse each contained line as a package.
     * If the path or content of file is nto valid, nothing is done.
     * @param filePath absolute path to input file
     */
    void loadPackagesFromFile(String filePath);

    /**
     * Reads file on given path and tries to parse each contained line as fee record.
     * If the path or content of file is nto valid, nothing is done.
     * @param filePath absolute path to input file
     */
    void loadFeesFromFile(String filePath);
}
