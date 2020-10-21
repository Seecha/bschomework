package eu.bsc.homework.store;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class InMemoryPackageStoreTest {

    private final PackageStore packageStore = new InMemoryPackageStore();

    @Before
    public void fillStoreWithTestFees() {
        packageStore.addFee(new BigDecimal("10"), new BigDecimal("2.50"));
        packageStore.addFee(new BigDecimal("2"), new BigDecimal("1.50"));
    }

    @Test
    public void addPackageTest_weightUnderLowestFee() {
        packageStore.addPackage("12345", new BigDecimal("1"));
        assertSinglePackageWithFee("1","0");
    }

    @Test
    public void addPackageTest_weightAboveHighestFee() {
        packageStore.addPackage("12345", new BigDecimal("11"));
        assertSinglePackageWithFee("11", "2.50");
    }

    @Test
    public void addPackageTest_weightBetweenFees() {
        packageStore.addPackage("12345", new BigDecimal("5"));
        assertSinglePackageWithFee("5", "1.50");
    }

    @Test
    public void addPackageTest_weightMatchingFeeLimit() {
        packageStore.addPackage("12345", new BigDecimal("2"));
        assertSinglePackageWithFee("2", "1.50");
    }

    private void assertSinglePackageWithFee(String weight, String fee) {
        List<PackageStoreItem> allPackages = packageStore.getAllPackages();
        Assert.assertEquals(1, allPackages.size());
        PackageStoreItem packageStoreItem = allPackages.get(0);
        Assert.assertEquals("12345", packageStoreItem.getPostalCode());
        Assert.assertEquals(new BigDecimal(weight), packageStoreItem.getWeight());
        Assert.assertEquals(new BigDecimal(fee), packageStoreItem.getFee());
    }

}
