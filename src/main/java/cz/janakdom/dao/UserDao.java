package cz.janakdom.dao;

import cz.janakdom.model.database.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
