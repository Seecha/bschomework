package eu.bsc.homework;

import eu.bsc.homework.reader.ConsoleInputReader;
import eu.bsc.homework.reader.InitialConfigurationReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BscHomeworkRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(BscHomeworkRunner.class);

    private final InitialConfigurationReader initialConfigurationReader;

    private final ConsoleInputReader consoleInputReader;

    @Autowired
    public BscHomeworkRunner(InitialConfigurationReader initialConfigurationReader, ConsoleInputReader consoleInputReader) {
        this.initialConfigurationReader = initialConfigurationReader;
        this.consoleInputReader = consoleInputReader;
    }

    @Override
    public void run(String... args) {
        LOG.info("BSC Homework application stared.");
        if(args.length > 1) {
            initialConfigurationReader.loadFeesFromFile(args[1]);
        }
        if (args.length > 0) {
            initialConfigurationReader.loadPackagesFromFile(args[0]);
        }
        consoleInputReader.readConsoleInputs();
        System.exit(0);
    }

}
