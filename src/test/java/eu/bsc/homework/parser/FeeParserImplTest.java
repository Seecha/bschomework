package eu.bsc.homework.parser;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class FeeParserImplTest {

    private final FeeParser feeParser = new FeeParserImpl();

    @Test
    public void parseFeeTest_validInput() {
        FeeParserResult feeParserResult = feeParser.parseFee("10 5.00");
        assertValidFee(feeParserResult, "10", "5.00");
    }

    @Test
    public void parseFeeTest_validInputWithDecimals() {
        FeeParserResult feeParserResult = feeParser.parseFee("0.2 0.50");
        assertValidFee(feeParserResult, "0.2", "0.50");
    }

    @Test
    public void parseFeeTest_invalidNegativeWeight() {
        FeeParserResult feeParserResult = feeParser.parseFee("-0.2 0.50");
        Assert.assertFalse(feeParserResult.isValidFee());
    }

    @Test
    public void parseFeeTest_invalidNegativePrice() {
        FeeParserResult feeParserResult = feeParser.parseFee("0.2 -0.50");
        Assert.assertFalse(feeParserResult.isValidFee());
    }

    @Test
    public void parseFeeTest_invalidTooManyDecimalsInWeight() {
        FeeParserResult feeParserResult = feeParser.parseFee("0.2222 0.50");
        Assert.assertFalse(feeParserResult.isValidFee());
    }

    @Test
    public void parseFeeTest_invalidTooManyDecimalsInPrice() {
        FeeParserResult feeParserResult = feeParser.parseFee("0.2 1.511");
        Assert.assertFalse(feeParserResult.isValidFee());
    }

    @Test
    public void parseFeeTest_invalidNotEnoughDecimalsInPrice() {
        FeeParserResult feeParserResult = feeParser.parseFee("0.2 1.5");
        Assert.assertFalse(feeParserResult.isValidFee());
    }

    @Test
    public void parseFeeTest_invalidLetterInWeight() {
        FeeParserResult feeParserResult = feeParser.parseFee("0.a 0.50");
        Assert.assertFalse(feeParserResult.isValidFee());
    }

    @Test
    public void parseFeeTest_invalidLetterInPrice() {
        FeeParserResult feeParserResult = feeParser.parseFee("0.2 a.00");
        Assert.assertFalse(feeParserResult.isValidFee());
    }

    private void assertValidFee(FeeParserResult feeParserResult, String expectedWeight, String expectedPrice) {
        Assert.assertTrue(feeParserResult.isValidFee());
        Assert.assertEquals(new BigDecimal(expectedWeight), feeParserResult.getWeightLimit());
        Assert.assertEquals(new BigDecimal(expectedPrice), feeParserResult.getPrice());
    }

}
