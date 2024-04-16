package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;

import java.util.Date;
import java.util.List;

public interface ToursRepository {

    User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber);
    DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient);
    TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education);
    Stop createStop(String name, String description);
    Route createRoute(String name, float price, float totalKM, int maxNumberUsers, List<Stop> stops);
    Supplier createSupplier(Supplier supplier);
    Purchase createPurchase(Purchase purchase);
}


