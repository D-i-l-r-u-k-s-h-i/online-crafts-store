package lk.apiit.eea1.online_crafts_store.Auth.Repository;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CraftCreatorRepository extends JpaRepository<CraftCreator,Long> {
    CraftCreator findByCreatorEmail(String email);
}
