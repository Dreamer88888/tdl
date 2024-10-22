package todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todolist.entity.EmmaUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<EmmaUser, UUID> {

    Optional<EmmaUser> findByUsername(String username);

}
