package unlp.info.bd2.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("DRIVER")
public class DriverUser extends User {

    @Column(name = "expedient", length = 50)
    private String expedient;

    @ManyToMany(mappedBy = "driverList", fetch = FetchType.LAZY)
    private List<Route> routes;

    public DriverUser() {}
    public DriverUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String expedient) {
        super(username, password, name, email, birthdate, phoneNumber);
        this.expedient = expedient;
        this.routes = new ArrayList<Route>();
    }

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void addRoute(Route route) {
        this.routes.add(route);
    }

    public boolean hasRoutes(){
        return !this.routes.isEmpty();
    }
}
