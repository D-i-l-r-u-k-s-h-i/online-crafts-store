package lk.apiit.eea1.online_crafts_store.CraftItem.Repository;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftCreatorCraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CraftCreatorItemRepository extends JpaRepository<CraftCreatorCraftItem,Long> {
    Page<CraftCreatorCraftItem> findAllByCraftCreator_CreatorId(long id, Pageable pageable);

    List<CraftCreatorCraftItem> findAllByCraftItem_Category(String category);

    List<CraftCreatorCraftItem> findAllByCraftItem_Type(String type);

    CraftCreatorCraftItem findByCraftItem_CraftId(long id);

    List<CraftCreatorCraftItem> getAllByCraftCreator(CraftCreator creator);

    CraftCreatorCraftItem getByCraftItem(CraftItem craftItem);
}
