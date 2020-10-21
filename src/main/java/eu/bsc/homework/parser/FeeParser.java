package eu.bsc.homework.parser;

public interface FeeParser {

    /**
     * Parses given line into {@link FeeParserResult}
     * @param feeInput single line containing one fee record.
     * @return FeeParserResult with parsed data.
     */
    FeeParserResult parseFee(String feeInput);
}
