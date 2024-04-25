package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ToursRepositoryImpl implements ToursRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public ToursRepositoryImpl() {}


    @Override
    public void createUser(Object user) throws ToursException {
        try{
            sessionFactory.getCurrentSession().persist(user);
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }

    }

    @Override
    public User getUserById(Long id) {
        return (User) this.sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        return (User) this.sessionFactory.getCurrentSession().createQuery("from User where username=:username", User.class)
                .setParameter("username",username)
                .uniqueResult();
    }

    @Override
    public void updateUser(Object user) throws ToursException{
        try {
            this.sessionFactory.getCurrentSession().persist(user);
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }
    }

    @Override
    public void createStop(Object stop) {
        this.sessionFactory.getCurrentSession().persist(stop);
    }

    @Override
    public List<Stop> getStopByNameStart(String stopName) {
        return this.sessionFactory.getCurrentSession().createQuery("from Stop where name like :nombre", Stop.class).
                setParameter("nombre", stopName + "%").
                getResultList();
    }

    @Override
    public void createRoute(Object route) {
        this.sessionFactory.getCurrentSession().persist(route);
    }

    @Override
    public Route getRouteById(Long id) {
        return (Route) this.sessionFactory.getCurrentSession().get(Route.class, id);
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {

        return this.sessionFactory.getCurrentSession().createQuery("from Route where price < :price", Route.class).
                setParameter("price", price).
                getResultList();
    }

    @Override
    public void updateRoute(Route route) {
        this.sessionFactory.getCurrentSession().persist(route);
    }

    @Override
    public void createSupplier(Supplier supplier) throws ToursException {
        try{
            this.sessionFactory.getCurrentSession().persist(supplier);
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }

    }

    @Override
    public void updateSupplier(Supplier supplier){
        this.sessionFactory.getCurrentSession().persist(supplier);
    }

    @Override
    public Supplier getSupplierById(Long id){
        return this.sessionFactory.getCurrentSession().get(Supplier.class, id);
    }

    @Override
    public Supplier getSupplierByAuthorizationNumber(String authorizationNumber) {
        return (Supplier) this.sessionFactory.getCurrentSession()
                .createQuery("from Supplier where authorizationNumber=:authorizationNumber", Supplier.class)
                .setParameter("authorizationNumber", authorizationNumber)
                .uniqueResult();
    }

    @Override
    public Service getServiceByNameAndSupplierId(String serviceName, Long supplierId) {
        //Supplier supplier = getSupplierById(supplierId);
        return (Service) this.sessionFactory.getCurrentSession().createQuery("from Service where name=:serviceName and supplier.id=:supplier", Service.class)
                .setParameter("serviceName", serviceName)
                .setParameter("supplier", supplierId)
                .uniqueResult();
    }

    @Override
    public Service getServiceById(Long id) {
        return this.sessionFactory.getCurrentSession().get(Service.class, id);
    }

    @Override
    public void updateService(Service service) {
        this.sessionFactory.getCurrentSession().persist(service);
    }

    @Override
    public void updatePurchase(Purchase purchase) {
        this.sessionFactory.getCurrentSession().persist(purchase);
    }

    @Override
    public Purchase getPurchaseByCode(String code) {
        return (Purchase) this.sessionFactory.getCurrentSession().createQuery("from Purchase where code=:code", Purchase.class)
                .setParameter("code", code)
                .uniqueResult();
    }


    @Override
    public List<Purchase> getRoutePurchases(Route route) {
        return this.sessionFactory.getCurrentSession().createQuery("from Purchase where route=:route", Purchase.class)
                .setParameter("route",route)
                .getResultList();
    }


    @Override
    public void deletePurchase(Purchase purchase) {
        this.sessionFactory.getCurrentSession().remove(purchase);
    }


    @Override
    public void removeUser(User user) {
        this.sessionFactory.getCurrentSession().remove(user);
    }



}
