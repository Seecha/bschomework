package eu.bsc.homework.store;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service responsible for storing packages and their fees.
 */
public interface PackageStore {

    /**
     * Inserts record about fee for given weight of package.
     * @param weight Lower bound of weight, inclusive.
     * @param fee fee for package of given or higher weight.
     */
    void addFee(BigDecimal weight, BigDecimal fee);

    /**
     * Insert new package.
     * @param code postal code of package.
     * @param weight weight package.
     */
    void addPackage(String code, BigDecimal weight);

    /**
     * Returns read-only view of all stored packages.
     * @return Read-only view of all stored packages.
     */
    List<PackageStoreItem> getAllPackages();
}
