package com.husseinabdallah.camelspringbootudemy.beans;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "name_address")
public class NameAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "house_number")
    private String houseNumber;
    private String city;
    private String province;
    @Column(name = "postal_code")
    private String postalCode;
}
