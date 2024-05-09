package unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Review;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.model.Supplier;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;
import unlp.info.bd2.repositories.*;
import unlp.info.bd2.utils.ToursException;

public class SpringDataToursServiceImpl implements ToursService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    DriverUserRepository driverUserRepository;
    @Autowired
    TourGuideUserRepository tourGuideUserRepository;
    @Autowired
    StopRepository stopRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public User createUser(String username, String password, String fullName, String email, Date birthdate,
            String phoneNumber) throws ToursException {
        try{
            return userRepository.save(new User(username, password, fullName, email, birthdate, phoneNumber));
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }
        catch (Exception e){
            throw new ToursException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate,
            String phoneNumber, String expedient) throws ToursException {
        try{
            return driverUserRepository.save(new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient));
        }catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }
        catch (Exception e){
            throw new ToursException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email,
            Date birthdate, String phoneNumber, String education) throws ToursException {
        try{
            return tourGuideUserRepository.save(new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education));
        }catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }
        catch (Exception e){
            throw new ToursException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) throws ToursException {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    @Transactional
    public User updateUser(User user) throws ToursException {
        if(userRepository.existsById(user.getId())){
            return userRepository.save(user);
        }else {
            throw new ToursException("El usuario no existe");
        }
    }

    @Override
    public void deleteUser(User user) throws ToursException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    @Transactional
    public Stop createStop(String name, String description) throws ToursException {
        try{
            return stopRepository.save(new Stop(name, description));
        }catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }catch (Exception e){
            throw new ToursException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stop> getStopByNameStart(String name) {
        return stopRepository.findByNameStartingWith(name);
    }

    @Override
    @Transactional
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops)
            throws ToursException {
        try{
            return routeRepository.save(new Route(name, price, totalKm, maxNumberOfUsers, stops));
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }catch (Exception e){
            throw new ToursException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesBelowPrice(float price) {
        return routeRepository.findByPriceLessThan(price);
    }

    @Override
    @Transactional
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        Optional<Route> optionalRoute = routeRepository.findById(idRoute);
        DriverUser driverUser = driverUserRepository.findByUsername(username);
        if(optionalRoute.isEmpty() || driverUser == null){
            throw new ToursException("No pudo realizarse la asignación");
        }
        Route route = optionalRoute.get();
        route.addDriver(driverUser);
        driverUser.addRoute(route);
        routeRepository.save(route);



    }

    @Override
    @Transactional
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        Optional<Route> optionalRoute = routeRepository.findById(idRoute);
        TourGuideUser tourGuideUser = tourGuideUserRepository.findByUsername(username);
        if(optionalRoute.isEmpty() || tourGuideUser == null){
            throw new ToursException("No pudo realizarse la asignación");
        }
        Route route = optionalRoute.get();
        route.addTourGuide(tourGuideUser);
        tourGuideUser.addRoute(route);

        routeRepository.save(route);

    }

    @Override
    @Transactional
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        try{
            return supplierRepository.save(new Supplier(businessName, authorizationNumber));
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }catch (Exception e){
            throw new ToursException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier)
            throws ToursException {
        Service service = new Service(name, price, description, supplier);
        supplier.addService(service);

        supplierRepository.save(supplier);
        return supplier.getServices().get(supplier.getServices().size()-1); //service

    }

    @Override
    @Transactional
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        if(!serviceRepository.existsById(id)){
            throw new ToursException("No existe el producto");
        }
        Service service = serviceRepository.findById(id).get();
        service.setPrice(newPrice);

        serviceRepository.save(service);
        return service;

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return Optional.ofNullable(supplierRepository.findByAuthorizationNumber(authorizationNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if(supplierOptional.isEmpty()){
            throw new ToursException("El proveedor no existe");
        }
        Supplier supplier = supplierOptional.get();
        return Optional.ofNullable(serviceRepository.findByNameAndSupplier(name, supplier));
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        if(route.getMaxNumberUsers() <= purchaseRepository.countByRoute(route)){
            throw new ToursException("No puede realizarse la compra");
        }
        Purchase purchase = new Purchase(code, user, route);
        user.addPurchase(purchase);
        try{
            userRepository.save(user);
            return purchase;

        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }catch (Exception e){
            throw new ToursException(e.getMessage());
        }

    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        if(route.getMaxNumberUsers() <= purchaseRepository.countByRoute(route)){
            throw new ToursException("No puede realizarse la compra");
        }
        Purchase purchase = new Purchase(code, date, user, route);
        user.addPurchase(purchase);
        try{
            userRepository.save(user);
            return user.getPurchaseList().get(user.getPurchaseList().size()-1); //purchase
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }catch (Exception e){
            throw new ToursException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        ItemService itemService = new ItemService(quantity, purchase, service);
        purchase.addItemService(itemService);
        service.addItemService(itemService);

        purchaseRepository.save(purchase);
        return purchase.getItemServiceList().get(purchase.getItemServiceList().size()-1); //itemService



    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseByCode(String code) {
        return Optional.ofNullable(purchaseRepository.findByCode(code));
    }

    @Override
    @Transactional
    public void deletePurchase(Purchase purchase) throws ToursException {
        try{
            purchaseRepository.delete(purchase);
        }
        catch (ConstraintViolationException e){
            throw new ToursException("Constraint Violation" + e.getMessage());
        }catch (Exception e){
            throw new ToursException(e.getMessage());
        }
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
