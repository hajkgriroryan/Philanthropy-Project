package PhilanthropyProject.classifiers;

/**
 * abstract klass vory jarangum a ClassifierImpl classic
 * ir mej pahum e parentId datan, override e arac toStringy,
 * isk equalsy u hashcode-y nuyn e inch ClassifierImpl-ny
 */
public abstract class ChildClassifierImpl extends ClassifierImpl implements ChildClassifier {
    private int parentId;

    public ChildClassifierImpl(int id, String name, int parentId) {
        super(id, name);
        this.parentId = parentId;

    }

    public ChildClassifierImpl() {
        super();
        parentId = 0;
    }

    @Override
    public String toString() {
        return "ChildClassifierImpl{" +
                "parentId=" + parentId +
                "} " + super.toString();
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}