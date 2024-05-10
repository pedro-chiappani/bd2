package unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import unlp.info.bd2.model.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>{

    @Query("select p from Purchase p where user=(:username)")
    abstract List<Purchase> getAllPurchasesOfUsername(@Param("username") String username);

    @Query("select distinct p from Purchase p where size(p.itemServiceList) > 0 order by p.totalPrice desc")
    List<Purchase> getTop10MoreExpensivePurchasesInService(Pageable pageable);

    @Query("select count (p) From Purchase p where p.date between (:start) and (:end)")
    long getCountOfPurchasesBetweenDates(@Param("start") Date start, @Param("end") Date end);
}
