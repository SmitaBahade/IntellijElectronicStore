package com.Icwd.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {
    private String productId;
    private String title;

    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;

    private Date addedDate;
    private boolean live;
    private boolean stock;
}
