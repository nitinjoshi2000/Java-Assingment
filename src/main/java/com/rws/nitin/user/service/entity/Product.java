package com.rws.nitin.user.service.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;
    private String productName;
    private Integer productPrice;
    private String productColor;
    private String productImage;

    @ManyToOne
    private User user;

}
