package eu.bsc.homework.writer;

import eu.bsc.homework.store.InMemoryPackageStore;
import eu.bsc.homework.store.PackageStore;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

public class ConsoleOutputWriterTest {

    private OutputWriter outputWriter;
    private final PrintStream standardOut = System.out;

    @Before
    public void fillPackageStore() {

        PackageStore packageStore = new InMemoryPackageStore();

        packageStore.addFee(new BigDecimal("10"), new BigDecimal("10.00"));
        packageStore.addFee(new BigDecimal("5"), new BigDecimal("2.50"));

        packageStore.addPackage("11111", new BigDecimal("6"));
        packageStore.addPackage("11111", new BigDecimal("4"));
        packageStore.addPackage("11111", new BigDecimal("11"));

        packageStore.addPackage("22222", new BigDecimal("100"));

        packageStore.addPackage("33333", new BigDecimal("1"));

        outputWriter = new ConsoleOutputWriter(packageStore);

    }

    @Test
    public void writeWeightsOfPackagesToOutputTest() {
        String lineSeparator = System.lineSeparator();
        String expectedOutput =
                          "22222 100.000 10.00" + lineSeparator
                        + "11111 21.000 12.50" + lineSeparator
                        + "33333 1.000 0.00" + lineSeparator;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        outputWriter.writeWeightsOfPackagesToOutput();

        Assert.assertEquals(expectedOutput, outputStream.toString());
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }

}
