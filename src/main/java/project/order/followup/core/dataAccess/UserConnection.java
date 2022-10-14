package project.order.followup.core.dataAccess;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project.order.followup.core.entities.User;
import project.order.followup.core.utilities.results.Result;

public interface UserConnection extends JpaRepository<User, Integer>{
	User findByEmail(String email);
	
	@Query("delete from User where id=:id")
	Result deleteByUserId(int id);
}
