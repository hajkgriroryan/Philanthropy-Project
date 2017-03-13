package PhilanthropyProject.project;

import PhilanthropyProject.classifiers.ClassifierImpl;

import java.util.ArrayList;

/**
 * naxatesvac e marzery pahelu hamar, nayev pahum e ir
 * qaxaqnery arrayum
 */
public class Region extends ClassifierImpl {
    private ArrayList<Province> provincesArray;

    public Region(int id, String name, ArrayList<Province> provincesArray) {
        super(id, name);
        if (provincesArray == null) {
            this.provincesArray = new ArrayList<Province>();
        } else {
            this.provincesArray = provincesArray;
        }

    }

    public Region() {

    }

    public boolean addProvince(Province province) {
        return provincesArray.add(province);
    }

    public boolean deleteProvince(Province province) {
        return provincesArray.remove(province);
    }

    public void clearProvinces() {
        provincesArray.clear();
    }

    @Override
    public String toString() {
        return "Region{" +
                "provincesArray=" + provincesArray +
                "} " + super.toString();
    }

    public ArrayList<Province> getProvincesArray() {
        return provincesArray;
    }

    public void setProvincesArray(ArrayList<Province> provincesArray) {
        if (provincesArray == null) {
            this.provincesArray = new ArrayList<Province>();
        } else {
            this.provincesArray = provincesArray;
        }
    }

}
