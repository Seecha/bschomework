package eu.bsc.homework.reader;

import eu.bsc.homework.parser.FeeParserImpl;
import eu.bsc.homework.parser.PackageParserImpl;
import eu.bsc.homework.store.InMemoryPackageStore;
import eu.bsc.homework.store.PackageStore;
import eu.bsc.homework.store.PackageStoreItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class InitialConfigurationReaderImplTest {

    private static final String TEST_PACKAGE_CONFIGURATION_PATH = "/eu/bsc/homework/reader/testPackagesConfiguration.txt";
    private static final String TEST_FEES_CONFIGURATION_PATH = "/eu/bsc/homework/reader/testFeesConfiguration.txt";

    private PackageStore packageStore;
    private InitialConfigurationReader initialConfigurationReader;

    @Before
    public void init() {
        packageStore = new InMemoryPackageStore();
        initialConfigurationReader = new InitialConfigurationReaderImpl(
                new PackageParserImpl(),
                packageStore,
                new FeeParserImpl()
        );
    }

    @Test
    public void loadPackagesFromFileTest() throws URISyntaxException {
        initialConfigurationReader.loadPackagesFromFile(getAbsolutePathToResource(TEST_PACKAGE_CONFIGURATION_PATH));

        List<PackageStoreItem> allPackages = packageStore.getAllPackages();
        Assert.assertEquals(5, allPackages.size());
        assertValidPackage(allPackages.get(0), "3.4", "08801", "0");
        assertValidPackage(allPackages.get(1), "5", "09300", "0");
        assertValidPackage(allPackages.get(2), "10", "12345", "0");
        assertValidPackage(allPackages.get(3), "11", "12354", "0");
        assertValidPackage(allPackages.get(4), "0", "98745", "0");
    }

    @Test
    public void loadPackagesFromFileTest_invalidFile() {
        initialConfigurationReader.loadPackagesFromFile("invalidPath");
        Assert.assertTrue(packageStore.getAllPackages().isEmpty());
    }

    @Test
    public void loadFeesFromFileTest() throws URISyntaxException {
        initialConfigurationReader.loadFeesFromFile(getAbsolutePathToResource(TEST_FEES_CONFIGURATION_PATH));
        initialConfigurationReader.loadPackagesFromFile(getAbsolutePathToResource(TEST_PACKAGE_CONFIGURATION_PATH));
        List<PackageStoreItem> allPackages = packageStore.getAllPackages();

        Assert.assertEquals(5, allPackages.size());
        assertValidPackage(allPackages.get(0), "3.4", "08801", "2.00");
        assertValidPackage(allPackages.get(1), "5", "09300", "2.50");
        assertValidPackage(allPackages.get(2), "10", "12345", "5.00");
        assertValidPackage(allPackages.get(3), "11", "12354", "5.00");
        assertValidPackage(allPackages.get(4), "0", "98745", "0");
    }

    private String getAbsolutePathToResource(String relativePath) throws URISyntaxException {
        URL resource = this.getClass().getResource(relativePath);
        return Paths.get(resource.toURI()).toFile().getAbsolutePath();
    }

    private void assertValidPackage(PackageStoreItem packageParseResult, String weight, String postalCode, String fee) {
        Assert.assertEquals(new BigDecimal(weight), packageParseResult.getWeight());
        Assert.assertEquals(postalCode, packageParseResult.getPostalCode());
        Assert.assertEquals(new BigDecimal(fee), packageParseResult.getFee());
    }
}
