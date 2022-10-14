package project.order.followup.core.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project.order.followup.core.entities.Customer;
import project.order.followup.core.utilities.results.Result;

public interface CustomerConnection extends JpaRepository<Customer, Integer>{
	@Query("delete from Customer where id=:id")
	Result deleteByCustomerId(int id);
}
