package com.cts.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PharmacyUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty(access = Access.READ_WRITE, required = true)
	@NotEmpty
	@Column(unique = true, insertable = true, updatable = false)
	private String username;

	@JsonProperty(access = Access.WRITE_ONLY, required = true)
	@NotEmpty
	@Column(insertable = true, updatable = true)
	private String password;

	@JsonProperty(access = Access.READ_WRITE, required = false)
	@Column(insertable = true, updatable = true)
	@Default
	private Boolean active = true;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Default
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles = new ArrayList<>();

}
