package PhilanthropyProject.project;

import PhilanthropyProject.classifiers.ClassifierImpl;

public class Donor extends ClassifierImpl {
    public Donor(int id, String name) {
        super(id, name);
    }

    public Donor() {
    }

    @Override
    public String toString() {
        return "Donor{} " + super.toString();
    }
}
