package eu.bsc.homework.reader;

import eu.bsc.homework.parser.PackageParseResult;
import eu.bsc.homework.parser.PackageParser;
import eu.bsc.homework.store.PackageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInputReaderImpl implements ConsoleInputReader {

    private static final String INVALID_INPUT_MESSAGE =
            "Invalid input passed. Expected inputs in format 'w.www ccccc' where w.www is package weight and ccccc is postal code. " +
                    "Example of valid inputs are '10 12345' or '3.567 44444'";

    private static final String VALID_INPUT_MESSAGE = "Package with weight %s for postal code %s successfully stored";

    private static final String WELCOME_MESSAGE = "Waiting for user inputs with pattern 'w.www ccccc' where w.www is package weight and ccccc is postal code.";

    private static final String EXIT_COMMAND_NAME = "quit";

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleInputReaderImpl.class);

    private final PackageParser packageParser;

    private final PackageStore packageStore;

    @Autowired
    public ConsoleInputReaderImpl(PackageParser packageParser, PackageStore packageStore) {
        this.packageParser = packageParser;
        this.packageStore = packageStore;
    }

    @Override
    public void readConsoleInputs() {
        System.out.println(WELCOME_MESSAGE);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            LOG.debug("Waiting for user input.");
            String nextLine = scanner.nextLine();
            LOG.debug("Processing user input {}.", nextLine);
            if (nextLine.startsWith(EXIT_COMMAND_NAME)) {
                LOG.debug("Quit command received, terminating application.");
                break;
            } else {
                processInputPackage(nextLine);
            }
        }
    }

    private void processInputPackage(String inputLine) {
        PackageParseResult packageParseResult = packageParser.parsePackage(inputLine);
        if (packageParseResult.isValidPackage()) {
            packageStore.addPackage(packageParseResult.getPostalCode(), packageParseResult.getWeight());
            System.out.printf((VALID_INPUT_MESSAGE) + System.lineSeparator(), packageParseResult.getWeight(), packageParseResult.getPostalCode());
        } else {
            System.out.println(INVALID_INPUT_MESSAGE);
        }
    }
}
