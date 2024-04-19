package com.application.rest.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Produto")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "preco")
    private BigDecimal price;

    //msm nome p relacao
    @ManyToOne
    @JoinColumn(name = "id_fabricante", nullable = false)
    private Maker maker;
}
