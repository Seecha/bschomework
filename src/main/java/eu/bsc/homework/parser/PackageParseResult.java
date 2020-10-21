package eu.bsc.homework.parser;

import java.math.BigDecimal;

/**
 * Class holding data about one parsed package.
 * If the package was successfully parsed validPackage is true and postalCode and weight are correctly filled.
 * If validPackage is false, the parsing was not successful.
 */
public final class PackageParseResult {

    private final boolean validPackage;
    private final String postalCode;
    private final BigDecimal weight;

    private PackageParseResult(boolean validPackage, String postalCode, BigDecimal weight) {
        this.validPackage = validPackage;
        this.postalCode = postalCode;
        this.weight = weight;
    }

    public boolean isValidPackage() {
        return validPackage;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public static PackageParseResult invalid() {
        return new PackageParseResult(false, "00000", BigDecimal.ZERO);
    }

    public static PackageParseResult withCodeAndWeight(String code, BigDecimal weight) {
        return new PackageParseResult(true, code, weight);
    }
}
