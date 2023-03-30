package com.Icwd.electronic.store.repositories;

import com.Icwd.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, String> {
}
