package project.order.followup.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import project.order.followup.business.abstracts.ProductService;
import project.order.followup.core.utilities.results.DataResult;
import project.order.followup.core.utilities.results.ErrorResult;
import project.order.followup.core.utilities.results.Result;
import project.order.followup.core.utilities.results.SuccessDataResult;
import project.order.followup.core.utilities.results.SuccessResult;
import project.order.followup.dataAccess.abstracts.ProductConnection;
import project.order.followup.entities.concretes.Product;
import project.order.followup.entities.dtos.ProductWithCategoryDto;

@Service
public class ProductManager implements ProductService{

	private ProductConnection productConnection;
	
	@Autowired
	public ProductManager(ProductConnection productConnection) {
		super();
		this.productConnection = productConnection;
	}


	@Override
	public DataResult<List<Product>> getAllProducts() {
		//Sort sort=Sort.by(Sort.Direction.ASC,"id");
		List<Product> products = new ArrayList<Product>();
		for(int i=0;i<this.productConnection.count();i++) {
			if(this.productConnection.findAll().get(i).getPresent()==1) {
				products.add(this.productConnection.findAll().get(i));
			}
			else {
				
			}
		}		
		return new SuccessDataResult<List<Product>>(products,"Data Listelendi");
	}


	@Override
	public Result add(Product product) {
		this.productConnection.save(product);
		return new SuccessResult("Ürün eklendi");
	}


	@Override
	public DataResult<Product> getByProductName(String productName) {
		return new SuccessDataResult<Product>(this.productConnection.getByProductName(productName),"Data Listelendi");
	}


	@Override
	public DataResult<Product> getByProductNameAndCategoryId(String productName, int categoryId) {
		return new SuccessDataResult<Product>(this.productConnection.getByProductNameAndCategory_CategoryId(productName,categoryId),"Data Listelendi");
	}


	@Override
	public DataResult<List<Product>> getByProductNameOrCategoryId(String productName, int categoryId) {
		return new SuccessDataResult<List<Product>>(this.productConnection.getByProductNameOrCategory_CategoryId(productName,categoryId),"Data Listelendi");
		
	}


	@Override
	public DataResult<List<Product>> getByCategoryIdIn(List<Integer> categories) {
		return new SuccessDataResult<List<Product>>(this.productConnection.getByCategory_CategoryIdIn(categories),"Data Listelendi");
		
	}


	@Override
	public DataResult<List<Product>> getByProductNameContains(String productName) {
		return new SuccessDataResult<List<Product>>(this.productConnection.getByProductNameContains(productName),"Data Listelendi");
		
	}


	@Override
	public DataResult<List<Product>> getByProductNameStartsWith(String productName) {
		return new SuccessDataResult<List<Product>>(this.productConnection.getByProductNameStartsWith(productName),"Data Listelendi");
		
	}


	@Override
	public DataResult<List<Product>> getByNameAndCategory(String productName, int categoryId) {
		return new SuccessDataResult<List<Product>>(this.productConnection.getByNameAndCategory(productName,categoryId),"Data Listelendi");
		
	}


	@Override
	public DataResult<List<Product>> getAllProductsByPage(int pageNo, int pageSize) {
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		return new SuccessDataResult<List<Product>>(this.productConnection.findAll(pageable).getContent());
	}


	@Override
	public DataResult<List<Product>> getAllProductsSortedDesc() {
		Sort sort=Sort.by(Sort.Direction.DESC,"productName");
		return new SuccessDataResult<List<Product>>(this.productConnection.findAll(sort),"Başarılı");
	}


	@Override
	public DataResult<List<ProductWithCategoryDto>> getProductWithCategoryDetails() {
		return new SuccessDataResult<List<ProductWithCategoryDto>>(this.productConnection.getProductWithCategoryDetails(),"Data Listelendi");
	}


	@Override
	public Result deleteByProductId(int id) {
		Optional<Product> optionalProduct  = this.productConnection.findById(id);
		if(optionalProduct.isPresent()) {
			optionalProduct.get().setPresent(0);
			this.productConnection.save(optionalProduct.get());
			return new SuccessResult("Ürün Silindi");
		}else{
			return new ErrorResult("Ürün Bulunamadı");
		}
		/*this.productConnection.deleteById(id);
		return new SuccessResult("Ürün silindi");*/
	}
	
	@Override
	public Result update(Product product) {
		if(this.productConnection.findById(product.getId()).get().getPresent()==1) {
			if(product.getProductName().equals("string")) {
				product.setProductName(this.productConnection.findById(product.getId()).get().getProductName());
			}
			if(product.getUnitPrice()==0) {
				product.setUnitPrice(this.productConnection.findById(product.getId()).get().getUnitPrice());
			}
			if(product.getUnitsInStock()==0) {
				product.setUnitsInStock(this.productConnection.findById(product.getId()).get().getUnitsInStock());
			}
			product.setPresent(1);
			product.setCategory(this.productConnection.findById(product.getId()).get().getCategory());
			this.productConnection.save(product);
			return new SuccessResult("Ürün Güncellendi");
		}else {
			if(product.getPresent()==0) {
				return new ErrorResult("Uyarı: Silinmiş Ürün!");
			}else {
				product.setProductName(this.productConnection.findById(product.getId()).get().getProductName());
				product.setUnitPrice(this.productConnection.findById(product.getId()).get().getUnitPrice());
				product.setUnitsInStock(this.productConnection.findById(product.getId()).get().getUnitsInStock());
				product.setCategory(this.productConnection.findById(product.getId()).get().getCategory());
				product.setPresent(1);
				this.productConnection.save(product);
				return new SuccessResult("Ürün yeniden stokta!");
			}
		}
		/*this.productConnection.save(product);
		return new SuccessResult("Ürün güncellendi");*/
	}

}
