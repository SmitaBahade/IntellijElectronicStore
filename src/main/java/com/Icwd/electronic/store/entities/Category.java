package com.Icwd.electronic.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
