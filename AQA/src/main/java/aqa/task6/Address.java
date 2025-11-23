package aqa.task6;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table()

public class Address implements Serializable {
    private String city;
    private String state;
    private int zip;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    public Address() {}

    public Address( String city, String state, int zip) {
        this.city = city;
        this.state = state;
        this.zip = zip;
    }


    public void setCity(String city) { this.city = city; }
    public String getCity() {return city;}
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public int getZip() { return zip; }
    public void setZip(int zip) { this.zip = zip; }
    public Integer getId() {return id;}

    @Override
    public String toString() {
        return city + ", " + state + " " + zip;
    }
}
