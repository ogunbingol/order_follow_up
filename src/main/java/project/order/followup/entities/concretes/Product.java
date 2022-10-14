package project.order.followup.entities.concretes;

import javax.persistence.*;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="p_id")
	private int id;
	
	//@Column(name="cate_id")
	//private int categoryId;
	
	@Column(name="p_name")
	private String productName;
	
	@Column(name="p_unitprice")
	private int unitPrice;
	
	@Column(name="p_unitsinstock")
	private int unitsInStock;
	
	@ManyToOne()
	@JoinColumn(name="cate_id")
	private Category category;

	@Column(name="p_present")
	private int present;
}
