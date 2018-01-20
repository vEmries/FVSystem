package App.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {

    List<User> findAll();
    User findById(Integer ID);
    User findByUsername(String userName);

    @Query("select u.id from User u")
    public List<Integer> getUserIDs();
}
