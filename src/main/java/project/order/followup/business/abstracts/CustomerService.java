package project.order.followup.business.abstracts;

import java.util.List;

import project.order.followup.core.entities.Customer;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.Result;

public interface CustomerService {
	DataResult<List<Customer>> getAllCustomers();
	Result add(Customer customer);
	Result update(Customer customer);
	Result deleteByCustomerId(int id);
}
