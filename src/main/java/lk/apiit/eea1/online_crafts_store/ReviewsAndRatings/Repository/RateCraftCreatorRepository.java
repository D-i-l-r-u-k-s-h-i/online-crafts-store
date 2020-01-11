package lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Repository;

import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Entity.RateCraftCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateCraftCreatorRepository extends JpaRepository<RateCraftCreator,Long> {
}
