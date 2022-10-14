package project.order.followup.core.entities;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="c_id")
	private int id;
	
	@Column(name="c_companyname")
	private String name;
	
	@OneToOne(cascade = {CascadeType.DETACH})
	@JoinColumn(name="u_id")
	private User user;
	
	/*@Column(name="c_contactuserid")
	private int contactuserid;*/
	
	@Column(name="c_present")
	private int present;
}
