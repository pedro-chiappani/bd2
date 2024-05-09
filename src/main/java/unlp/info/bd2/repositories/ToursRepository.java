package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;

public interface ToursRepository {

    void create(Object object) throws ToursException;
    void update(Object object) throws ToursException;
    void remove(Object object) throws ToursException ;
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<Stop> getStopByNameStart(String stopName);
    Route getRouteById(Long id);
    List<Route> getRoutesBelowPrice(float price);
    Supplier getSupplierById(Long id);
    Supplier getSupplierByAuthorizationNumber(String authorizationNumber);
    Service getServiceByNameAndSupplierId(String serviceName, Long supplierId);
    Service getServiceById(Long id);
    Purchase getPurchaseByCode(String code);
    List<Purchase> getRoutePurchases(Route route);

    List<Purchase> getAllPurchasesOfUsername(User user);
    List<User> getUserSpendingMoreThan(float mount);
    List<Supplier> getTopNSuppliersInPurchase(int cant);
    List<Purchase> getTop10MoreExpensivePurchasesInServices();
    List<User> getTop5UsersMorePurchases();
    long getCountOfPurchasesBetweenDates(Date fecha1, Date fecha2);
    List<Route> getRoutesWithStop(Stop stop);
    Long getMaxStopOfRoutes();
    List<Route> getRoutesNotSell();
    List<Route> getTop3RoutesWithMaxRating();
    Service getMostDemandedService();
    List<Service> getServiceNoAddedToPurchases();
    List<TourGuideUser> getTourGuidesWithRating1();

}
