package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.DriverUser;

@Repository
public interface DriverUserRepository extends CrudRepository<DriverUser,Long> {
    DriverUser findByUsername(String username);
}
