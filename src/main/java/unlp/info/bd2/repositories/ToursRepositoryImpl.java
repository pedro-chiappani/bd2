package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;

import java.util.Date;
import java.util.List;

public class ToursRepositoryImpl implements ToursRepository {

    @Override
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) {
        //Imagino que aca hay que agregarle la persistencia y luego devolverlo
        User usu = new User(username, password, fullName, email, birthdate, phoneNumber);
        return usu;
    }

    @Override
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) {
        DriverUser driver = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
        return driver;
    }

    @Override
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) {
        TourGuideUser tourGuide = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
        return tourGuide;
    }

    @Override
    public Stop createStop(String name, String description) {
        Stop parada = new Stop(name, description);
        return parada;
    }

    @Override
    public Route createRoute(String name, float price, float totalKM, int maxNumberUsers, List<Stop> stops) {
        Route route = new Route(name, price, totalKM, maxNumberUsers, stops);
        //Persistir
        return route;
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
        //persistir
        return supplier;
    }

    @Override
    public Purchase createPurchase(Purchase purchase) {
        //persistir
        return purchase;
    }

}
