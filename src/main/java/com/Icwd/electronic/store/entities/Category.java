package com.Icwd.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categoryies")
public class Category {
    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name ="category_title")
    private String title;
    @Column(name="category_desc")
    private String description;
    private String coverImage;

    //other attibutes if you have
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products= new ArrayList<>();
}
