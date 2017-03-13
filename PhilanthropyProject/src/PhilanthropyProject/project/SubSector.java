package PhilanthropyProject.project;

import PhilanthropyProject.classifiers.ChildClassifierImpl;

/**
 * naxatesvac e SubSectorney pahelu hamar
 */
public class SubSector extends ChildClassifierImpl {
    public SubSector(int id, String name, int parentId) {
        super(id, name, parentId);
    }

    public SubSector() {

    }

    @Override
    public String toString() {
        return "SubSector{} " + super.toString();
    }
}
