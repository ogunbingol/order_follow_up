package project.order.followup.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.order.followup.business.abstracts.CustomerService;
import project.order.followup.core.entities.Customer;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.Result;


@RestController
@RequestMapping("/api/customers")
public class CustomersController {
	private CustomerService customerService;
	
	@Autowired
	public CustomersController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@GetMapping("/getall")
	public DataResult<List<Customer>> getAllCustomers(){
		return this.customerService.getAllCustomers();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody Customer customer) {
		return this.customerService.add(customer);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody Customer customer) {
		return this.customerService.update(customer);
	}
	
	@DeleteMapping("/delete")
	public Result deleteByCustomerId(@RequestParam int id) {
		return this.customerService.deleteByCustomerId(id);
	}
}
