package com.nimel.mymicroservices.beerorderservice.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(generator="UUID")
	private UUID id;
	@Version
	private Long version;
	@CreationTimestamp
	private Timestamp createdDate;
	@UpdateTimestamp
	private Timestamp lastModifiedDate;
	
	public boolean isNew() {
		return this.id == null;
	}

}
