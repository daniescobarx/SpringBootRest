package com.application.rest.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fabricante")
public class Maker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "o nome fabricante é obrigatório")
    @Column(name = "nome")
    private String name;

    //relacao
    @OneToMany(mappedBy = "maker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Product> productList = new ArrayList<>();


}
