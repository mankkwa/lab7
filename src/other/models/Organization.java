package other.models;


import java.time.ZonedDateTime;

public class Organization implements Comparable<Organization>{
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    private String fullName; //Длина строки не должна быть больше 1637, Строка не может быть пустой, Поле может быть null
    private Integer employeesCount; //Поле не может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле не может быть null


    public Organization(String name, Coordinates coordinates, Float annualTurnover, String fullName, Integer employeesCount, OrganizationType type, Address postalAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    public Organization() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(Float annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Override
    public String toString() {
        return "\nid=" + id +
                ",\nname = " + name +
                ",\ncoordinates = " + coordinates +
                ",\ncreationDate = " + creationDate +
                ",\nannualTurnover = " + annualTurnover +
                ",\nfullName = " + fullName +
                ",\nemployeesCount = " + employeesCount +
                ",\ntype = " + type +
                ",\npostalAddress = " + postalAddress +
                '\n';
    }


    /**
    Метод для сравнения элементов коллекции
     принцип прост - сравниваем this и other и возвращаем результат!
     */

    @Override
    public int compareTo(Organization other) {

        if (other == null){
            return -1;
        }

        int nameComparison = this.name.compareTo(other.name);
        if (nameComparison != 0) {
            return nameComparison;
        }

        int coordinatesComparison = this.coordinates.compareTo(other.coordinates);
        if (coordinatesComparison != 0){
            return coordinatesComparison;
        }

        int annualTurnoverComparison = Float.compare(this.annualTurnover, other.annualTurnover);
        if (annualTurnoverComparison != 0) {
            return annualTurnoverComparison;
        }

        int fullComparison = this.fullName.compareTo(other.fullName);
        if (fullComparison != 0){
            return fullComparison;
        }

        int employeesCountComparison = Integer.compare(this.employeesCount, other.employeesCount);
        if (employeesCountComparison != 0){
            return employeesCountComparison;
        }

        int typeComparison = this.type.compareTo(other.type);
        if (typeComparison != 0){
            return typeComparison;
        }

        return this.postalAddress.compareTo(other.postalAddress);
    }


}
