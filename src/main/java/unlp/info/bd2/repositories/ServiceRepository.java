package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;

import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;

public interface ServiceRepository extends CrudRepository<Service, Long>{
    Service findByNameAndSupplier(String name, Supplier supplier);
}
