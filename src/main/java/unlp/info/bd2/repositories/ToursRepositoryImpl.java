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
        List<User> users = this.sessionFactory.getCurrentSession().createQuery("SELECT u.* FROM users u JOIN purchases p ON u.id = p.user_id GROUP BY u.id HAVING SUM(p.amount) >= :mount", User.class)
                .setParameter("mount", mount)
                .getResultList();
        return users;
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchase(int n){
        return this.sessionFactory.getCurrentSession()
                .createQuery("select s, count(p) as purchaseCount " +
                            "from Supplier s join s.services srv join srv.itemService si join si.purchase p " +
                            "group by s.id order by purchaseCount desc", Supplier.class)
                .setMaxResults(n)
                .getResultList();
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select distinct p from Purchase p " +
                            "join fetch p.route r join fetch r.stops " +
                            "join fetch p.itemServices i where size(p.itemServices) > 0 " +
                            "order by p.totalPrice desc", Purchase.class)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<User> getTop5UsersMorePurchases() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select u, count(p) as purchaseCount " +
                            "from User u join u.purchases p group by u.id " +
                            "order by purchaseCount desc", User.class)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date begin, Date end){
        return (long) this.sessionFactory.getCurrentSession().createQuery("select count p From Purchase p where p.date between :start and :end", Long.class)
                .setParameter("start", begin)
                .setParameter("end", end)
                .uniqueResult();
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop){
        return this.sessionFactory.getCurrentSession()
                .createQuery("from Route where stop=:stop", Route.class)
                .getResultList();
    }

    @Override
    public Long getMaxStopOfRoutes() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select max(size(r.stops)) from Route r", Long.class)
                .getSingleResult();
    }

    @Override
    public List<Route> getRoutesNotSell() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select r from Route r " +
                            "where not exist (select 1 from Purchase p where p.route = r)", Route.class)
                .getResultList();
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select r from Route r " +
                            "join r.purchases p join p.reviews rev " +
                            "group by r order by avg(rev.rating) desc", Route.class)
                .setMaxResults(3)
                .getResultList();

    }


    @Override
    public Service getMostDemandedService() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select s from Service s " +
                            "join s.itemServices iserv " +
                            "group by s order by count(iserv) desc", Service.class)
                .setMaxResults(1)
                .uniqueResult();
    }


    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select s from Service s " +
                            "where not exist (select 1 from ItemService iserv where iserv.service = s)",
                             Service.class)
                .getResultList();
    }


    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("select distinct tg from TourGuideUser tg " +
                            "join tg.purchases p join p.reviews rev " +
                            "where rev.rating = 1", TourGuideUser.class)
                .getResultList();
    }

}
