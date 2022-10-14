package project.order.followup.business.abstracts;

import java.util.List;


import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.Result;
import project.order.followup.entities.concretes.Product;
import project.order.followup.entities.dtos.ProductWithCategoryDto;

public interface ProductService {
	DataResult<List<Product>> getAllProducts();
	DataResult<List<Product>> getAllProductsSortedDesc();
	DataResult<List<Product>> getAllProductsByPage(int pageNo, int pageSize);
	Result add(Product product);
	Result update(Product product);
	Result deleteByProductId(int id);
	DataResult<Product> getByProductName(String productName);
	DataResult<Product> getByProductNameAndCategoryId(String productName, int categoryId);
	DataResult<List<Product>> getByProductNameOrCategoryId(String productName, int categoryId);
	DataResult<List<Product>> getByCategoryIdIn(List<Integer> categories);
	DataResult<List<Product>> getByProductNameContains(String productName);
	DataResult<List<Product>> getByProductNameStartsWith(String productName);
	
	DataResult<List<Product>> getByNameAndCategory(String productName, int categoryId);
	DataResult<List<ProductWithCategoryDto>> getProductWithCategoryDetails();
}
