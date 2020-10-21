package eu.bsc.homework.reader;

import eu.bsc.homework.parser.FeeParser;
import eu.bsc.homework.parser.FeeParserResult;
import eu.bsc.homework.parser.PackageParseResult;
import eu.bsc.homework.parser.PackageParser;
import eu.bsc.homework.store.PackageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InitialConfigurationReaderImpl implements InitialConfigurationReader {

    private static final Logger LOG = LoggerFactory.getLogger(InitialConfigurationReaderImpl.class);

    private final PackageParser packageParser;

    private final PackageStore packageStore;

    private final FeeParser feeParser;

    @Autowired
    public InitialConfigurationReaderImpl(PackageParser packageParser, PackageStore packageStore, FeeParser feeParser) {
        this.packageParser = packageParser;
        this.packageStore = packageStore;
        this.feeParser = feeParser;
    }

    @Override
    public void loadPackagesFromFile(String filePath) {
        LOG.debug("Loading initial packages from file {}.", filePath);
        getLinesOfFile(filePath)
                .stream()
                .map(packageParser::parsePackage)
                .filter(PackageParseResult::isValidPackage)
                .forEach(pacParseResult -> packageStore.addPackage(pacParseResult.getPostalCode(), pacParseResult.getWeight()));
    }


    @Override
    public void loadFeesFromFile(String filePath) {
        LOG.debug("Loading fees from file {}.", filePath);
        getLinesOfFile(filePath)
                .stream()
                .map(feeParser::parseFee)
                .filter(FeeParserResult::isValidFee)
                .forEach(feeParserResult -> packageStore.addFee(feeParserResult.getWeightLimit(), feeParserResult.getPrice()));
    }

    private List<String> getLinesOfFile(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.isReadable(path)) {
            try (Stream<String> lines = Files.lines(path)) {
                return lines.collect(Collectors.toList());
            } catch (IOException e) {
                LOG.error(String.format("Unexpected IO exception while reading file %s", filePath), e);
                return Collections.emptyList();
            }
        } else {
            LOG.warn("File {} cannot be read.", filePath);
            System.out.printf(("Passed file %s cannot be read.") + System.lineSeparator(), filePath);
            return Collections.emptyList();
        }
    }
}
