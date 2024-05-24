package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import unlp.info.bd2.model.TourGuideUser;

@Repository
public interface TourGuideUserRepository extends CrudRepository<TourGuideUser, Long>{

    TourGuideUser findByUsername(String username);


    @Query("select distinct tg from TourGuideUser tg join tg.routes r join Purchase p on r = p.route join p.review rev where rev.rating = 1")
    List<TourGuideUser> getTourGuidesWithRating1();

}
