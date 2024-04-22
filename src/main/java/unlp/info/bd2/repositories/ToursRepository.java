package unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;

import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ToursRepository {

    void createUser(Object user) throws ToursException;
    User getUserById(Long id);
    User getUserByUsername(String username);
    void createDriverUser(Object driver);
    void createTourGuideUser(Object tourGuide);
    void updateUser(Object user);
    void createStop(Object Stop);
    List<Stop> getStopByNameStart(String stopName);
    void createRoute(Object route);
    Route getRouteById(Long id);
    List<Route> getRoutesBelowPrice(float price);
    void updateRoute(Route route);
    void createSupplier(Supplier supplier) throws ToursException;
    void updateSupplier(Supplier supplier);
    Supplier getSupplierById(Long id);
    Supplier getSupplierByAuthorizationNumber(String authorizationNumber);
    Service getServiceByNameAndSupplierId(String serviceName, Long supplierId);
    Service getServiceById(Long id);
    void updateService(Service service);
    void createPurchase(Purchase purchase) throws ToursException;
    void createItemService(ItemService itemService);
    Purchase getPurchaseByCode(String code);
    List<Purchase> getRoutePurchases(Route route);
    Purchase getPurchaseByUserAndDate(User user, Date date, Route route);



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
