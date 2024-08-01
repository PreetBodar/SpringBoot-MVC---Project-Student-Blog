package BlogAcadamy.BlogAcadamy.repository;

import BlogAcadamy.BlogAcadamy.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
