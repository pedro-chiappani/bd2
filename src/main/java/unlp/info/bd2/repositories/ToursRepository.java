package unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;

import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;

public interface ToursRepository {

    List<User> getAllPurchasesOfUsername(String username);
    List<User> getUsersSpendingMoreThan(int mount);
    List<Supplier> getTopNSuppliersInPurchase(int cant);
    List<Purchase> getTop10MoreExpensivePurchasesInServices();
    List<User> getTop5UsersMorePurchases();
    long getCountOfPurchasesBetweenDates(Date fecha1, Date fecha2);
    List<Route> getRoutesWithStop(Route route);
    Long getMaxStopOfRoutes();
    List<Route> getRoutesNotSell();
    List<Route> getTop3RoutesWithMaxRating();
    Service getMostDemandedService();
    List<Service> getServiceNoAddedToPurchases();
    List<TourGuideUser> getTourGuidesWithRating1();

}
