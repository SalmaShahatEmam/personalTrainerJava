package org.example.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="products")
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue()
    private int id;

    @Column(unique = true)
    private String slug;
    private String name;
    private float price ;

    //store the image

    private String img_type;
    private String img_name;
    @Lob
    private byte[] img_data;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
}
