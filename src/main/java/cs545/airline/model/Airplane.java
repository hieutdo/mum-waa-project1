package cs545.airline.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"serialnr"}))
public class Airplane {

    @Id
    @GeneratedValue
    private long id;

    private String serialnr;
    private String model;
    private int capacity;
    @OneToMany(mappedBy = "airplane")
    @OrderBy("departureDate, departureTime")
    private List<Flight> flights;

    /* Constructors */
    public Airplane() {
    }

    public Airplane(String serialnr, String model, int capacity) {
        this.serialnr = serialnr;
        this.model = model;
        this.capacity = capacity;
    }

    /* Getters and Setters */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialnr() {
        return serialnr;
    }

    public void setSerialnr(String serialnr) {
        this.serialnr = serialnr;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

    /* Collection Methods */
    public boolean addFlight(Flight flight) {
        boolean success = (!flights.contains(flight)) && (flights.add(flight));
        if (success) {
            flight.setAirplane(this);
        }
        return success;
    }

    public boolean removeFlight(Flight flight) {
        boolean success = false;
        if (flights.remove(flight)) {
            flight.setAirplane(null);
            success = true;
        }
        return success;
    }
}
