package lk.apiit.eea1.online_crafts_store.Auth.Repository;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CraftCreatorRepository extends JpaRepository<CraftCreator,Long> {
    CraftCreator findByCreatorEmail(String email);

    CraftCreator findByUser_Id(long id);

    CraftCreator getByCreatorId(long id);

    @Query("SELECT u FROM CraftCreator u WHERE lower(u.creatorEmail) like lower(concat('%', ?1,'%')) or lower(u.creatorName) like lower(concat('%', ?1,'%'))")
    List<CraftCreator> searchCreator(String name);
}
