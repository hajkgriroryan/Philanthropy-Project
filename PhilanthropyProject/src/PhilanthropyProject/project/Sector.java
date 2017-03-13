package PhilanthropyProject.project;

import PhilanthropyProject.classifiers.ClassifierImpl;

/**
 * naxatesvac e sectornery pahelu hamar,
 * nayev pahum e ir subsectornery arrayum
 */
public class Sector extends ClassifierImpl {
    SubSector subSector;

    public Sector(int id, String name, SubSector subSector) {
        super(id, name);
        if (subSector == null) {
            this.subSector = new SubSector();
        } else {
            this.subSector = subSector;
        }
    }

    public Sector() {

    }


    public SubSector getSubSector() {
        return subSector;
    }

    public void setSubSector(SubSector subSector) {
        this.subSector = subSector;
    }

    @Override
    public String toString() {
        return "Sector{" +
                "subSector=" + subSector +
                "} " + super.toString();
    }
}
