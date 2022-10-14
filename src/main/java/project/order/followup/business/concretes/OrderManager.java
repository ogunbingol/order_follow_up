package project.order.followup.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.order.followup.business.abstracts.OrderService;
import project.order.followup.core.dataAccess.CustomerConnection;
import project.order.followup.core.dataAccess.OrderConnection;
import project.order.followup.core.entities.Order;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.ErrorResult;
import project.order.followup.core.utilities.results.Result;
import project.order.followup.core.utilities.results.SuccessDataResult;
import project.order.followup.core.utilities.results.SuccessResult;
import project.order.followup.dataAccess.abstracts.ProductConnection;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

@Service
public class OrderManager implements OrderService{
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
	LocalDateTime now = LocalDateTime.now();
	
	private OrderConnection orderConnection;
	private ProductConnection productConnection;
	private CustomerConnection customerConnection;
	
	@Autowired
	public OrderManager(OrderConnection orderConnection, ProductConnection productConnection, CustomerConnection customerConnection) {
		super();
		this.orderConnection = orderConnection;
		this.productConnection=productConnection;
		this.customerConnection=customerConnection;
	}
	
	@Override
	public DataResult<List<Order>> getAllOrders() {
		List<Order> orders = new ArrayList<Order>();
		for(int i=0;i<this.orderConnection.count();i++) {
			if(this.orderConnection.findAll().get(i).getPresent()==1) {
				orders.add(this.orderConnection.findAll().get(i));
			}
			else {
				
			}
		}		
		return new SuccessDataResult<List<Order>>(orders,"Orders Listelendi");
	
	}

	@Override
	public Result add(Order order) {
		if(this.productConnection.findById(order.getProductId()).isPresent() && this.productConnection.findById(order.getProductId()).get().getPresent()==1 && this.customerConnection.findById(order.getCustomerId()).isPresent() && this.customerConnection.findById(order.getCustomerId()).get().getPresent()==1) {
			if(this.productConnection.findById(order.getProductId()).get().getUnitsInStock()>0 && this.productConnection.findById(order.getProductId()).get().getUnitsInStock()>=order.getOrderNumber()) {
				this.productConnection.findById(order.getProductId()).get().setUnitsInStock(this.productConnection.findById(order.getProductId()).get().getUnitsInStock()-order.getOrderNumber());
				order.setOrderDate(dtf.format(now));
				order.setPresent(1);
				this.orderConnection.save(order);
				return new SuccessResult("Sipariş Eklendi");
			}else {
				return new ErrorResult("İstediğiniz miktarda ürün stokta yok!");
			}
		}else {
			return new ErrorResult("Böyle bir ürün yok ya da müşteri bulunamadı!");
		}
		
	}

	@Override
	public Result update(Order order) {
		if(this.orderConnection.findById(order.getId()).isPresent() && this.orderConnection.findById(order.getId()).get().getPresent()==1) {
			order.setCustomerId(this.orderConnection.findById(order.getId()).get().getCustomerId());
			order.setOrderDate(this.orderConnection.findById(order.getId()).get().getOrderDate());
			order.setPresent(this.orderConnection.findById(order.getId()).get().getPresent());
			order.setProductId(this.orderConnection.findById(order.getId()).get().getProductId());
			if(order.getOrderNumber()>0) {
				if(order.getOrderNumber()<this.orderConnection.findById(order.getId()).get().getOrderNumber()) {
					this.productConnection.findById(order.getProductId()).get().setUnitsInStock(this.productConnection.findById(order.getProductId()).get().getUnitsInStock()+(this.orderConnection.findById(order.getId()).get().getOrderNumber()-order.getOrderNumber()));
				}else if(order.getOrderNumber()>this.orderConnection.findById(order.getId()).get().getOrderNumber()) {
					if(this.productConnection.findById(order.getProductId()).get().getUnitsInStock()>0 && this.productConnection.findById(order.getProductId()).get().getUnitsInStock()>=order.getOrderNumber()-this.orderConnection.findById(order.getId()).get().getOrderNumber()) {
						this.productConnection.findById(order.getProductId()).get().setUnitsInStock(this.productConnection.findById(order.getProductId()).get().getUnitsInStock()-(order.getOrderNumber()-this.orderConnection.findById(order.getId()).get().getOrderNumber()));
					}else {
						return new ErrorResult("İstediğiniz miktarda ürün stokta yok!");
					}
				}
			}else if(order.getOrderNumber()==0) {
				this.productConnection.findById(this.orderConnection.findById(order.getId()).get().getProductId()).get().setUnitsInStock(this.productConnection.findById(this.orderConnection.findById(order.getId()).get().getProductId()).get().getUnitsInStock()+this.orderConnection.findById(order.getId()).get().getOrderNumber());
				this.orderConnection.deleteById(order.getId());
				return new SuccessResult("Sipariş Silindi!");
			}else {
				return new ErrorResult("Geçerli olmayan sipariş adedi!");
			}
			this.orderConnection.save(order);
			return new SuccessResult("Sipariş Güncellendi");
			
		}else {
			return new ErrorResult("Böyle bir sipariş yok!");
		}
	}

	@Override
	public Result deleteByOrderId(int id) {
		Optional<Order> optionalOrder  = this.orderConnection.findById(id);
		if(optionalOrder.isPresent() && optionalOrder.get().getPresent()==1) {
			optionalOrder.get().setPresent(0);
			this.productConnection.findById(this.orderConnection.findById(optionalOrder.get().getId()).get().getProductId()).get().setUnitsInStock(this.productConnection.findById(this.orderConnection.findById(optionalOrder.get().getId()).get().getProductId()).get().getUnitsInStock()+this.orderConnection.findById(optionalOrder.get().getId()).get().getOrderNumber());
			this.orderConnection.save(optionalOrder.get());
			return new SuccessResult("Order Silindi");
		}else{
			return new ErrorResult("Order Bulunamadı");
		}
		/*this.productConnection.deleteById(id);
		return new SuccessResult("Ürün silindi");*/
	
	}

}
