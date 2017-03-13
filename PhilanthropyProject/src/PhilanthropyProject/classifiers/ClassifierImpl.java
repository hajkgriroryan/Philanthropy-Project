package PhilanthropyProject.classifiers;

/**
 *  abstract class vorum ka id, ir getter-ov name ir getterov, setterov
 *  override a arac toString-y, vor@ ir mej nerarum a id-n u name-y,
 *  equals vory hamematum e id-ov
 *  hashCode vory havasar e id-in
 */
public abstract class ClassifierImpl implements Classifier {
    private int id;
    private String name;

    public ClassifierImpl(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClassifierImpl() {
        id = 0;
        name = null;
    }

    @Override
    public String toString() {
        return "ClassifierImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassifierImpl that = (ClassifierImpl) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
