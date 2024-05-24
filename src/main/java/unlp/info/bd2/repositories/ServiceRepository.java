package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.Service;
import unlp.info.bd2.model.Supplier;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long>{

    Service findByNameAndSupplier(String name, Supplier supplier);


    @Query("select s from Service s join s.itemServiceList iserv group by s order by count(iserv) desc")
    List<Service> getMostDemandedService(Pageable pageable);

    @Query("from Service s where not exists (from ItemService iserv where iserv.service = s)")
    List<Service> findByItemServiceListNull();

}
