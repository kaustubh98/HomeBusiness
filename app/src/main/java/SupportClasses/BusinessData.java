package SupportClasses;

public class BusinessData {

    private String Name;
    private String Description;
    private String Location;
    private String Owner;

    //empty constructor for reading data from firebase
    public BusinessData(){

    }

    public BusinessData(String name, String description, String location, String owner) {
        Name = name;
        Description = description;
        Location = location;
        Owner = owner;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }
}
