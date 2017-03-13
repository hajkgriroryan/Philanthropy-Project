package PhilanthropyProject.project;

import PhilanthropyProject.classifiers.ClassifierImpl;

/**
 * Contact classum ogtagorcvox class e,
 * vory naxatesvac e kazmakerputyunery pahelu hamar
 */
public class Organisation extends ClassifierImpl {

    public Organisation(int id, String name) {
        super(id, name);
    }

    public Organisation() {
    }

    @Override
    public String toString() {
        return "Organisation{} " + super.toString();
    }
}
