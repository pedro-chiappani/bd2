package unlp.info.bd2.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

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
        return (User) this.sessionFactory.getCurrentSession().createQuery("from User where username=:username").setParameter("username",username).uniqueResult();
    }

    @Override
    public void createDriverUser(Object driver) {
        this.sessionFactory.getCurrentSession().persist(driver);
    }

    @Override
    public void createTourGuideUser(Object tourGuideUser) {
        this.sessionFactory.getCurrentSession().persist(tourGuideUser);
    }

    @Override
    public void updateUser(Object user) {
        this.sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    public void createStop(Object stop) {
        this.sessionFactory.getCurrentSession().persist(stop);
    }

    @Override
    public List<Stop> getStopByNameStart(String stopName) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Stop> cr = cb.createQuery(Stop.class);
        Root<Stop> stop = cr.from(Stop.class);

        cr.select(stop).where(cb.like(stop.get("name"), stopName+"%"));
        return sessionFactory.getCurrentSession().createQuery(cr).getResultList();
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
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Route> cr = cb.createQuery(Route.class);
        Root<Route> route = cr.from(Route.class);

        cr.select(route).where(cb.lt(route.get("price"), price));
        return sessionFactory.getCurrentSession().createQuery(cr).getResultList();
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
        return (Supplier) this.sessionFactory.getCurrentSession().createQuery("from Supplier where authorizationNumber=:authorizationNumber").setParameter("authorizationNumber", authorizationNumber).uniqueResult();
    }

    @Override
    public Service getServiceByNameAndSupplierId(String serviceName, Long supplierId) {
        Supplier supplier = getSupplierById(supplierId);
        return (Service) this.sessionFactory.getCurrentSession().createQuery("from Service where name=:serviceName and supplier=:supplier")
                .setParameter("serviceName", serviceName)
                .setParameter("supplier", supplier)
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
    public Purchase createPurchase(Purchase purchase) {
        //persistir
        return purchase;
    }

}
