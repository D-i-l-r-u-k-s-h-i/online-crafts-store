package lk.apiit.eea1.online_crafts_store.Auth.Repository;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.AllUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AllUsers,Long> {
    AllUsers findByUsername(String Username);
}
