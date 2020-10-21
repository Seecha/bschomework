package eu.bsc.homework.parser;

import java.math.BigDecimal;

/**
 * Class holding data about one parsed fee.
 * If the fee was successfully parsed validFee is true and weightLimit and price are correctly filled.
 * If validFee is false, the parsing was not successful.
 */
public final class FeeParserResult {

    private final boolean validFee;
    private final BigDecimal weightLimit;
    private final BigDecimal price;

    private FeeParserResult(boolean validFee, BigDecimal weightLimit, BigDecimal price) {
        this.validFee = validFee;
        this.weightLimit = weightLimit;
        this.price = price;
    }

    public boolean isValidFee() {
        return validFee;
    }

    public BigDecimal getWeightLimit() {
        return weightLimit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static FeeParserResult invalid() {
        return new FeeParserResult(false, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public static FeeParserResult withWeightLimitAndPrice(BigDecimal weightLimit, BigDecimal price) {
        return new FeeParserResult(true, weightLimit, price);
    }
}
