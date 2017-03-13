package PhilanthropyProject.project;

import PhilanthropyProject.classifiers.ClassifierImpl;

import java.math.BigDecimal;

/**
 * Currency class vory naxatesvac e
 * arjuyti tipy ev arjuyti giny dramov pahelu hamar
 */
public class Currency extends ClassifierImpl {
    private BigDecimal currencyToDram;
    private BigDecimal totalAmount;
    private BigDecimal valueDram;

    public Currency(int id, String name, BigDecimal currencyToDram, BigDecimal totalAmount) {
        super(id, name);
        if (currencyToDram == null) {
            this.currencyToDram = new BigDecimal(0);
        } else if (currencyToDram.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("currencyToDram cannot be negative number.");
        } else {
            this.currencyToDram = currencyToDram;
        }
        if (totalAmount == null) {
            this.totalAmount = new BigDecimal(0);
        } else if (totalAmount.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("totalAmount cannot be negative number.");
        } else {
            this.totalAmount = totalAmount;
        }
        valueDram = this.currencyToDram.multiply(this.totalAmount);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyToDram=" + currencyToDram +
                ", totalAmount=" + totalAmount +
                ", valueDram=" + valueDram +
                "} " + super.toString();
    }

    public BigDecimal getCurrencyToDram() {
        return currencyToDram;
    }

    public void setCurrencyToDram(BigDecimal currencyToDram) {
        this.currencyToDram = currencyToDram;
        valueDram = this.currencyToDram.multiply(this.totalAmount);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        valueDram = this.currencyToDram.multiply(this.totalAmount);
    }

    public BigDecimal getValueDram() {
        return valueDram;
    }
}
