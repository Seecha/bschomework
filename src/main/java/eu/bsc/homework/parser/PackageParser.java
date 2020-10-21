package eu.bsc.homework.parser;

public interface PackageParser {

    /**
     * Parses given line into {@link PackageParseResult}
     * @param packageInput single line containing one package
     * @return PackageParseResult with parsed data.
     */
    PackageParseResult parsePackage(String packageInput);
}
