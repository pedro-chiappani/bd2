package unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.User;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>{

    int countByRoute(Route route);
    Purchase findByCode(String code);


    @Query("select p from Purchase p where user=(:user)")
    abstract List<Purchase> getAllPurchasesOfUsername(@Param("user") User user);

    @Query("select distinct p from Purchase p where size(p.itemServiceList) > 0 order by p.totalPrice desc")
    List<Purchase> getTop10MoreExpensivePurchasesInService(Pageable pageable);

    @Query("select count (p) From Purchase p where p.date between (:start) and (:end)")
    long getCountOfPurchasesBetweenDates(@Param("start") Date start, @Param("end") Date end);

}
