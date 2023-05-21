package com.cg.domain.esport.dto;

import com.cg.domain.esport.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    public Category toCategory(){
        return new Category()
                    .setId(id)
                    .setName(name);
    }
}
