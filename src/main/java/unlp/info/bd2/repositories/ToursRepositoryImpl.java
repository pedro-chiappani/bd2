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
import java.util.stream.Collectors;

@Repository
public class ToursRepositoryImpl implements ToursRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public ToursRepositoryImpl() {}


    @Override
    public void create(Object object) throws ToursException {
        try{
            sessionFactory.getCurrentSession().persist(object);
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }
        catch (Exception e){
            throw new ToursException(e.getMessage());
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
    public void update(Object object) throws ToursException{
        try {
            this.sessionFactory.getCurrentSession().persist(object);
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }
        catch (Exception e){
            throw new ToursException(e.getMessage());
        }
    }


    @Override
    public List<Stop> getStopByNameStart(String stopName) {
        return this.sessionFactory.getCurrentSession().createQuery("from Stop where name like :nombre", Stop.class).
                setParameter("nombre", stopName + "%").
                getResultList();
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
    public void remove(Object object) throws ToursException {
        try{
            this.sessionFactory.getCurrentSession().remove(object);
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }
        catch (Exception e){
            throw new ToursException(e.getMessage());
        }
    }



    public Purchase getPurchaseByUserAndDate(User user, Date date, Route route) {
        return (Purchase) this.sessionFactory.getCurrentSession().createQuery("from Purchase where user=:user and date=:date and route=:route", Purchase.class)
                .setParameter("user", user)
                .setParameter("date", date)
                .setParameter("route", route)
                .uniqueResult();
    }



    @Override
    public List<Purchase> getAllPurchasesOfUsername(User user) {
        return this.sessionFactory.getCurrentSession().createQuery("from Purchase where user=:user", Purchase.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<User> getUserSpendingMoreThan(float mount){
        return this.sessionFactory.getCurrentSession()
                .createQuery("select distinct u from User u " +
                             "join u.purchaseList p where p.totalPrice >= :mount", User.class)
                .setParameter("mount", mount)
                .getResultList();
        // return users;
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchase(int n){
        return this.sessionFactory.getCurrentSession()
                .createQuery("select s from Supplier s " +
                            "join s.services srv join srv.itemServiceList si " +
                            "join si.purchase p group by s " +
                            "order by count(p) desc", Supplier.class)
                .setMaxResults(n)
                .getResultList()
                ;
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select distinct p from Purchase p " +
                            "where size(p.itemServiceList) > 0 " +
                            "order by p.totalPrice desc", Purchase.class)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<User> getTop5UsersMorePurchases() {
        List<User> users = this.sessionFactory.getCurrentSession()
                .createQuery("select u from User u " +
                            "order by size(u.purchaseList) desc", User.class)
                .setMaxResults(5)
                .getResultList();

        return users;
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date begin, Date end){
        return (long) this.sessionFactory.getCurrentSession().createQuery("select count (p) From Purchase p where p.date between :start and :end", Long.class)
                .setParameter("start", begin)
                .setParameter("end", end)
                .uniqueResult();
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop){
        return this.sessionFactory.getCurrentSession()
                .createQuery("select distinct r from Route r join r.stops s where s = :stop", Route.class)
                .setParameter("stop", stop)
                .getResultList();
    }

    @Override
    public Long getMaxStopOfRoutes() {
        return ((Number) this.sessionFactory.getCurrentSession()
                .createQuery("select max(size(r.stops)) from Route r", Long.class)
                .getSingleResult()).longValue();
    }

    @Override
    public List<Route> getRoutesNotSell() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("from Route r " +
                            "where not exists (from Purchase p where p.route = r)", Route.class)
                .getResultList();
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select r from Route r " +
                            "where exists (select 1 from Purchase p where p.route = r) " +
                            "order by (select avg(p.review.rating) from Purchase p where p.route = r) desc", Route.class)
                .setMaxResults(3)
                .getResultList();

    }


    @Override
    public Service getMostDemandedService() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select s from Service s " +
                            "join s.itemServiceList iserv " +
                            "group by s order by sum(iserv.quantity) desc", Service.class)
                .setMaxResults(1)
                .uniqueResult();
    }


    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("from Service s " +
                            "where not exists (from ItemService iserv where iserv.service = s)",
                             Service.class)
                .getResultList();
    }


    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select distinct tg from TourGuideUser tg " +
                            "join tg.routes r " +
                            "join Purchase p on r = p.route " +
                            "join p.review rev "+
                            "where rev.rating = 1", TourGuideUser.class)
                .getResultList();
    }

}
