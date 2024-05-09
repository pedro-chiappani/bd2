package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;

import unlp.info.bd2.model.TourGuideUser;

public interface TourGuideUserRepository extends CrudRepository<TourGuideUser, Long>{
    TourGuideUser findByUsername(String username);
}
