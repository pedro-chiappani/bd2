package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import unlp.info.bd2.model.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Long>{

    Supplier findByAuthorizationNumber(String authorizationNumber);


    @Query("select s from Supplier s join s.services srv join srv.itemServiceList si join si.purchase p group by s order by count(p) desc")
    List<Supplier> getTopNSuppliersInPurchases(Pageable pageable);

}
