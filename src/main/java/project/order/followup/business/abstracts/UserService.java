package project.order.followup.business.abstracts;

import java.util.List;

import project.order.followup.core.entities.User;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.Result;

public interface UserService {
	DataResult<List<User>> getAllUsers();
	Result add(User user);
	Result update(User user);
	Result deleteByUserId(int id);
	DataResult<User> findByEmail(String email);
}
