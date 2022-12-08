package com.husseinabdallah.camelspringbootudemy.beans;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name = "name_address")
@NamedQuery(name = "fetchAllRows", query="select x from NameAddress x")
public class NameAddress implements Serializable {

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
