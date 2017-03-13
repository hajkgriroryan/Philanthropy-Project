package PhilanthropyProject.project;

import java.util.ArrayList;
import java.util.Date;

/**
 * naxatesvac e projecti bolor atributnery pahelu hamar,
 * override a arac toString, hashcode, equals funkcianery
 */
public class Project {
    private int id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String duration;
    private Currency valueType;
    private Sector projectSector;
    private ArrayList<Region> projectRegions;
    private ArrayList<Contact> projectContacts;
    private ArrayList<Donor> projectDonors;


    public Project(int id, String title, String description, Date startDate, Date endDate,
                   Currency valueType, Sector projectSector, ArrayList<Contact> projectContacts,
                   ArrayList<Region> projectRegions, ArrayList<Donor> projectDonors) {
        if (id < 0) {
            throw new IllegalArgumentException("id cannot be negative number.");
        }
        this.id = id;
        if (title == null) {
            throw new IllegalArgumentException("title cannot be null.");
        }
        this.title = title;
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null.");
        }
        this.description = description;
        if (startDate == null) {
            throw new IllegalArgumentException("startDate cannot be null.");
        }
        this.startDate = startDate;

        if (endDate == null) {
            throw new IllegalArgumentException("endDate cannot be null.");
        }
        this.endDate = endDate;
        this.duration = getDatesDifference(this.startDate, this.endDate);
        if (valueType == null) {
            throw new IllegalArgumentException("valueType cannot be null.");
        }
        this.valueType = valueType;
        if (projectSector == null) {
            this.projectSector = new Sector();
        } else {
            this.projectSector = projectSector;
        }

        if (projectContacts == null) {
            this.projectContacts = new ArrayList<Contact>();
        } else {
            this.projectContacts = projectContacts;
        }

        if (projectRegions == null) {
            this.projectRegions = new ArrayList<Region>();
        } else {
            this.projectRegions = projectRegions;
        }

        if (projectDonors == null) {
            this.projectDonors = new ArrayList<Donor>();
        } else {
            this.projectDonors = projectDonors;
        }
    }

    public boolean addRegion(Region region) {
        return projectRegions.add(region);
    }

    public boolean deleteRegion(Region region) {
        return projectRegions.remove(region);
    }

    public void clearRegions() {
        projectRegions.clear();
    }

    public boolean addContact(Contact contact) {
        return projectContacts.remove(contact);
    }

    public boolean deleteContact(Contact contact) {
        return projectContacts.remove(contact);
    }

    public void clearContacts() {
        projectContacts.clear();
    }

    public boolean addDonor(Donor donor) {
        return projectDonors.remove(donor);
    }

    public boolean deleteDonor(Donor donor) {
        return projectDonors.remove(donor);
    }

    public void clearDonors() {
        projectDonors.clear();
    }

    private static final String getDatesDifference(Date date1, Date date2) {
//        YearMonth m1 = YearMonth.from(date1.toInstant());
//        YearMonth m2 = YearMonth.from(date2.toInstant());
//        String ans = (m1.until(m2, ChronoUnit.MONTHS) + 1) + " months " + (m1.until(m2, ChronoUnit.DAYS) + 1) + " days";
//        return ans;

        return ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24)) / 30 + " Months " +
                ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24)) % 30 + " Days ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Project project = (Project) o;

        return id == project.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration='" + duration + '\'' +
                ", valueType=" + valueType +
                ", projectSector=" + projectSector +
                ", projectRegions=" + projectRegions +
                ", projectContacts=" + projectContacts +
                ", projectDonors=" + projectDonors +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("title cannot be null.");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null.");
        }
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate cannot be null.");
        }
        this.startDate = startDate;
        this.duration = getDatesDifference(this.startDate, this.endDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("endDate cannot be null.");
        }
        this.endDate = endDate;
        this.duration = getDatesDifference(this.startDate, this.endDate);
    }

    public Currency getValueType() {
        return valueType;
    }

    public void setValueType(Currency valueType) {
        if (valueType == null) {
            throw new IllegalArgumentException("valueType cannot be null.");
        }
        this.valueType = valueType;
    }

    public Sector getProjectSector() {
        return projectSector;
    }

    public void setProjectSector(Sector projectSector) {
        if (projectSector == null) {
            this.projectSector = new Sector();
        } else {
            this.projectSector = projectSector;
        }
    }

    public ArrayList<Region> getProjectRegions() {
        return projectRegions;
    }

    public void setProjectRegions(ArrayList<Region> projectRegions) {
        if (projectRegions == null) {
            this.projectRegions = new ArrayList<Region>();
        } else {
            this.projectRegions = projectRegions;
        }
    }

    public ArrayList<Contact> getProjectContacts() {
        return projectContacts;
    }

    public void setProjectContacts(ArrayList<Contact> projectContacts) {
        if (projectContacts == null) {
            this.projectContacts = new ArrayList<Contact>();
        } else {
            this.projectContacts = projectContacts;
        }
    }

    public ArrayList<Donor> getProjectDonors() {
        return projectDonors;
    }

    public void setProjectDonors(ArrayList<Donor> projectDonors) {
        if (projectDonors == null) {
            this.projectDonors = new ArrayList<Donor>();
        } else {
            this.projectDonors = projectDonors;
        }
    }
}
