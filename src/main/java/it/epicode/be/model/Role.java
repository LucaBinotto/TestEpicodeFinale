package it.epicode.be.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "auth_roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "auth_roles_generator")
	@SequenceGenerator(name="auth_roles_generator", sequenceName = "auth_roles_seq")
	private Long id;
	public enum RoleType {
		ROLE_USER,
	    ROLE_ADMIN;
	}
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

}
