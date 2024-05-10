package unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.repositories.ToursRepository;
import unlp.info.bd2.utils.ToursException;

public class ToursServiceImpl implements ToursService {

    private ToursRepository toursRepository;

    public ToursServiceImpl( ToursRepository toursRepository){
        this.toursRepository = toursRepository;
    }

    @Override
    @Transactional
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        User usu = new User(username, password, fullName, email, birthdate, phoneNumber);
        this.toursRepository.create(usu);
        return usu;
    }

    @Override
    @Transactional
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate,
            String phoneNumber, String expedient) throws ToursException {
        DriverUser usu = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
        this.toursRepository.create(usu);
        return usu;
    }

    @Override
    @Transactional
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email,
            Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser tourGuideUser = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
        this.toursRepository.create(tourGuideUser);
        return tourGuideUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) throws ToursException {
        return Optional.ofNullable(this.toursRepository.getUserById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return Optional.ofNullable(this.toursRepository.getUserByUsername(username));
    }

    @Override
    @Transactional
    public User updateUser(User user) throws ToursException {
        this.toursRepository.update(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(User user) throws ToursException {
       if(user.hasRoutes()) {
           throw new ToursException("El usuario no puede ser desactivado");
       }
       if (user.getPurchaseList().isEmpty()) {
           this.toursRepository.remove(user);
       }
        else{
            if(user.isActive()){
                user.deactivate();
            }
            else {
                throw new ToursException("El usuario se encuentra desactivado");
            }
        }
    }

    @Override
    @Transactional
    public Stop createStop(String name, String description) throws ToursException {
        Stop stop = new Stop(name, description);
        this.toursRepository.create(stop);
        return stop;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stop> getStopByNameStart(String name) {
        return this.toursRepository.getStopByNameStart(name);
    }

    @Override
    @Transactional
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops)
            throws ToursException {
        Route ruta = new Route(name, price, totalKm, maxNumberOfUsers, stops);
        this.toursRepository.create(ruta);
        return ruta;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Route> getRouteById(Long id) {
        return Optional.ofNullable(this.toursRepository.getRouteById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesBelowPrice(float price) {
        return this.toursRepository.getRoutesBelowPrice(price);
    }

    @Override
    @Transactional
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {

        Route route = this.toursRepository.getRouteById(idRoute);
        DriverUser driverUser = (DriverUser) this.toursRepository.getUserByUsername(username);
        if (route == null || driverUser == null){
            throw new ToursException("No pudo realizarse la asignación");
        }
        route.addDriver(driverUser);
        driverUser.addRoute(route);
        this.toursRepository.update(route);
    }

    @Override
    @Transactional
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        Route route = this.toursRepository.getRouteById(idRoute);
        TourGuideUser tourGuideUser = (TourGuideUser) this.toursRepository.getUserByUsername(username);
        if (route == null || tourGuideUser == null){
            throw new ToursException("No pudo realizarse la asignación");
        }

        route.addTourGuide(tourGuideUser);
        tourGuideUser.addRoute(route);
        this.toursRepository.update(route);

    }

    @Override
    @Transactional
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        Supplier supplier = new Supplier(businessName, authorizationNumber);
        this.toursRepository.create(supplier);
        return supplier;
    }

    @Override
    @Transactional
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier)
            throws ToursException {
       Service service = new Service(name, price, description,supplier);
       supplier.addService(service);
       this.toursRepository.update(supplier);
       return service;
    }

    @Override
    @Transactional
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        Service service = this.toursRepository.getServiceById(id);
        if (service == null){
            throw new ToursException("No existe el producto");
        }
        service.setPrice(newPrice);
        this.toursRepository.update(service);
        return service;

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierById(Long id) {
        return Optional.ofNullable(this.toursRepository.getSupplierById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return Optional.ofNullable(this.toursRepository.getSupplierByAuthorizationNumber(authorizationNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        return Optional.ofNullable(this.toursRepository.getServiceByNameAndSupplierId(name, id));
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        if(route.getMaxNumberUsers() <= this.toursRepository.getRoutePurchases(route).size()){
            throw new ToursException("No puede realizarse la compra");
        }
        Purchase purchase = new Purchase(code, user, route);
        user.addPurchase(purchase);
        this.toursRepository.update(user);
        return purchase;
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        if(route.getMaxNumberUsers() <= this.toursRepository.getRoutePurchases(route).size()){
            throw new ToursException("No puede realizarse la compra");
        }
        Purchase purchase = new Purchase(code, date, user, route);
        user.addPurchase(purchase);
        this.toursRepository.update(user);
        return purchase;
    }

    @Override
    @Transactional
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
       ItemService itemService = new ItemService(quantity, purchase, service);
       purchase.addItemService(itemService);
       service.addItemService(itemService);
       this.toursRepository.update(purchase);
       return itemService;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseByCode(String code) {
        return Optional.ofNullable(this.toursRepository.getPurchaseByCode(code));
    }

    @Override
    @Transactional
    public void deletePurchase(Purchase purchase) throws ToursException {
        this.toursRepository.remove(purchase);
    }

    @Override
    @Transactional
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        Review review = new Review(rating, comment, purchase);
        purchase.setReview(review);
        this.toursRepository.update(purchase);
        return review;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        User user = this.toursRepository.getUserByUsername(username);
        return this.toursRepository.getAllPurchasesOfUsername(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUserSpendingMoreThan(float mount) {
        List<User> usersMoreThan = this.toursRepository.getUserSpendingMoreThan(mount);
        System.out.println(usersMoreThan);
        return usersMoreThan;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return this.toursRepository.getTopNSuppliersInPurchase(n);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getTop10MoreExpensivePurchasesInServices() {
        return this.toursRepository.getTop10MoreExpensivePurchasesInServices();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getTop5UsersMorePurchases() {
        return this.toursRepository.getTop5UsersMorePurchases();
    }

    @Override
    @Transactional(readOnly = true)
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return this.toursRepository.getCountOfPurchasesBetweenDates(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesWithStop(Stop stop) {
        return this.toursRepository.getRoutesWithStop(stop);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getMaxStopOfRoutes() {
        return this.toursRepository.getMaxStopOfRoutes();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutsNotSell() {
        return this.toursRepository.getRoutesNotSell();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getTop3RoutesWithMaxRating() {
        return this.toursRepository.getTop3RoutesWithMaxRating();
    }

    @Override
    @Transactional(readOnly = true)
    public Service getMostDemandedService() {
        return this.toursRepository.getMostDemandedService();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Service> getServiceNoAddedToPurchases() {
        return this.toursRepository.getServiceNoAddedToPurchases();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return this.toursRepository.getTourGuidesWithRating1();
    }

}
