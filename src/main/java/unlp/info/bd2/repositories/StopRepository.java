package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import unlp.info.bd2.model.Stop;

public interface StopRepository extends CrudRepository<Stop, Long>{
    List<Stop> findByNameStartingWith(String name);
}
