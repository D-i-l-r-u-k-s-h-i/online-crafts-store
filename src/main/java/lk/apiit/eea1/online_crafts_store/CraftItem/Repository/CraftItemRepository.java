package lk.apiit.eea1.online_crafts_store.CraftItem.Repository;

import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CraftItemRepository extends JpaRepository<CraftItem,Long> {
    CraftItem findByCraftId(long id);

    //search craft
    @Query("SELECT u FROM CraftItem u WHERE lower(u.category) like lower(concat('%', ?1,'%')) or lower(u.ciName) like lower(concat('%', ?1,'%')) or lower(u.shortDescription) like lower(concat('%', ?1,'%')) or lower(u.type) like lower(concat('%', ?1,'%'))")
    List<CraftItem> searchCrafts(String name);

    List<CraftItem> findAllByType(String type);

    List<CraftItem> findAllByCategory(String category);

    //get most recent 10
    @Query("SELECT u FROM CraftItem u ORDER BY u.craftId DESC")
    List<CraftItem> getMostResentCrafts(Pageable pageable);
}
