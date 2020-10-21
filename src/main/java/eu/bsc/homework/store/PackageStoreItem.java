package eu.bsc.homework.store;

import java.math.BigDecimal;

/**
 * Class holding info about one stored package.
 */
public class PackageStoreItem {

    private final String postalCode;
    private final BigDecimal fee;
    private final BigDecimal weight;

    public PackageStoreItem(String postalCode, BigDecimal fee, BigDecimal weight) {
        this.postalCode = postalCode;
        this.fee = fee;
        this.weight = weight;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
