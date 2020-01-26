package lk.apiit.eea1.online_crafts_store.CraftItem.Repository;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftCreatorCraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CraftCreatorItemRepository extends JpaRepository<CraftCreatorCraftItem,Long> {
    List<CraftCreatorCraftItem> findAllByCraftCreator_CreatorId(long id);

    List<CraftCreatorCraftItem> findAllByCraftItem_Category(String category);

    List<CraftCreatorCraftItem> findAllByCraftItem_Type(String type);

    CraftCreatorCraftItem findByCraftItem_CraftId(long id);
}
