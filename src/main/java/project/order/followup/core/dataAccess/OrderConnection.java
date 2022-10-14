package project.order.followup.core.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project.order.followup.core.entities.Order;
import project.order.followup.core.utilities.results.Result;

public interface OrderConnection extends JpaRepository<Order, Integer>{
	@Query("delete from Order where id=:id")
	Result deleteByOrderId(int id);
}
