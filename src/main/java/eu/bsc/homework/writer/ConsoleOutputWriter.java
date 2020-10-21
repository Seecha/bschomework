package eu.bsc.homework.writer;

import eu.bsc.homework.store.PackageStore;
import eu.bsc.homework.store.PackageStoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * Implementation of {@link OutputWriter} for writing information into console.
 */
@Component
public class ConsoleOutputWriter implements OutputWriter {

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleOutputWriter.class);

    private final PackageStore packageStore;

    @Autowired
    public ConsoleOutputWriter(PackageStore packageStore) {
        this.packageStore = packageStore;
    }

    public void writeWeightsOfPackagesToOutput() {
        LOG.debug("Writing info about Packages to console.");
        StringBuilder stringBuilder = new StringBuilder();
        packageStore.getAllPackages()
                .stream()
                .collect(Collectors.groupingBy(PackageStoreItem::getPostalCode))
                .entrySet()
                .stream()
                .map(entry -> getOutputLine(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(OutputLine::getTotalWeight).reversed())
                .forEach(addLineToStringBuilder(stringBuilder));
        System.out.print(stringBuilder);
    }

    private OutputLine getOutputLine(String postalCode, List<PackageStoreItem> packageStoreItemList) {
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (PackageStoreItem packageStoreItem : packageStoreItemList) {
            totalWeight = totalWeight.add(packageStoreItem.getWeight());
            totalPrice = totalPrice.add(packageStoreItem.getFee());
        }
        return new OutputLine(postalCode, totalWeight, totalPrice);
    }

    private Consumer<OutputLine> addLineToStringBuilder(StringBuilder stringBuilder) {
        return outputLine -> stringBuilder
                .append(outputLine.print())
                .append(System.lineSeparator());
    }

    /**
     * Crate holding information about total weight and fee of all packages with same postal code.
     */
    private static class OutputLine {
        private final String postalCode;
        private final BigDecimal totalWeight;
        private final BigDecimal totalCost;

        private OutputLine(String postalCode, BigDecimal totalWeight, BigDecimal totalCost) {
            this.postalCode = postalCode;
            this.totalWeight = totalWeight;
            this.totalCost = totalCost;
        }

        private BigDecimal getTotalWeight() {
            return totalWeight;
        }

        private String print() {
            return postalCode
                    + ' '
                    + totalWeight.setScale(3, BigDecimal.ROUND_UNNECESSARY)
                    + ' '
                    + totalCost.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        }
    }
}
