package lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Repository;

import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Entity.ReviewCraftItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewCraftItemRepository extends JpaRepository<ReviewCraftItem,Long> {
}