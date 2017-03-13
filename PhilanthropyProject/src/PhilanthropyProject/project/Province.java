package PhilanthropyProject.project;

import PhilanthropyProject.classifiers.ChildClassifierImpl;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * naxatesvac e qaxaqnery pahelu hamar, nayev pahum e
 * qaxaqi harakic gyuxery arrayum
 * nayev pahum e ir marzi id-n
 */
public class Province extends ChildClassifierImpl {
    private ArrayList<District> districtsArray;
    private BigDecimal sumPercent;

    public Province(int id, String name, int parentId, ArrayList<District> districtsArray) {
        super(id, name, parentId);
        sumPercent = new BigDecimal(0);
        if (districtsArray == null) {
            this.districtsArray = new ArrayList<District>();
        } else {
            this.districtsArray = districtsArray;
            for (District i : districtsArray) {
                sumPercent = sumPercent.add(i.getPercent());
            }
        }
        if (sumPercent.compareTo(new BigDecimal(100)) > 0) {
            throw new IllegalArgumentException("sumPercent cannot be more than 100.");
        }
    }

    public Province() {

    }

    public boolean addDistrict(District district) {
        return districtsArray.add(district);
    }

    public boolean deleteDistrict(District district) {
        return districtsArray.remove(district);
    }

    public void clearDistricts() {
        districtsArray.clear();
    }

    @Override
    public String toString() {
        return "Province{" +
                "districtsArray=" + districtsArray +
                ", sumPercent=" + sumPercent +
                "} " + super.toString();
    }

    public ArrayList<District> getDistrictsArray() {
        return districtsArray;
    }

    public void setDistrictsArray(ArrayList<District> districtsArray) {
        if (districtsArray == null) {
            this.districtsArray = new ArrayList<District>();
        } else {
            this.districtsArray = districtsArray;
        }
    }
}
