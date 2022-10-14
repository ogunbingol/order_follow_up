package project.order.followup.business.abstracts;

import java.util.List;

import project.order.followup.core.entities.Order;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.Result;

public interface OrderService {
	DataResult<List<Order>> getAllOrders();
	Result add(Order order);
	Result update(Order order);
	Result deleteByOrderId(int id);
}
