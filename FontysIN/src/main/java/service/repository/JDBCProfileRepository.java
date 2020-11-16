package service.repository;

import service.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JDBCProfileRepository extends JDBCRepository {

    public List<Profile> getProfile(int givenUserId) throws DatabaseException, SQLException {
        List<Profile> foundProfiles = new ArrayList<>();


        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM profiles where userId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        try {
            statement.setInt(1, givenUserId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String language = resultSet.getString("language");


                Profile e = new Profile(id, userId, language);
                foundProfiles.add(e);
            }
            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read profiles from the database.",throwable);
        }
        finally {
            statement.close();
            connection.close();
        }
        return foundProfiles;
    }

    public List<Experience> getExperiences(int userId, int givenProfileId) throws DatabaseException, SQLException {
        List<Experience> foundExperiences = new ArrayList<>();
        for (Profile p: getProfile(userId)) {
            if (p.getUserId() == userId && p.getId() == givenProfileId) {
                Connection connection = this.getDatabaseConnection();
                String sql = "SELECT * FROM experiences where profileId = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                try {
                    statement.setInt(1, givenProfileId);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        int profileId = resultSet.getInt("profileId");
                        String title = resultSet.getString("title");
                        String company = resultSet.getString("company");
                        String location = resultSet.getString("location");
                        String emplymentType = resultSet.getString("employmentType");
                        int startDate = resultSet.getInt("startDate");
                        int endDate = resultSet.getInt("endDate");
                        String description = resultSet.getString("description");
                        EmplymentType r = EmplymentType.FullTime;
                        if (emplymentType == "FullTime")
                        {
                            r = EmplymentType.FullTime;
                        }
                        else if (emplymentType == "PartTime")
                        {
                            r = EmplymentType.PartTime;
                        }
                        else  if (emplymentType == "FullTime")
                        {
                            r = EmplymentType.PartTime;
                        }

                        Experience e = new Experience(id, profileId, title, company, r, location,  startDate, endDate, description);
                        foundExperiences.add(e);
                    }
                    connection.close();

                } catch (SQLException throwable) {
                    throw new DatabaseException("Cannot read students from the database.",throwable);
                }
                finally {
                    statement.close();
                    connection.close();
                }
                return foundExperiences;
            }
        }
        return null;
    }
    public Experience getExperienceById(int expId) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM experiences WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, expId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                connection.close();
                throw new DatabaseException("Experience with id " + expId + " cannot be found");
            } else {
                int Id = resultSet.getInt("id");
                int profileId = resultSet.getInt("profileId");
                String title = resultSet.getString("title");
                String company = resultSet.getString("company");
                String 	location= resultSet.getString("location");
                String content = resultSet.getString("title");
                String empType = resultSet.getString("employmentType");
                int startDate = resultSet.getInt("startDate");
                int endDate = resultSet.getInt("endDate");
                String description = resultSet.getString("description");
                EmplymentType r = EmplymentType.FullTime;
                if (empType == "FullTime")
                {
                    r = EmplymentType.FullTime;
                }
                else if (empType == "PartTime")
                {
                    r = EmplymentType.PartTime;
                }
                else  if (empType == "FreeLancer")
                {
                    r = EmplymentType.FreeLancer;
                }
                Experience e = new Experience(Id, profileId, title, company, r, location,  startDate, endDate, description);
                connection.close();
                return e;
            }
        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read products from the database.",throwable);
        }
    }
    public boolean updateExperience(Experience ex) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "UPDATE `experiences` SET `title`=?,`company`=?,`location`=?,`employmentType`=?,`startDate`=?,`endDate`=?,`description`=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, ex.getTitle());
            statement.setString(2, ex.getCompany());
            statement.setString(3, ex.getLocation());
            statement.setString(4, String.valueOf(ex.getEmploymentType()));
            statement.setInt(5, ex.getStartDateExperience());
            statement.setInt(6, ex.getEndDateExperience());
            statement.setString(7, ex.getDescriptionExperience());
            statement.setInt(8, ex.getId());
            statement.executeUpdate();
            connection.commit();
            connection.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Education> getEducations(int userId, int givenProfileId) throws DatabaseException, SQLException {
        List<Education> foundEducations = new ArrayList<>();

        for (Profile p: getProfile(userId)) {
            if (p.getUserId() == userId && p.getId() == givenProfileId) {
                Connection connection = this.getDatabaseConnection();
                String sql = "SELECT * FROM educations where profileId = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                try {
                    statement.setInt(1, givenProfileId);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        int profileId = resultSet.getInt("profileId");
                        String school = resultSet.getString("school");
                        int startYear = resultSet.getInt("startYear");
                        int endYear = resultSet.getInt("endYear");
                        String degree = resultSet.getString("degree");
                        String fieldStudy = resultSet.getString("fieldStudy");
                        String description = resultSet.getString("description");

                        Education e = new Education(id, profileId, school, startYear, endYear, degree, fieldStudy, description);
                        foundEducations.add(e);
                    }
                    connection.close();

                } catch (SQLException throwable) {
                    throw new DatabaseException("Cannot read students from the database.", throwable);
                } finally {
                    statement.close();
                    connection.close();
                }
                return foundEducations;
            }
        }
        return null;
    }
    public Education getEducationById(int eduId) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM educations WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, eduId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                connection.close();
                throw new DatabaseException("Experience with id " + eduId + " cannot be found");
            } else {
                int id = resultSet.getInt("id");
                int profileId = resultSet.getInt("profileId");
                String school = resultSet.getString("school");
                int startYear = resultSet.getInt("startYear");
                int endYear = resultSet.getInt("endYear");
                String degree = resultSet.getString("degree");
                String fieldStudy = resultSet.getString("fieldStudy");
                String description = resultSet.getString("description");

                Education e = new Education(id, profileId, school, startYear, endYear, degree, fieldStudy, description);
                connection.close();
                return e;
            }
        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read products from the database.",throwable);
        }
    }
    public boolean updateEducation(Education education) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "UPDATE `educations` SET `school`=?,`startYear`=?,`endYear`=?,`degree`=?,`fieldStudy`=?,`description`=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);


            statement.setString(1, education.getSchool());
            statement.setInt(2, education.getStartYearEducation());
            statement.setInt(3, education.getEndYearEducation());
            statement.setString(4,  education.getDegreeEducation());
            statement.setString(5,  education.getFieldStudy());
            statement.setString(6,  education.getDescriptionEducation());
            statement.setInt(7, education.getId());

            statement.executeUpdate();
            connection.commit();
            connection.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<About> getAbout(int userId, int givenProfileId) throws DatabaseException, SQLException {
        List<About> foundAbout = new ArrayList<>();

        for (Profile p: getProfile(userId)) {
            if (p.getUserId() == userId && p.getId() == givenProfileId) {
                Connection connection = this.getDatabaseConnection();
                String sql = "SELECT * FROM about where profileId = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                try {
                    statement.setInt(1, givenProfileId);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        int profileId = resultSet.getInt("profileId");
                        String content = resultSet.getString("content");

                        About e = new About(id, profileId, content);
                        foundAbout.add(e);
                    }
                    connection.close();

                } catch (SQLException throwable) {
                    throw new DatabaseException("Cannot read students from the database.", throwable);
                } finally {
                    statement.close();
                    connection.close();
                }
                return foundAbout;
            }
        }
        return null;
    }
    public About getAboutById(int aboId) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM about WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, aboId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                connection.close();
                throw new DatabaseException("Experience with id " + aboId + " cannot be found");
            } else {
                int id = resultSet.getInt("id");
                int profileId = resultSet.getInt("profileId");
                String content = resultSet.getString("content");

                About e = new About(id, profileId, content);
                connection.close();
                return e;
            }
        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read products from the database.",throwable);
        }
    }

    public boolean updateAbout(About about) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "UPDATE `about` SET `content`=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, about.getContent());
            statement.setInt(2, about.getId());



            statement.executeUpdate();
            connection.commit();
            connection.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Skill> getSkills(int userId, int givenProfileId) throws DatabaseException, SQLException {
        List<Skill> foundSkill = new ArrayList<>();

        for (Profile p: getProfile(userId)) {
            if (p.getUserId() == userId && p.getId() == givenProfileId) {
                Connection connection = this.getDatabaseConnection();
                String sql = "SELECT * FROM skills where profileId = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                try {
                    statement.setInt(1, givenProfileId);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        int profileId = resultSet.getInt("profileId");
                        String name = resultSet.getString("name");

                        Skill e = new Skill(id, profileId, name);
                        foundSkill.add(e);
                    }
                    connection.close();

                } catch (SQLException throwable) {
                    throw new DatabaseException("Cannot read students from the database.", throwable);
                } finally {
                    statement.close();
                    connection.close();
                }
                return foundSkill;
            }
        }
        return null;
    }

    public List<User> getUsers() throws DatabaseException, SQLException {
        List<User> allUsers = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM users";
        PreparedStatement statement = connection.prepareStatement(sql);
        try {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String userType = resultSet.getString("userType");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNr");
                int addressId = resultSet.getInt("addressId");
                String image = resultSet.getString("image");
                int locationId = resultSet.getInt("locationId");
                int departmentId = resultSet.getInt("departmentId");
                String userNumber = resultSet.getString("userNumber");
                UserType r = UserType.Teacher;
                if (userType == "student")
                {
                    r = UserType.Student;
                }
                else if (userType == "employee")
                {
                    r = UserType.Teacher;
                }
                else  if (userType == "admin")
                {
                    r = UserType.FontysStaff;
                }

                User u = new User(id, firstName, lastName, r, email, password, phoneNumber, addressId, locationId, departmentId,  userNumber);
                allUsers.add(u);

            }
            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read data from the database.", throwable);
        }
//        finally {
//            statement.close();
//            connection.close();
//        }
        return allUsers;
    }

    public User getUser(int userId) throws DatabaseException, SQLException {

        for (User u: getUsers()) {
            if (u.getId() == userId) {
                return u;
            }
        }
        return null;
    }

    public boolean createExperience(Experience experience) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();

        Boolean exist;
        exist = false;

        String sql = "INSERT INTO experiences ( profileId, title, company, location, startDate, endDate, description) VALUES (?,?,?,?,?,?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
                preparedStatement.setInt(1, experience.getProfileId());
                preparedStatement.setString(2, experience.getTitle());
                preparedStatement.setString(3, experience.getCompany());
                preparedStatement.setString(4, experience.getLocation());
                preparedStatement.setInt(5,  experience.getStartDateExperience());
                preparedStatement.setInt(6,  experience.getEndDateExperience());
                preparedStatement.setString(7,  experience.getDescriptionExperience());
                preparedStatement.executeUpdate();

                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, "value");
                connection.setAutoCommit(false);
                ps.close();
                connection.commit();
                connection.close();

                return true;

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new experience.", throwable);
        }
        finally {
            preparedStatement.close();
            connection.close();
        }

    }

    public boolean createEducation(Education education) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();

        Boolean exist;
        exist = false;

        String sql = "INSERT INTO educations ( profileId, school, startYear, endYear, degree, fieldStudy, description) VALUES (?,?,?,?,?,?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
            preparedStatement.setInt(1, education.getProfileId());
            preparedStatement.setString(2, education.getSchool());
            preparedStatement.setInt(3, education.getStartYearEducation());
            preparedStatement.setInt(4, education.getEndYearEducation());
            preparedStatement.setString(5,  education.getDegreeEducation());
            preparedStatement.setString(6,  education.getFieldStudy());
            preparedStatement.setString(7,  education.getDescriptionEducation());
            preparedStatement.executeUpdate();

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "value");
            connection.setAutoCommit(false);
            ps.close();
            connection.commit();
            connection.close();

            return true;

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new experience.", throwable);
        }
        finally {
            preparedStatement.close();
            connection.close();
        }
    }

    public boolean createSkill(Skill skill, int userId) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();

        for (Skill p: getSkills(userId, skill.getProfileId())) {
          if(p.getId() == skill.getProfileId()){
              if (p.getName().equals(skill.getName())) {
                  return false;
              }
          }
        }
        String sql = "INSERT INTO skills ( profileId, name) VALUES (?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
            preparedStatement.setInt(1, skill.getProfileId());
            preparedStatement.setString(2, skill.getName());

            preparedStatement.executeUpdate();

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "value");
            connection.setAutoCommit(false);
            ps.close();
            connection.commit();
            connection.close();

            return true;

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new experience.", throwable);
        }
        finally {
            preparedStatement.close();
            connection.close();
        }
    }


    public int createProfile(Profile newProfile, int userId) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();
        int id = 0;
        boolean exist;
        exist = false;
        String generatedColumns[] = { "ID" };

        for (Profile p: getProfile(userId)) {
            if (p.getUserId() == userId) {
                if (p.getLanguage().equals(newProfile.getLanguage())) {
                   exist = true;
                    return 0;
                }
            }
        }
        ResultSet rs = null;
        if(!exist) {
            String sql = "INSERT INTO profiles (userId, language) VALUES (?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            try {
                preparedStatement.setInt(1, newProfile.getUserId());
                preparedStatement.setString(2, newProfile.getLanguage());

                preparedStatement.executeUpdate();

                rs = preparedStatement.getGeneratedKeys();
                if(rs != null && rs.next()){
                    System.out.println("Generated Emp Id: "+rs.getInt(1));
                    id = rs.getInt(1);
                }
                
//                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, "id");
                connection.setAutoCommit(false);
//                ps.close();
                connection.commit();
                connection.close();
                return id;


            } catch (SQLException throwable) {
                throw new DatabaseException("Cannot create new profile.", throwable);
            } finally {
                preparedStatement.close();
                connection.close();
            }
        }
        return 0;
    }

    public boolean createAbout(About about) throws DatabaseException, SQLException {
        Connection connection = this.getDatabaseConnection();

        String sql = "INSERT INTO about ( profileId, content) VALUES (?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
            preparedStatement.setInt(1, about.getProfileId());
            preparedStatement.setString(2, about.getContent());

            preparedStatement.executeUpdate();

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "value");
            connection.setAutoCommit(false);
            ps.close();
            connection.commit();
            connection.close();

            return true;

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot create new about.", throwable);
        }
        finally {
            preparedStatement.close();
            connection.close();
        }
    }

    public Address getAddressById(int aId) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM addresses WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, aId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                connection.close();
                throw new DatabaseException("Experience with id " + aId + " cannot be found");
            } else {
                int id = resultSet.getInt("id");
                String 	streetName = resultSet.getString("streetName");
                String houseNumber = resultSet.getString("houseNumber");
                String city = resultSet.getString("city");
                String zipcode = resultSet.getString("zipcode");

                Address a = new Address(id, streetName,houseNumber,city,zipcode);
                connection.close();
                return a;
            }
        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read products from the database.",throwable);
        }
    }
    public boolean updateAddress(Address a) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "UPDATE `addresses` SET `streetName`=?,`houseNumber`=?,`city`=?,`zipcode`=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(5, a.getId());
            statement.setString(1, a.getStreetName());
            statement.setString(2, a.getHouseNumber());
            statement.setString(3, a.getCity());
            statement.setString(4, a.getZipCode());

            statement.executeUpdate();
            connection.commit();
            connection.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public boolean updatePhone(User u) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "UPDATE `users` SET `phoneNr`=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, u.getPhoneNumber());
            statement.setInt(2, u.getId());



            statement.executeUpdate();
            connection.commit();
            connection.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    // ----------------------------------------- Privacy

    public boolean createPrivacy(Privacy p) throws DatabaseException{
        Connection connection = this.getDatabaseConnection();
        boolean exist = false;
        String sql = "INSERT INTO privacy(`userId`,`educationSetting`, `experienceSetting`, `skillSetting`) VALUES (?,?,?,?)";
        try {
            if(!exist){
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setInt(1, p.getUserId());
                statement.setString(2,p.getEducationSetting().toString());
                statement.setString(3,p.getExperienceSetting().toString());
                statement.setString(4,p.getSkillSetting().toString());

                statement.executeUpdate();

                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1,1);
                connection.setAutoCommit(false);
                connection.commit();
                connection.close();
                return true;
            } else  {
                connection.close();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Privacy> getPrivacyList() throws DatabaseException {
        List<Privacy> privacyList = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM privacy";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String educationSetting = resultSet.getString("educationSetting");
                String experienceSetting = resultSet.getString("experienceSetting");
                String skillSetting = resultSet.getString("skillSetting");

                Privacy.Setting edu = Privacy.Setting.EVERYONE;
                if (educationSetting.equals("EVERYONE"))
                {
                    edu = Privacy.Setting.EVERYONE;
                }
                else if (educationSetting.equals("CONNECTIONS"))
                {
                    edu = Privacy.Setting.CONNECTIONS;
                }
                else  if (educationSetting.equals("ONLYME"))
                {
                    edu = Privacy.Setting.ONLYME;
                }
                Privacy.Setting exp = Privacy.Setting.EVERYONE;
                if (experienceSetting.equals("EVERYONE"))
                {
                    exp = Privacy.Setting.EVERYONE;
                }
                else if (experienceSetting.equals("CONNECTIONS"))
                {
                    exp = Privacy.Setting.CONNECTIONS;
                }
                else  if (experienceSetting.equals("ONLYME"))
                {
                    exp = Privacy.Setting.ONLYME;
                }
                Privacy.Setting ski = Privacy.Setting.EVERYONE;
                if (skillSetting.equals( "EVERYONE"))
                {
                    ski = Privacy.Setting.EVERYONE;
                }
                else if (skillSetting.equals("CONNECTIONS"))
                {
                    ski = Privacy.Setting.CONNECTIONS;
                }
                else  if (skillSetting.equals("ONLYME"))
                {
                    ski = Privacy.Setting.ONLYME;
                }

                Privacy a = new Privacy(id, userId, edu, exp, ski);
                privacyList.add(a);
            }
            connection.setAutoCommit(false);
            connection.close();

        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read comments from the database.",throwable);
        }
        return privacyList;
    }

    public Privacy getPrivacyById(int pId) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM privacy WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, pId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                connection.close();
                throw new DatabaseException("Privacy with id " + pId + " cannot be found");
            } else {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                String educationSetting = resultSet.getString("educationSetting");
                String experienceSetting = resultSet.getString("experienceSetting");
                String skillSetting = resultSet.getString("skillSetting");

                Privacy.Setting edu = Privacy.Setting.EVERYONE;
                if (educationSetting.equals("EVERYONE"))
                {
                    edu = Privacy.Setting.EVERYONE;
                }
                else if (educationSetting.equals("CONNECTIONS"))
                {
                    edu = Privacy.Setting.CONNECTIONS;
                }
                else  if (educationSetting.equals("ONLYME"))
                {
                    edu = Privacy.Setting.ONLYME;
                }
                Privacy.Setting exp = Privacy.Setting.EVERYONE;
                if (experienceSetting.equals("EVERYONE"))
                {
                    exp = Privacy.Setting.EVERYONE;
                }
                else if (experienceSetting.equals("CONNECTIONS"))
                {
                    exp = Privacy.Setting.CONNECTIONS;
                }
                else  if (experienceSetting.equals("ONLYME"))
                {
                    exp = Privacy.Setting.ONLYME;
                }
                Privacy.Setting ski = Privacy.Setting.EVERYONE;
                if (skillSetting.equals( "EVERYONE"))
                {
                    ski = Privacy.Setting.EVERYONE;
                }
                else if (skillSetting.equals("CONNECTIONS"))
                {
                    ski = Privacy.Setting.CONNECTIONS;
                }
                else  if (skillSetting.equals("ONLYME"))
                {
                    ski = Privacy.Setting.ONLYME;
                }

                Privacy a = new Privacy(id, userId, edu, exp, ski);
                connection.close();
                return a;
            }
        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read products from the database.",throwable);
        }
    }
    public boolean updatePrivacy(Privacy a) throws DatabaseException {
        Connection connection = this.getDatabaseConnection();
        String sql = "UPDATE `privacy` SET `educationSetting`=?,`experienceSetting`=?,`skillSetting`=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(4, a.getId());
            statement.setString(1, String.valueOf(a.getEducationSetting()));
            statement.setString(2, String.valueOf(a.getExperienceSetting()));
            statement.setString(3, String.valueOf(a.getSkillSetting()));

            statement.executeUpdate();
            connection.commit();
            connection.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    // ----------------------------------------- Privacy ^
    /******************Ranim******************Deleting data in profile page*********************/

    // Delete education in profile page
    public void deleteEducation(int userId, int profileId, int educationId) throws DatabaseException {

        Connection connection = this.getDatabaseConnection();

        String sql = "Delete FROM educations where id = ? AND profileId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,educationId);
            preparedStatement.setInt(2,profileId);

            preparedStatement.executeUpdate();
            connection.commit();
        }
        catch (SQLException throwable){
            throw  new DatabaseException("Cannot delete education.", throwable);
        }

    }

    // Delete experience in profile page
    public void deleteExperience(int userId, int profileId, int experienceId) throws DatabaseException {

        Connection connection = this.getDatabaseConnection();

        String sql = "Delete FROM experiences where id = ? AND profileId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,experienceId);
            preparedStatement.setInt(2,profileId);

            preparedStatement.executeUpdate();
            connection.commit();
        }
        catch (SQLException throwable){
            throw  new DatabaseException("Cannot delete experience.", throwable);
        }

    }

    // Delete skill in profile page
    public void deleteSkill(int userId, int profileId, int skillId) throws DatabaseException {

        Connection connection = this.getDatabaseConnection();

        String sql = "Delete FROM skills where id = ? AND profileId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,skillId);
            preparedStatement.setInt(2,profileId);

            preparedStatement.executeUpdate();
            connection.commit();
        }
        catch (SQLException throwable){
            throw  new DatabaseException("Cannot delete skill.", throwable);
        }

    }

    /**************Ranim******************************Filter users**************************/

    //get all users with the given user type from data base
    public List<User> getUsersByType(UserType type) throws DatabaseException {

        List<User> filtered = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();

        String sql = "SELECT * FROM users WHERE userType = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type.name()); // set user type parameter
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                UserType userType = UserType.valueOf(resultSet.getString("userType"));
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNr");
                int addressId = resultSet.getInt("addressId");
                String image = resultSet.getString("image");
                int locationId = resultSet.getInt("locationId");
                int departmentId = resultSet.getInt("departmentId");
                String userNumber = resultSet.getString("userNumber");

                User u = new User(id, firstName, lastName, userType, email, password, phoneNumber, addressId, locationId, departmentId,  userNumber);
                filtered.add(u);

            }


        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read users from the database.",throwable);
        }
        return filtered;
    }

    //get all users with the given location id from data base
    public List<User> getUsersByLocation(int lId) throws DatabaseException {

        List<User> filtered = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();

        String sql = "SELECT * FROM users WHERE locationId = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lId); // set user fontys location parameter
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                UserType userType = UserType.valueOf(resultSet.getString("userType"));
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNr");
                int addressId = resultSet.getInt("addressId");
                String image = resultSet.getString("image");
                int locationId = resultSet.getInt("locationId");
                int departmentId = resultSet.getInt("departmentId");
                String userNumber = resultSet.getString("userNumber");

                User u = new User(id, firstName, lastName, userType, email, password, phoneNumber, addressId, locationId, departmentId,  userNumber);
                filtered.add(u);

            }


        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read users from the database.",throwable);
        }
        return filtered;
    }

    //get all users with the given department id from data base
    public List<User> getUsersByDepartment(int bId) throws DatabaseException {

        List<User> filtered = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();

        String sql = "SELECT * FROM users WHERE departmentId = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bId); // set user fontys location parameter
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                UserType userType = UserType.valueOf(resultSet.getString("userType"));
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNr");
                int addressId = resultSet.getInt("addressId");
                String image = resultSet.getString("image");
                int locationId = resultSet.getInt("locationId");
                int departmentId = resultSet.getInt("departmentId");
                String userNumber = resultSet.getString("userNumber");

                User u = new User(id, firstName, lastName, userType, email, password, phoneNumber, addressId, locationId, departmentId,  userNumber);
                filtered.add(u);

            }


        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read users from the database.",throwable);
        }
        return filtered;
    }

    //get all users with the given start study year from data base
    public List<User> getUsersByStartStudyYear(int year) throws DatabaseException {

        List<User> filtered = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();

        String sql = "SELECT users.id, users.firstName, users.lastName, users.userType, users.email, users.password," +
                "users.phoneNr, users.addressId, users.image, users.locationId, users.departmentId, users.userNumber FROM ((educations INNER JOIN profiles " +
                "ON educations.profileId = profiles.id) INNER JOIN users ON profiles.userId = users.id) WHERE school = 'Fontys'" +
                "AND startYear = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, year); // set user start study year parameter
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                UserType userType = UserType.valueOf(resultSet.getString("userType"));
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNr");
                int addressId = resultSet.getInt("addressId");
                String image = resultSet.getString("image");
                int locationId = resultSet.getInt("locationId");
                int departmentId = resultSet.getInt("departmentId");
                String userNumber = resultSet.getString("userNumber");

                User u = new User(id, firstName, lastName, userType, email, password, phoneNumber, addressId, locationId, departmentId,  userNumber);
                filtered.add(u);

            }


        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read users from the database.",throwable);
        }
        return filtered;
    }

    //get all users with the given start work year from data base
    public List<User> getUsersByStartWorkYear(int year) throws DatabaseException {

        List<User> filtered = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();

        String sql = "SELECT users.id, users.firstName, users.lastName, users.userType, users.email, users.password," +
                "users.phoneNr, users.addressId, users.image, users.locationId, users.departmentId, users.userNumber " +
                "FROM ((experiences INNER JOIN profiles ON experiences.profileId = profiles.id) INNER JOIN users ON profiles.userId = users.id) " +
                "WHERE company = 'Fontys' AND users.userType != 'Student' AND startDate = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, year); // set user start study year parameter
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                UserType userType = UserType.valueOf(resultSet.getString("userType"));
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNr");
                int addressId = resultSet.getInt("addressId");
                String image = resultSet.getString("image");
                int locationId = resultSet.getInt("locationId");
                int departmentId = resultSet.getInt("departmentId");
                String userNumber = resultSet.getString("userNumber");

                User u = new User(id, firstName, lastName, userType, email, password, phoneNumber, addressId, locationId, departmentId,  userNumber);
                filtered.add(u);

            }


        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read users from the database.",throwable);
        }
        return filtered;
    }

    /******************RANIM***********************Combined Filter Search**************************/

    //get all users with the given user type, location and department from data base
    public List<User> getUsersByUserTypeAndLocationAndDepartment(UserType type, int lId, int dId) throws DatabaseException {

        List<User> filtered = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();

        String sql = "SELECT * FROM users WHERE userType = ? AND locationId = ? AND departmentId = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type.name()); // set user start study year parameter
            statement.setInt(2, lId); // set user location id parameter
            statement.setInt(3, dId); // set user department id parameter
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                UserType userType = UserType.valueOf(resultSet.getString("userType"));
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNr");
                int addressId = resultSet.getInt("addressId");
                String image = resultSet.getString("image");
                int locationId = resultSet.getInt("locationId");
                int departmentId = resultSet.getInt("departmentId");
                String userNumber = resultSet.getString("userNumber");

                User u = new User(id, firstName, lastName, userType, email, password, phoneNumber, addressId, locationId, departmentId,  userNumber);
                filtered.add(u);

            }


        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read users from the database.",throwable);
        }
        return filtered;
    }

    //get all users with the given user type, start study year, location and department from data base
    public List<User> getUsersByUserTypeAndStartStudyYearAndDepartmentAndLocation(UserType type, int year, int lId, int dId) throws DatabaseException {

        List<User> filtered = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();

        String sql = "SELECT users.id, users.firstName, users.lastName, users.userType, users.email, users.password," +
                "users.phoneNr, users.addressId, users.image, users.locationId, users.departmentId, users.userNumber " +
                "FROM ((educations INNER JOIN profiles ON educations.profileId = profiles.id) INNER JOIN users " +
                "ON profiles.userId = users.id)" +
                " WHERE school = 'Fontys' AND users.userType = ? AND users.locationId = ? " +
                "And users.departmentId = ? AND startYear = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, type.name()); // set user start study year parameter
            statement.setInt(2, lId); // set user location id parameter
            statement.setInt(3, dId); // set user department id parameter
            statement.setInt(4, year); // set user start study year parameter

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                UserType userType = UserType.valueOf(resultSet.getString("userType"));
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNr");
                int addressId = resultSet.getInt("addressId");
                String image = resultSet.getString("image");
                int locationId = resultSet.getInt("locationId");
                int departmentId = resultSet.getInt("departmentId");
                String userNumber = resultSet.getString("userNumber");

                User u = new User(id, firstName, lastName, userType, email, password, phoneNumber, addressId, locationId, departmentId,  userNumber);
                filtered.add(u);

            }


        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read users from the database.",throwable);
        }
        return filtered;
    }

    //get all users with the given user type, location and department from data base
    public List<User> getUsersByUserTypeAndStartWorkYearAndDepartmentAndLocationFontysStaff(UserType type, int year, int lId, int dId) throws DatabaseException {

        List<User> filtered = new ArrayList<>();

        Connection connection = this.getDatabaseConnection();

        String sql = "SELECT users.id, users.firstName, users.lastName, users.userType, users.email, users.password," +
                "users.phoneNr, users.addressId, users.image, users.locationId, users.departmentId, users.userNumber " +
                "FROM ((experiences INNER JOIN profiles ON experiences.profileId = profiles.id) INNER JOIN users " +
                "ON profiles.userId = users.id)" +
                " WHERE company = 'Fontys' AND users.userType = ? AND users.locationId = ? " +
                "And users.departmentId = ? AND startDate = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, type.name()); // set user start work year parameter
            statement.setInt(2, lId); // set user location id parameter
            statement.setInt(3, dId); // set user department id parameter
            statement.setInt(4, year); // set user start work year parameter

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                UserType userType = UserType.valueOf(resultSet.getString("userType"));
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phoneNr");
                int addressId = resultSet.getInt("addressId");
                String image = resultSet.getString("image");
                int locationId = resultSet.getInt("locationId");
                int departmentId = resultSet.getInt("departmentId");
                String userNumber = resultSet.getString("userNumber");

                User u = new User(id, firstName, lastName, userType, email, password, phoneNumber, addressId, locationId, departmentId,  userNumber);
                filtered.add(u);

            }


        } catch (SQLException throwable) {
            throw new DatabaseException("Cannot read users from the database.",throwable);
        }
        return filtered;
    }


}
