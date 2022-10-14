package project.order.followup.entities.concretes;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="categories")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","products"})
public class Category {
	
	@Id
	@Column(name="cate_id")
	private int categoryId;
	
	@Column(name="cate_name")
	private String categoryName;
	
	@OneToMany(mappedBy="category")
	private List<Product> products;
	
}
