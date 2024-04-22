package unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;

import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;
import unlp.info.bd2.utils.ToursException;

public interface ToursRepository {

    List<User> getAllPurchasesOfUsername(String username) throws ToursException;
    List<User> getUsersSpendingMoreThan(int mount) throws ToursException;
    List<Supplier> getTopNSuppliersInPurchase(int cant) throws ToursException;
    List<Purchase> getTop10MoreExpensivePurchasesInServices() throws ToursException;
    List<User> getTop5UsersMorePurchases() throws ToursException;
    long getCountOfPurchasesBetweenDates(Date fecha1, Date fecha2) throws ToursException;
    List<Route> getRoutesWithStop(Route route) throws ToursException;
    Long getMaxStopOfRoutes() throws ToursException;
    List<Route> getRoutesNotSell() throws ToursException;
    List<Route> getTop3RoutesWithMaxRating() throws ToursException;
    Service getMostDemandedService() throws ToursException;
    List<Service> getServiceNoAddedToPurchases() throws ToursException;
    List<TourGuideUser> getTourGuidesWithRating1() throws ToursException;

}
