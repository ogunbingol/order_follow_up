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

import project.order.followup.business.abstracts.ProductService;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.Result;
import project.order.followup.entities.concretes.Product;
import project.order.followup.entities.dtos.ProductWithCategoryDto;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
	
	private ProductService productService;
	
	@Autowired
	public ProductsController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping("/getall")
	public DataResult<List<Product>> getAllProducts(){
		return this.productService.getAllProducts();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody Product product) {
		return this.productService.add(product);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody Product product) {
		return this.productService.update(product);
	}
	
	@DeleteMapping("/delete")
	public Result deleteByProductId(@RequestParam int id) {
		return this.productService.deleteByProductId(id);
	}
	
	@GetMapping("/getByProductName")
	public DataResult<Product> getByProductName(@RequestParam String productName){
		return this.productService.getByProductName(productName);
	}
	
	@GetMapping("/getByProductNameAndCategoryId")
	public DataResult<Product> getByProductNameAndCategoryId(@RequestParam String productName,@RequestParam int categoryId){
		return this.productService.getByProductNameAndCategoryId(productName,categoryId);
	}
	
	@GetMapping("/getByProductNameContains")
	public DataResult<List<Product>> getByProductNameContains(@RequestParam String productName){
		return this.productService.getByProductNameContains(productName);
	}
	
	@GetMapping("/getAllByPage")
	DataResult<List<Product>> getAllProductsByPage(int pageNo, int pageSize){
		return this.productService.getAllProductsByPage(pageNo,pageSize);
	}
	
	@GetMapping("/getAllProductsSortedDesc")
	public DataResult<List<Product>> getAllProductsSorted() {
		return this.productService.getAllProductsSortedDesc();
	}
	
	@GetMapping("/getProductWithCategoryDetails")
	public DataResult<List<ProductWithCategoryDto>> getProductWithCategoryDetails(){
		return this.productService.getProductWithCategoryDetails();
	}
}
