package com.jipvio.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name="user_role_id")
	private Long userRoleId;
	
	@NotNull
	@Column(name="user_type_id")
	private Long userTypeId;
	
	@NotNull
	@Column(name="email", unique=true)
	private String email;
	
	@NotNull
	@Column(name="password")
	private String password;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@Column(name="birth_dt")
	private Date birthDate;
	
	@Column(name="gender")
	private String gender;
	
	@JsonIgnore
	@Column(name="reg_dt", updatable=false)
	@Temporal(TemporalType.TIMESTAMP) //TIMESTAMP
	@CreatedDate
	private Date createdDate; //생성일시
	
	@Column(name="upd_dt")
	@Temporal(TemporalType.TIMESTAMP) //TIMESTAMP
	@UpdateTimestamp
	private Date updatedDate; //생성일시
	
	@JsonIgnore
	@Column(name="del_yn", columnDefinition="BIT(1)")
	private Boolean delYn = Boolean.FALSE;
}