package eu.bsc.homework.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PackageParserImpl implements PackageParser {

    private static final Logger LOG = LoggerFactory.getLogger(PackageParserImpl.class);

    private static final Pattern EXPECTED_PATTERN = Pattern.compile("^(\\d+.?\\d{0,3}) (\\d{5})$");

    @Override
    public PackageParseResult parsePackage(String packageInput) {
        LOG.debug("Parsing input {}", packageInput);
        Matcher matcher = EXPECTED_PATTERN.matcher(packageInput);
        return matcher.matches() ? getPackageParseResult(matcher) : getInvalidParseResult();
    }

    private PackageParseResult getPackageParseResult(Matcher matcher) {
        BigDecimal weight = new BigDecimal(matcher.group(1));
        String postalCode = matcher.group(2);
        LOG.debug("Successfully parsed input as package with code {} and weight {}.", postalCode, weight);
        return PackageParseResult.withCodeAndWeight(postalCode, weight);
    }

    private PackageParseResult getInvalidParseResult() {
        LOG.debug("Passed input does not match required pattern, returning invalid PackageParseResult.");
        return PackageParseResult.invalid();
    }
}
