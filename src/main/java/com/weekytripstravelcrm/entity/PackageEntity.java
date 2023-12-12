package com.weekytripstravelcrm.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table
@Getter
@Setter
public class PackageEntity {

	@Id
	@Column(name="package_id")
	private String packageId;
	@Column
	private String packageName;
	@Column
	private String city;
	@Column
	private String description;
	@Column
	private Integer travelDays;
	
	@OneToMany(mappedBy = "packageEntity",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
	@Column
	private List<ItineraryEntity> itinery;
	
	/*@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "trip_id")
	private TripEntity trip;*/
	@Column
	private String image;

	@Column
	private String inclusions;
	@Column
	private String exclusions;
	@Column
	private String CancellationPolicy;
	@Column
	private String importantNotes;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private Double totalPackageCost;
	@Column
	private Double commissionPercent;
	@Column
	private Double discountPercent;
}
