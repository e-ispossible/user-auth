package com.salle.user.domain;

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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	@Column(name="email", unique=true)
	private String email;
	
	@NotNull
	@Column(name="password")
	private String password;
	
	@Column(name="user_role_id")
	private Long userRoleId;
	
	@JsonIgnore
	@Column(name="created_date", updatable=false)
	@Temporal(TemporalType.TIMESTAMP) //TIMESTAMP
	@CreatedDate
	private Date createdDate; //생성일시
	
	@JsonIgnore
	@Column(name="del_yn", columnDefinition="BIT(1)")
	private Boolean delYn;
}
