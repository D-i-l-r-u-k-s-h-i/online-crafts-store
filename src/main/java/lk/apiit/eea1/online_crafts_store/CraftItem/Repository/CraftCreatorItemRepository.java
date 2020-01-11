package lk.apiit.eea1.online_crafts_store.CraftItem.Repository;

import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftCreatorCraftItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CraftCreatorItemRepository extends JpaRepository<CraftCreatorCraftItem,Long> {
}
