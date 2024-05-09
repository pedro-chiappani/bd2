package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;

import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>{
    int countByRoute(Route route);
    Purchase findByCode(String code);
}
