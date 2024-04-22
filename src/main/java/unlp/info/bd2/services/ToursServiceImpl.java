package unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import unlp.info.bd2.model.*;
import unlp.info.bd2.repositories.ToursRepository;
import unlp.info.bd2.utils.ToursException;

public class ToursServiceImpl implements ToursService {

    private ToursRepository toursRepository;

    public ToursServiceImpl( ToursRepository toursRepository){
        this.toursRepository = toursRepository;
    }

    @Override
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        User usu = new User(username, password, fullName, email, birthdate, phoneNumber);
        this.toursRepository.createUser(usu);
        return usu;
    }

    @Override
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate,
            String phoneNumber, String expedient) throws ToursException {
        DriverUser usu = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
        this.toursRepository.createDriverUser(usu);
        return usu;
    }

    @Override
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email,
            Date birthdate, String phoneNumber, String education) throws ToursException {
        TourGuideUser tourGuideUser = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
        this.toursRepository.createTourGuideUser(tourGuideUser);
        return tourGuideUser;
    }

    @Override
    public Optional<User> getUserById(Long id) throws ToursException {
        return Optional.ofNullable(this.toursRepository.getUserById(id));
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return Optional.ofNullable(this.toursRepository.getUserByUsername(username));

    }

    @Override
    public User updateUser(User user) throws ToursException {
        this.toursRepository.updateUser(user);
        return user;
    }

    @Override
    public void deleteUser(User user) throws ToursException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public Stop createStop(String name, String description) throws ToursException {
        Stop parada = new Stop(name, description);
        this.toursRepository.createStop(parada);
        return parada;
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        return this.toursRepository.getStopByNameStart(name);
    }

    @Override
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops)
            throws ToursException {
        Route ruta = new Route(name, price, totalKm, maxNumberOfUsers, stops);
        this.toursRepository.createRoute(ruta);
        return ruta;
    }

    @Override
    public Optional<Route> getRouteById(Long id) {
        return Optional.ofNullable(this.toursRepository.getRouteById(id));
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return this.toursRepository.getRoutesBelowPrice(price);
    }

    @Override
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        try {
            Route route = this.toursRepository.getRouteById(idRoute);
            DriverUser driverUser = (DriverUser) this.toursRepository.getUserByUsername(username);
            route.addDriver(driverUser);
            this.toursRepository.updateRoute(route);
        }
        catch (Exception e) {
            throw new ToursException("No pudo realizarse la asignación");
        }
    }

    @Override
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        try{
            Route route = this.toursRepository.getRouteById(idRoute);
            TourGuideUser tourGuideUser = (TourGuideUser) this.toursRepository.getUserByUsername(username);
            route.addTourGuide(tourGuideUser);
            this.toursRepository.updateRoute(route);
        }
        catch (Exception e) {
            throw new ToursException("No pudo realizarse la asignación");
        }
    }

    @Override
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        Supplier supplier = new Supplier(businessName, authorizationNumber);
        this.toursRepository.createSupplier(supplier);
        return supplier;
    }

    @Override
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier)
            throws ToursException {
       Service service = new Service(name, price, description,supplier);
       supplier.addService(service);
       this.toursRepository.updateSupplier(supplier);
       return service;
    }

    @Override
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        try{
            Service service = this.toursRepository.getServiceById(id);
            service.setPrice(newPrice);
            this.toursRepository.updateService(service);
            return service;
        }
        catch (Exception e){
            throw new ToursException("No existe el producto");
        }

    }

    @Override
    public Optional<Supplier> getSupplierById(Long id) {
        return Optional.ofNullable(this.toursRepository.getSupplierById(id));
    }

    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return Optional.ofNullable(this.toursRepository.getSupplierByAuthorizationNumber(authorizationNumber));
    }

    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        return Optional.ofNullable(this.toursRepository.getServiceByNameAndSupplierId(name, id));
    }

    @Override
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        Purchase purchase = new Purchase(code, user, route);
        user.addPurchase(purchase);
        this.toursRepository.createPurchase(purchase);
        return purchase;
    }

    @Override
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {

        if(route.getMaxNumberUsers() <= this.toursRepository.getRoutePurchases(route).size()){
            throw new ToursException("No puede realizarse la compra");
        }
        Purchase purchase = new Purchase(code, date, user, route);
        user.addPurchase(purchase);
        this.toursRepository.createPurchase(purchase);
        return purchase;
    }

    @Override
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
       ItemService itemService = new ItemService(quantity, purchase, service);
       purchase.addItemService(itemService);
       this.toursRepository.createItemService(itemService);
       return itemService;
    }

    @Override
    public Optional<Purchase> getPurchaseByCode(String code) {
        return Optional.ofNullable(this.toursRepository.getPurchaseByCode(code));
    }

    @Override
    public void deletePurchase(Purchase purchase) throws ToursException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePurchase'");
    }

    @Override
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addReviewToPurchase'");
    }

    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPurchasesOfUsername'");
    }

    @Override
    public List<User> getUserSpendingMoreThan(float mount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserSpendingMoreThan'");
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTopNSuppliersInPurchases'");
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTop10MoreExpensivePurchasesInServices'");
    }

    @Override
    public List<User> getTop5UsersMorePurchases() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTop5UsersMorePurchases'");
    }

    @Override
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCountOfPurchasesBetweenDates'");
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoutesWithStop'");
    }

    @Override
    public Long getMaxStopOfRoutes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMaxStopOfRoutes'");
    }

    @Override
    public List<Route> getRoutsNotSell() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoutsNotSell'");
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTop3RoutesWithMaxRating'");
    }

    @Override
    public Service getMostDemandedService() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMostDemandedService'");
    }

    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServiceNoAddedToPurchases'");
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTourGuidesWithRating1'");
    }

}
