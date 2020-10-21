package eu.bsc.homework.reader;

import eu.bsc.homework.parser.PackageParserImpl;
import eu.bsc.homework.store.InMemoryPackageStore;
import eu.bsc.homework.store.PackageStore;
import eu.bsc.homework.store.PackageStoreItem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

public class ConsoleInputReaderImplTest {

    private ConsoleInputReader consoleInputReader;
    private PackageStore packageStore;
    private final InputStream standardIn = System.in;

    @Before
    public void init() {
        packageStore = new InMemoryPackageStore();
        consoleInputReader = new ConsoleInputReaderImpl(new PackageParserImpl(), packageStore);
    }

    @Test
    public void readConsoleInputTest_valid() {
        String commands = "6 12345" + System.lineSeparator() + "quit";
        ByteArrayInputStream in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);
        consoleInputReader.readConsoleInputs();
        Assert.assertEquals(1, packageStore.getAllPackages().size());
        PackageStoreItem packageStoreItem = packageStore.getAllPackages().get(0);
        Assert.assertEquals(new BigDecimal("6"), packageStoreItem.getWeight());
        Assert.assertEquals("12345", packageStoreItem.getPostalCode());
    }

    @Test
    public void readConsoleInputTest_invalid() {
        String commands = "6 123456" + System.lineSeparator() + "quit";
        ByteArrayInputStream in = new ByteArrayInputStream(commands.getBytes());
        System.setIn(in);
        consoleInputReader.readConsoleInputs();
        Assert.assertTrue(packageStore.getAllPackages().isEmpty());
    }

    @After
    public void tearDown() {
        System.setIn(standardIn);
    }
}
