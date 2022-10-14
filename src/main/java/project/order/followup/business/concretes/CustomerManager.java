package project.order.followup.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import project.order.followup.business.abstracts.CustomerService;
import project.order.followup.core.dataAccess.CustomerConnection;
import project.order.followup.core.dataAccess.UserConnection;
import project.order.followup.core.entities.Customer;
import project.order.followup.core.entities.User;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.ErrorResult;
import project.order.followup.core.utilities.results.Result;
import project.order.followup.core.utilities.results.SuccessDataResult;
import project.order.followup.core.utilities.results.SuccessResult;

@Service
public class CustomerManager implements CustomerService{

	private CustomerConnection customerConnection;
	private UserConnection userConnection;
	
	@Autowired
	public CustomerManager(CustomerConnection customerConnection, UserConnection userConnection) {
		super();
		this.customerConnection = customerConnection;
		this.userConnection=userConnection;
	}
	
	@Override
	public DataResult<List<Customer>> getAllCustomers() {
		//Sort sort=Sort.by(Sort.Direction.ASC,"id");
		List<Customer> customers = new ArrayList<Customer>();
		for(int i=0;i<this.customerConnection.count();i++) {
			if(this.customerConnection.findAll().get(i).getPresent()==1 && this.customerConnection.findAll().get(i).getUser().getPresent()==1) {
				customers.add(this.customerConnection.findAll().get(i));
			}
			else {
				
			}
		}	
		return new SuccessDataResult<List<Customer>>(customers,"Customer Listelendi");
	}
	
	@Override
	public Result add(Customer customer) {
		if(this.userConnection.findById(customer.getUser().getId()).isPresent()) {
			customer.setUser(this.userConnection.findById(customer.getUser().getId()).get());
			customer.setPresent(1);
			this.customerConnection.save(customer);
			return new SuccessResult("Customer eklendi!");
		}else {
			User user=new User();
			user.setEmail(customer.getUser().getEmail());
			user.setName(customer.getUser().getName());
			user.setPassword(customer.getUser().getPassword());
			user.setPhone(customer.getUser().getPhone());
			user.setPresent(1);
			
			Sort sort=Sort.by(Sort.Direction.DESC,"id");
			List<User> lastOneUser=this.userConnection.findAll(sort);
			user.setId(lastOneUser.get(0).getId()+1);
			customer.setPresent(1);
			customer.setUser(user);
			this.customerConnection.save(customer);
			this.userConnection.save(user);
			return new SuccessResult("Customer eklendi");
		}
	}

	@Override
	public Result update(Customer customer) {
		if(customer.getName().equals("string")) {
			customer.setName(this.customerConnection.findById(customer.getId()).get().getName());
		}
		customer.setUser(this.customerConnection.findById(customer.getId()).get().getUser());
		this.customerConnection.save(customer);
		return new SuccessResult("Customer güncellendi");
	}

	@Override
	public Result deleteByCustomerId(int id) {
		Optional<Customer> optionalCustomer  = this.customerConnection.findById(id);
		if(optionalCustomer.isPresent()) {
			optionalCustomer.get().setPresent(0);
			this.customerConnection.save(optionalCustomer.get());
			return new SuccessResult("Customer Silindi");
		}else{
			return new ErrorResult("Customer Bulunamadı");
		}
		/*this.productConnection.deleteById(id);
		return new SuccessResult("Ürün silindi");*/
	}

}
