package lk.apiit.eea1.online_crafts_store.Auth.Repository;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.AllUsers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AllUsers,Long> {
    AllUsers findByUsername(String Username);

    //get most recent 10
    @Query("SELECT u FROM AllUsers u ORDER BY u.id DESC")
    List<AllUsers> getNewlyRegisteredUsers(Pageable pageable);
}
