package PhilanthropyProject.project;

import PhilanthropyProject.classifiers.ChildClassifierImpl;

import java.math.BigDecimal;

/**
 * naxatesvac e gyuxery pahelu hamar, ir mej pahum e nayev ir qaxaqi Id-n
 */
public class District extends ChildClassifierImpl {
    private BigDecimal percent;

    public District(int id, String name, int parentId, BigDecimal percent) {
        super(id, name, parentId);
        if (percent == null) {
            percent = new BigDecimal(0);
        } else {
            if (percent.compareTo(new BigDecimal(100)) > 0) {
                throw new IllegalArgumentException("percent cannot be greater than 100.");
            }
            this.percent = percent;
        }

    }

    public District() {

    }

    @Override
    public String toString() {
        return "District{" +
                "percent=" + percent +
                "} " + super.toString();
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        if (percent.compareTo(new BigDecimal(100)) > 0) {
            throw new IllegalArgumentException("percent cannot be greater than 100.");
        }
        this.percent = percent;
    }
}