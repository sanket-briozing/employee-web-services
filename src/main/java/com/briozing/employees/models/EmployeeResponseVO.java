package com.briozing.employees.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
//json include
public class EmployeeResponseVO {

    private long id;

    private String name;

    private String email;

    private String country;

    private List<String> errors;
}
