package lk.apiit.eea1.online_crafts_store.CraftItem.Repository;

import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CraftItemRepository extends JpaRepository<CraftItem,Long> {
    CraftItem findByCraftId(long id);
}
