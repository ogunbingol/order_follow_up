package project.order.followup.core.entities;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

@Data
@Entity
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="o_id")
	private int id;
	
	@Column(name="c_id")
	private int customerId;
	
	@Column(name="p_id")
	private int productId;
	
	@Column(name="o_date")
	@DateTimeFormat
	private String orderDate;
	
	@Column(name="o_number")
	private int orderNumber;
	
	@Column(name="o_present")
	private int present;
}
