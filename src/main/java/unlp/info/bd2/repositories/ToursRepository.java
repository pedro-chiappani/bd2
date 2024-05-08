package unlp.info.bd2.repositories;

import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.List;

public interface ToursRepository {

    void create(Object object) throws ToursException;
    void update(Object object) throws ToursException;
    void remove(Object object);
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

}


