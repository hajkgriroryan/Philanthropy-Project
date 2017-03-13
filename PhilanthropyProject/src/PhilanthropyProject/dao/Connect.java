package PhilanthropyProject.dao;

import com.sun.istack.internal.NotNull;
import PhilanthropyProject.project.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * naxatevac e Database-i het kap stexcelu hamr,
 * uni hnaravorutyun delete, update ev load
 */
public class Connect {
    private Connection con = null;
    private final String url = "jdbc:sqlserver://";
    private final String serverName = "localhost";
    private final String portNumber = "1433";
    private final String databaseName = "philanthropyDB";
    private final String userName = "Hayk";
    private final String password = "1";
    private final String selectMethod = "cursor";

    public Connect() {
    }

    private String getConnectionUrl() {
        return "jdbc:sqlserver://localhost:1433;databaseName=philanthropyDB;selectMethod=cursor;";
    }

    private Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.con = DriverManager.getConnection(this.getConnectionUrl(), "Hayk", "1");
            if (this.con != null) {
                System.out.println("Connection Successful!");
            }
        } catch (SQLException var2) {
            var2.printStackTrace();
            System.out.println("Error Trace in getConnection() : " + var2.getMessage());
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            System.out.println("Error during load driver : " + var3.getMessage());
        }

        return this.con;
    }


//    public <T> void universalLoad(String query, List<T> returnList) {
//        try (Connection con = this.getConnection()) {
//            PreparedStatement ps = con.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                if(returnList.getClass() == (new ArrayList<Region>()).getClass()) {
//                    returnList.add(new T(rs.getInt("DistrictID"), rs.getString("DistrictsName"),
//                            rs.getInt("ProvinceID"), rs.getBigDecimal("PercentNum"));
//                }
//
//
//            }
//            rs.close();
//            rs = null;
//            closeConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * load anox funkcianern en District-i, Province-i,
     * region-i, contact-i, project- ev sector-neri
     */
    public List<District> loadDistricts(int provinceId) {
        List<District> districtList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "SELECT Districts.DistrictId, DistrictsName, ProvinceID, PercentNum FROM ProjectDistrict\n" +
                    "inner join Districts on ProjectDistrict.DistrictID = Districts.DistrictID\n" +
                    "where ProvinceID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, provinceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                District district = new District(rs.getInt("DistrictID"), rs.getString("DistrictsName"),
                        rs.getInt("ProvinceID"), rs.getBigDecimal("PercentNum"));

                districtList.add(district);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return districtList;
    }

    public List<Province> loadProvinces(int regionId) {
        List<Province> provinceList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "Select ProvinceID, ProvinceName, RegionID from Provinces\n" +
                    "where RegionID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, regionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                List<District> districtList = loadDistricts(rs.getInt("ProvinceID"));
                Province province = new Province(rs.getInt("ProvinceID"), rs.getString("ProvinceName"),
                        rs.getInt("RegionID"), (ArrayList<District>) districtList);

                provinceList.add(province);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinceList;
    }

    public List<Region> loadRegions(int projectId) {
        List<Region> regionList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "select Regions.RegionID, RegionName from ProjectDistrict\n" +
                    "inner join Project on Project.ProjectID = ProjectDistrict.ProjectID\n" +
                    "inner join Districts on ProjectDistrict.DistrictID = Districts.DistrictID\n" +
                    "inner join Provinces on Provinces.ProvinceID = Districts.ProvinceID\n" +
                    "inner join Regions on Regions.RegionID = Provinces.RegionID\n";
            PreparedStatement ps;
            if (projectId != -1) {
                sql += "where Project.ProjectID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, projectId);
            } else {
                ps = con.prepareStatement(sql);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                List<Province> provinceList = loadProvinces(rs.getInt("RegionId"));
                Region region = new Region(rs.getInt("RegionId"), rs.getString("RegionName"),
                        (ArrayList<Province>) provinceList);

                regionList.add(region);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regionList;
    }

    public List<SubSector> loadSubSectors(int SectorId) {
        List<SubSector> subSectorList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "SELECT SubSectorID, SubSectorName, Sectors.SectorID FROM SubSectors\n" +
                    "inner join Sectors on Sectors.SectorID = SubSectors.SectorID\n" +
                    "where Sectors.SectorID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, SectorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SubSector subSector = new SubSector(rs.getInt("SubSectorID"), rs.getString("SubSectorName"),
                        rs.getInt("SectorID"));

                subSectorList.add(subSector);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subSectorList;
    }

    public List<Sector> loadSectors(int projectId) {
        List<Sector> sectorList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql;
            ResultSet rs;
            PreparedStatement ps;
            if (projectId == -1) {
                sql = "Select SectorId, SectorName from Sectors";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Sector sector = new Sector(rs.getInt("SectorId"), rs.getString("SectorName"), null);

                    sectorList.add(sector);
                }
            } else {
                sql = "select Sectors.SectorID, SectorName, SubSectors.SubSectorID, SubSectorName from Project\n" +
                        "inner join SubSectors on SubSectors.SubSectorID = Project.SubSectorID\n" +
                        "inner join Sectors on Sectors.SectorID = SubSectors.SectorID";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Sector sector = new Sector(rs.getInt("SectorId"), rs.getString("SectorName"),
                            new SubSector(rs.getInt("SubSectorID"), rs.getString("SubSectorName"), rs.getInt("SectorId")));

                    sectorList.add(sector);
                }
            }

            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectorList;
    }

    public List<Contact> loadContacts(int projectId) {
        List<Contact> contactList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "Select Contacts.ContactId, FirstName, LastName, Organisation, Email, Phone from Contacts\n" +
                    "inner join ContactsProjects on ContactsProjects.ContactID = Contacts.ContactId\n" +
                    "where ProjectID = " + projectId;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact(rs.getInt("ContactId"), rs.getString("FirstName"),
                        rs.getString("LastName"), new Organisation(rs.getInt("ContactId"), rs.getString("Organisation")),
                        rs.getString("Email"), rs.getString("Phone"));
                contactList.add(contact);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public List<Donor> loadDonors(int projectID) {
        List<Donor> donorList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql;
            PreparedStatement ps;
            if (projectID == -1) {
                sql = "Select DonorID, DonorName from AvailableDonors";
                ps = con.prepareStatement(sql);
            } else {
                sql = "Select AvailableDonors.DonorID, DonorName from AvailableDonors\n" +
                        "inner join SelectedDonors on SelectedDonors.DonorID = AvailableDonors.DonorID\n" +
                        "inner join Project on Project.ProjectID = SelectedDonors.ProjectID\n" +
                        "where SelectedDonors.ProjectID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, projectID);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Donor donor = new Donor(rs.getInt("DonorID"), rs.getString("DonorName"));

                donorList.add(donor);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donorList;
    }

    public List<Project> loadProjects() {
        List<Project> projectList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "Select ProjectID, ProjectTitle, ProjectDescription, StartDate, EndDate, TotalAmount, \n" +
                    "TotalAmountTypeID, AmountName, AmountToDram, Sectors.SectorId, SectorName from Project\n" +
                    "inner join SubSectors on SubSectors.SubSectorID = Project.SubSectorID\n" +
                    "inner join Sectors on Sectors.SectorID =SubSectors.SectorID\n" +
                    "inner join AmountType on AmountType.AmountTypeID = project.TotalAmountTypeID";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Project project = new Project(rs.getInt("ProjectID"), rs.getString("ProjectTitle"),
                        rs.getString("ProjectDescription"), rs.getDate("StartDate"), rs.getDate("EndDate"),
                        new Currency(rs.getInt("TotalAmountTypeID"), rs.getString("AmountName"),
                                rs.getBigDecimal("AmountToDram"), rs.getBigDecimal("TotalAmount")),
                        new Sector(rs.getInt("SectorId"), rs.getString("SectorName"), null),
                        (ArrayList<Contact>) loadContacts(rs.getInt("ProjectID")),
                        (ArrayList<Region>) loadRegions(rs.getInt("ProjectID")),
                        (ArrayList<Donor>) loadDonors(rs.getInt("ProjectId")));

                projectList.add(project);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    /**
     * Delete ev Update funkcianery
     */
    public void deleteSectorById(int sectorId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Sectors where SectorID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sectorId);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSectorsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Sectors";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSector(int id, @NotNull String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update Sectors set SectorName = ? where SectorId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubSectorById(int subSectorId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from SubSectors where SubSectorID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, subSectorId);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubSectorsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from SubSectors";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSubSector(int id, @NotNull String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update SubSectors set SubSectorName = ? where SubSectorsId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContactById(int contactID) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Contacts where ContactID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, contactID);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContactsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Contacts";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContact(int id, @NotNull String firstName, @NotNull String lastName,
                              @NotNull Organisation organisation, @NotNull String mail, @NotNull String phone) {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(organisation);
        Objects.requireNonNull(mail);
        Objects.requireNonNull(phone);
        try (Connection con = this.getConnection()) {
            String sql = "update Contacts set FirstName = ?, lastName = ?, " +
                    "organisation = ?, Email = ?, Phone = ? where ContactID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, organisation.getName());
            ps.setString(4, mail);
            ps.setString(5, phone);
            ps.setInt(6, id);

            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAmountTypeById(int amountTypeId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from AmountType where AmountTypeId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, amountTypeId);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAmountTypesAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from AmountType";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAmountType(int id, @NotNull String amountName, @NotNull BigDecimal amountToDram) {
        try (Connection con = this.getConnection()) {
            String sql = "update AmountType set AmountName = ?, AmountToDram = ?, where AmountTypeId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            ps.setString(1, amountName);
            ps.setBigDecimal(2, amountToDram);
            ps.setInt(3, id);
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDistrictById(int districtId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Districts where DistrictID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, districtId);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDistrictsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Districts";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateDistrict(int id, @NotNull String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update Districts set DistrictsName = ? where DistrictId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(2, id);
            ps.setString(1, name);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProvinceById(int provinceId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Provinces where ProvinceID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, provinceId);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProvincesAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Provinces";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProvince(int id, @NotNull String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update Provinces set ProvinceName = ? where ProvinceId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRegionById(int regionId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Regions where RegionID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, regionId);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRegionsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Regions";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRegion(int id, @NotNull String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update Regions set RegionName = ? where RegionId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProjectById(int projectID) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Project where ProjectID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, projectID);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProjectsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Project";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProject(int id, @NotNull String projectTitle, @NotNull String projectDescription,
                              @NotNull Date startDate, @NotNull Date endDate, @NotNull String duration, @NotNull BigDecimal totalAmount,
                              int totalAmountTypeId, int subSectorId) {
        try (Connection con = this.getConnection()) {
            String sql = "update Project\nset ProjectTitle = ?, projectDescription = ?, startDate = ?, " +
                    "endDate = ?, duration = ?, totalAmount = ?, totalAmountTypeId = ?, subSectorId = ?  where ProjectId = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, projectTitle);
            ps.setString(2, projectDescription);
            ps.setDate(3, new java.sql.Date(startDate.getTime()));
            ps.setDate(4, new java.sql.Date(endDate.getTime()));
            ps.setString(5, duration);
            ps.setBigDecimal(6, totalAmount);
            ps.setInt(7, totalAmountTypeId);
            ps.setInt(8, subSectorId);
            ps.setInt(9, id);
            ps.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Contact Location, Donor, Project saves
     */
    public int saveContact(@NotNull Contact contact, int projectId, @NotNull Connection con) {
        int dummy = -1;
        try {
            con.setAutoCommit(false);
            String sql = "insert Contacts values ( ? , ? , ? , ? , ? ) ";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getOrganisation().getName());
            ps.setString(4, contact.geteMail());
            ps.setString(5, contact.getPhone());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            dummy = rs.getInt(1);
            sql = "insert ContactsProjects values(?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, dummy);
            ps.setInt(2, projectId);
            ps.executeUpdate();

            con.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dummy;
    }

    public void saveLocation(int projectId, int districtId, @NotNull BigDecimal percent, @NotNull Connection con) {
        try {
            String sql = "insert ProjectDistrict values(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, projectId);
            ps.setInt(2, districtId);
            ps.setBigDecimal(3, percent);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveDonor(int donorId, int projectId, @NotNull Connection con) {
        try {
            String sql = "insert SelectedDonors values(?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, donorId);
            ps.setInt(2, projectId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int saveProject(@NotNull Project project, @NotNull Connection con) {
        int dummy = -1;
        try {
            String sql = "insert Project values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, project.getTitle());
            ps.setString(2, project.getDescription());
            ps.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
            ps.setString(5, project.getDuration());
            ps.setBigDecimal(6, project.getValueType().getTotalAmount());
            ps.setInt(7, project.getValueType().getId());
            ps.setInt(8, project.getProjectSector().getSubSector().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            dummy = rs.getInt(1);
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dummy;
    }

    public void save(@NotNull Project project, @NotNull List<Contact> contactsList,
                     @NotNull List<District> locationList, @NotNull List<Donor> donorsList) {
        Connection con = this.getConnection();
        try {
            con.setAutoCommit(false);
            int projectId = saveProject(project, con);
            for (Contact i : contactsList) {
                saveContact(i, projectId, con);
            }
            for (District i : locationList) {
                saveLocation(projectId, i.getId(), i.getPercent(), con);
            }
            for (Donor i : donorsList) {
                saveDonor(i.getId(), projectId, con);
            }
            con.commit();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void closeConnection() {
        try {
            if (this.con != null) {
                this.con.close();
            }

            this.con = null;
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

}
