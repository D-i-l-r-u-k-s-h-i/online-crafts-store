package lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Repository;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.AllUsers;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Entity.RateCraftCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateCraftCreatorRepository extends JpaRepository<RateCraftCreator,Long> {
    RateCraftCreator getByCraftCreatorAndUser(CraftCreator craftCreator, AllUsers user);

    int countAllByCraftCreator(CraftCreator craftCreator);

    int countAllByRatingAndCraftCreator(int rating,CraftCreator craftCreator);
}
