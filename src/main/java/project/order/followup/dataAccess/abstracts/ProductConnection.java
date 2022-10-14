package project.order.followup.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import project.order.followup.core.utilities.results.Result;
import project.order.followup.entities.concretes.Product;
import project.order.followup.entities.dtos.ProductWithCategoryDto;

public interface ProductConnection extends JpaRepository<Product, Integer>{
	Product getByProductName(String productName);
	Product getByProductNameAndCategory_CategoryId(String productName, int categoryId);
	List<Product> getByProductNameOrCategory_CategoryId(String productName, int categoryId);
	List<Product> getByCategory_CategoryIdIn(List<Integer> categories);
	List<Product> getByProductNameContains(String productName);
	List<Product> getByProductNameStartsWith(String productName);
	
	@Query("from Product where productName=:productName and category.categoryId=:categoryId")
	List<Product> getByNameAndCategory(String productName, int categoryId);
	
	@Query("select new project.order.followup.entities.dtos.ProductWithCategoryDto(p.id, p.productName, c.categoryName) from Category c Inner Join c.products p")
	List<ProductWithCategoryDto> getProductWithCategoryDetails();
	
	@Query("delete from Product where id=:id")
	Result deleteByProductId(int id);
}
