package com.husseinabdallah.camelspringbootudemy.beans;

import lombok.Data;

@Data
public class NameAddress {
    private String name;
    private String houseNumber;
    private String city;
    private String province;
    private String postalCode;
}
