package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import unlp.info.bd2.model.Service;

public interface ServiceRepository extends CrudRepository<Service, Long>{

    @Query("select s from Service s join s.itemServiceList iserv group by s order by count(iserv) desc")
    Service getMostDemandedService();

    @Query("from Service s where not exists (from ItemService iserv where iserv.service = s)")
    List<Service> getServiceNoAddedToPurchases();
}
