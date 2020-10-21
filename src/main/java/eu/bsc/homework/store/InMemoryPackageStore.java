package eu.bsc.homework.store;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Simple implementation of {@link PackageStore} holding data in memory.
 */
@Component
public class InMemoryPackageStore implements PackageStore {

    private final List<PackageStoreItem> packages;
    private final TreeMap<BigDecimal, BigDecimal> feesByWeights;

    public InMemoryPackageStore() {
        packages = new LinkedList<>();
        feesByWeights = new TreeMap<>();
    }

    @Override
    public synchronized void addFee(BigDecimal weight, BigDecimal fee) {
        feesByWeights.put(weight, fee);
    }

    @Override
    public synchronized void addPackage(String code, BigDecimal weight) {
        packages.add(new PackageStoreItem(
                code,
                getPriceForWeight(weight),
                weight
        ));
    }

    @Override
    public List<PackageStoreItem> getAllPackages() {
        return Collections.unmodifiableList(packages);
    }

    private BigDecimal getPriceForWeight(BigDecimal weight) {
        Map.Entry<BigDecimal, BigDecimal> closestEntry = feesByWeights.floorEntry(weight);
        return closestEntry == null ? BigDecimal.ZERO : closestEntry.getValue();
    }
}
