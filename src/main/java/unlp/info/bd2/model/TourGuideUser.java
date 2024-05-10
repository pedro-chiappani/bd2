package unlp.info.bd2.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("TOURGUIDE")
public class TourGuideUser extends User {

    @Column(name = "education", length = 70)
    private String education;

    @ManyToMany(mappedBy = "tourGuideList", fetch = FetchType.LAZY)
    private List<Route> routes;

    public TourGuideUser() {}
    public TourGuideUser(String username, String password, String name, String email, Date birthdate, String phoneNumber,String education) {
        super(username, password, name, email, birthdate, phoneNumber);
        this.education = education;
        this.routes = new ArrayList<>();
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

    public void addRoute(Route route) {
        this.routes.add(route);
    }

    public boolean hasRoutes(){
        return !this.routes.isEmpty();
    }


}
