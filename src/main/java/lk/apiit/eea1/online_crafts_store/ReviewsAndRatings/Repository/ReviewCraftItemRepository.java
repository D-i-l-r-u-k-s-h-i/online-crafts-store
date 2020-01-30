package lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Repository;

import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Entity.ReviewCraftItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewCraftItemRepository extends JpaRepository<ReviewCraftItem,Long> {

    List<ReviewCraftItem> getAllByCraftItemIn(List<CraftItem> craftItemList);
}
