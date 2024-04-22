package unlp.info.bd2.repositories;

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

    void deletePurchase(Purchase purchase);

    void createReview(Review review);

    void removeUser(User user);

    Optional<TourGuideUser> getTourGuideUserById(Long id);
}


