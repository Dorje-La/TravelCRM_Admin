package com.weekytripstravelcrm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    @Column
    private String street;
    @Column
    private String apt;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String pinCode;

}
