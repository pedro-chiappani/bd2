package unlp.info.bd2.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;

public interface RouteRepository extends CrudRepository<Route, Long>{

    @Query("select distinct r from Route r join r.stops s where s = (:stop)")
    List<Route> getRoutesWithStop(@Param("stop") Stop stop);

    @Query("select max(size(r.stops)) from Route r")
    long getMaxStopOfRoutes();

    @Query("from Route r where not exists (from Purchase p where p.route = r)")
    List<Route> getRoutesNotSell();

    @Query("select r from Route r where exists (select 1 from Purchase p where p.route = r) order by (select avg(p.review.rating) from Purchase p where p.route = r) desc")
    List<Route> getTop3RoutesWithMaxRating(Pageable pageable);

}
