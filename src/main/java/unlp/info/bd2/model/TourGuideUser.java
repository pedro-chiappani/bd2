package unlp.info.bd2.model;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tour_guides")
public class TourGuideUser extends User {

    private String education;

    @ManyToMany(mappedBy = "tourGuideList")
    private List<Route> routes;

    public TourGuideUser() {}
    public TourGuideUser(String username, String password, String name, String email, Date birthdate, String phoneNumber,String education) {
        super(username, password, name, email, birthdate, phoneNumber);
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
