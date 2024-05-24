package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    User findByUsername(String username);


    @Query("select distinct u from User u join u.purchaseList p where p.totalPrice >= :mount")
    List<User> findDistinctByPurchaseListNotNullOrderByTotalPriceGreaterThan(float mount);

    @Query("select u from User u order by size(u.purchaseList) desc")
    List<User> getTop5UsersMorePurchases(Pageable pageable);

}
