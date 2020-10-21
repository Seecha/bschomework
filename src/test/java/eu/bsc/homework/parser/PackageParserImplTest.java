package eu.bsc.homework.parser;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class PackageParserImplTest {

    private final PackageParser packageParser = new PackageParserImpl();

    @Test
    public void parsePackageTest_validInput() {
        PackageParseResult packageParseResult = packageParser.parsePackage("6 12345");
        assertValidPackage(packageParseResult, "6", "12345");
    }

    @Test
    public void parsePackageTest_validInputWithDecimals() {
        PackageParseResult packageParseResult = packageParser.parsePackage("6.123 23456");
        assertValidPackage(packageParseResult, "6.123", "23456");
    }

    @Test
    public void parsePackageTest_invalidNegativeNumber() {
        PackageParseResult packageParseResult = packageParser.parsePackage("-6.123 23456");
        Assert.assertFalse(packageParseResult.isValidPackage());
    }

    @Test
    public void parsePackageTest_invalidTooManyDecimals() {
        PackageParseResult packageParseResult = packageParser.parsePackage("6.1234 23456");
        Assert.assertFalse(packageParseResult.isValidPackage());
    }

    @Test
    public void parsePackageTest_invalidLongPostalCode() {
        PackageParseResult packageParseResult = packageParser.parsePackage("6.1234 234567");
        Assert.assertFalse(packageParseResult.isValidPackage());
    }

    @Test
    public void parsePackageTest_invalidLetterInWeight() {
        PackageParseResult packageParseResult = packageParser.parsePackage("a.00 a2345");
        Assert.assertFalse(packageParseResult.isValidPackage());
    }

    @Test
    public void parsePackageTest_invalidLetterInPostalCode() {
        PackageParseResult packageParseResult = packageParser.parsePackage("6.1234 a2345");
        Assert.assertFalse(packageParseResult.isValidPackage());
    }

    private void assertValidPackage(PackageParseResult packageParseResult, String weight, String postalCode) {
        Assert.assertTrue(packageParseResult.isValidPackage());
        Assert.assertEquals(new BigDecimal(weight), packageParseResult.getWeight());
        Assert.assertEquals(postalCode, packageParseResult.getPostalCode());
    }
}
