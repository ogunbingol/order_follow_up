package project.order.followup.core.entities;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="u_id")
	private int id;
	
	@Column(name="u_name")
	@NotBlank
	@NotNull
	private String name;
	
	@Column(name="u_email")
	@Email
	@NotBlank
	@NotNull
	private String email;
	
	@Column(name="u_phone")
	@NotBlank
	@NotNull
	private String phone;
	
	@Column(name="u_password")
	@NotBlank
	@NotNull
	private String password;
	
	@Column(name="u_present")
	private int present;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	@OneToOne(mappedBy = "user")
    private Customer customer;
}
