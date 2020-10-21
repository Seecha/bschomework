package eu.bsc.homework.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FeeParserImpl implements FeeParser{

    private static final Logger LOG = LoggerFactory.getLogger(FeeParserImpl.class);

    private static final Pattern EXPECTED_PATTERN = Pattern.compile("^(\\d+.?\\d{0,3}) (\\d+.?\\d{2})$");

    public FeeParserResult parseFee(String feeInput) {
        LOG.debug("Parsing input {}", feeInput);
        Matcher matcher = EXPECTED_PATTERN.matcher(feeInput);
        return matcher.matches() ? getFeeParseResult(matcher) : getInvalidParseResult();
    }

    private FeeParserResult getFeeParseResult(Matcher matcher) {
        BigDecimal weight = new BigDecimal(matcher.group(1));
        BigDecimal price = new BigDecimal(matcher.group(2));
        LOG.debug("Successfully parsed input as fee with weight limit {} and price {}.", weight, price);
        return FeeParserResult.withWeightLimitAndPrice(weight, price);

    }

    private FeeParserResult getInvalidParseResult() {
        LOG.debug("Passed input does not match required pattern, returning invalid FeeParserResult.");
        return FeeParserResult.invalid();
    }
}
