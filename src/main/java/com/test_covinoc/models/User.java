package com.test_covinoc.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = ("users"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer id;

    @NotEmpty(message = "No puede estar vacío")
    @Column(nullable = false)
    private String phone;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min = 2, max= 20, message = "El tamaño tiene que estar entre 2 y 20")
    @Column(nullable = false, unique = true)
    private String name;
}
