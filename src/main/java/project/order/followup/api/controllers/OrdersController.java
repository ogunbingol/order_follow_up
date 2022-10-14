package project.order.followup.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.order.followup.business.abstracts.OrderService;
import project.order.followup.core.entities.Order;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.Result;


@RestController
@RequestMapping("/api/orders")
public class OrdersController {
	private OrderService orderService;
	
	@Autowired
	public OrdersController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}
	
	@PostMapping(value="/add")
	public ResponseEntity<?> add(@Valid @RequestBody Order order) {
		
		return ResponseEntity.ok(this.orderService.add(order)) ;
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<?> update(@Valid @RequestBody Order order) {
		
		return ResponseEntity.ok(this.orderService.update(order)) ;
	}
	
	@DeleteMapping("/delete")
	public Result deleteByOrderId(@RequestParam int id) {
		return this.orderService.deleteByOrderId(id);
	}
	
	@GetMapping("/getAllOrders")
	public DataResult<List<Order>> getAllOrders(){
		return this.orderService.getAllOrders();
	}
}
