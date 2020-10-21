package eu.bsc.homework.task;

import eu.bsc.homework.writer.OutputWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled task responsible for triggering {@link OutputWriter} every minute.
 */
@Component
public class WriteOutputTask {

    private static final Logger LOG = LoggerFactory.getLogger(WriteOutputTask.class);

    private final OutputWriter outputWriter;

    @Autowired
    public WriteOutputTask(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    @Scheduled(fixedRate = 60000)
    public void writeWeightsToOutput() {
        LOG.debug("Scheduled task triggered.");
        outputWriter.writeWeightsOfPackagesToOutput();
    }
}
