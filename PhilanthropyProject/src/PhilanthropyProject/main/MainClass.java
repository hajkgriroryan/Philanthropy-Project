package PhilanthropyProject.main;

import PhilanthropyProject.dao.Connect;
import PhilanthropyProject.project.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * naxatesvac e projecty testavorelu hamar
 */

public class MainClass {
    public static void main(String[] args) throws SQLException {

        District d1 = new District(3, "d1", 1, new BigDecimal(30));
        District d2 = new District(2, "d2", 2, new BigDecimal(50));
        if (d1.equals(d2)) {
            System.out.println("Districts are equal\n");
        }
        ArrayList<District> districtArray = new ArrayList<>();
        districtArray.add(d1);
        districtArray.add(d2);
        Province p1 = new Province(1, "p1", 1, districtArray);
        Province p2 = new Province(2, "p2", 2, districtArray);
        if (p1.equals(p2)) {
            System.out.println("Provinces are equal\n");
        }
        ArrayList<Province> provinceArray = new ArrayList<>();
        provinceArray.add(p1);
        provinceArray.add(p2);
        Region r1 = new Region(1, "r1", provinceArray);
        Region r2 = new Region(2, "r2", provinceArray);
        if (r1.equals(r2)) {
            System.out.println("Regions are equal\n");
        }
        if (r1.equals(r1)) {
            System.out.println("Regions are equal\n");
        }
        BigDecimal value1 = new BigDecimal(1);
        BigDecimal value2 = new BigDecimal(510);
        Currency currency1 = new Currency(1, "AMD", value1, new BigDecimal(2000));
        Currency currency2 = new Currency(2, "EVRO", value2, new BigDecimal(9900));
        if (currency1.equals(currency2)) {
            System.out.println("Currency types are equal\n");
        }
        ArrayList<Region> loc1 = new ArrayList<Region>(10);
        loc1.add(r1);
        loc1.add(r2);
        SubSector sub1 = new SubSector(1, "sub1", 1);
        SubSector sub2 = new SubSector(2, "sub2", 2);

        Sector sec1 = new Sector(1, "sec1", sub1);
        Sector sec2 = new Sector(2, "sec2", sub2);
        if (sec1.equals(sec2)) {
            System.out.println("ProjectSectors are equal\n");
        }

        Contact cont1 = new Contact(1, "Manuk", "Manukyan", new Organisation(1, "Gazprom"), "Man@mail", "077-78-76-98");

        ArrayList<Contact> contactArray = new ArrayList<Contact>(1);
        contactArray.add(cont1);

        Donor don1 = new Donor(1, "Don1");
        Donor don2 = new Donor(2, "Don2");
        ArrayList<Donor> donArray = new ArrayList<Donor>();
        donArray.add(don1);
        donArray.add(don2);

        Project pro1 = new Project(1, "p1", "description1", new Date(999999), new Date(),
                currency1, sec1, contactArray, loc1, donArray);

        Project pro2 = new Project(2, "p2", "description2", new Date(56754), new Date(),
                currency2, sec2, null, null, null);
        if (pro1.equals(pro2)) {
            System.out.println("Projects are equal\n");
        }
        System.out.println(pro1.getId() + "\n");

        Connect myDbTest = new Connect();

        List<Project> projects = myDbTest.loadProjects();
        for (int i = 0; i < projects.size(); ++i) {
            System.out.println(projects.get(i).toString());
        }

        Organisation org = new Organisation(1, "Org1");
        myDbTest.updateContact(2, "Poxosss", "Poxosyan", org, "PoxosPoxosyan@gmail.com", "093-567-832");

        myDbTest.deleteAmountTypeById(5);

        myDbTest.updateProject(2, "Project1", "Descr1", new Date(567), new Date(),
                pro1.getDuration(), new BigDecimal(100000), 1, 1);

        List<Sector> sectors = myDbTest.loadSectors(1);
        for (int i = 0; i < sectors.size(); ++i) {
            System.out.println(sectors.get(i).toString());
        }

        myDbTest.save(pro1, contactArray, districtArray, donArray);

    }
}
