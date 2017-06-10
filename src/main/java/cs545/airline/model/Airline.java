package cs545.airline.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "Airline_Name", columnNames = {"name"}))
public class Airline {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL)
    @OrderBy("departureDate, departureTime")
    private List<Flight> flights = new ArrayList<>();

    /* Constructors */
    public Airline() {
    }

    public Airline(String name) {
        this.name = name;
    }

    /* Getters & Setters */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

    /* Collections Methods */
    public boolean addFlight(Flight flight) {
        boolean success = (!flights.contains(flight)) && (flights.add(flight));
        if (success) {
            flight.setAirline(this);
        }
        return success;
    }

    public boolean removeFlight(Flight flight) {
        boolean success = false;
        if (flights.remove(flight)) {
            flight.setAirline(null);
            success = true;
        }
        return success;
    }
}
