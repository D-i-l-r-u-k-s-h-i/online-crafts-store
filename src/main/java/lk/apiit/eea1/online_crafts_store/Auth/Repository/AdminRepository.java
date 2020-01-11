package lk.apiit.eea1.online_crafts_store.Auth.Repository;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin getByUsername(String username);
}
