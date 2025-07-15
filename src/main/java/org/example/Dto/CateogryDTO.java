package org.example.Dto;

import lombok.Getter;
import org.example.model.Category;

@Getter
public class CateogryDTO {

    public Long id;
    public String title;
    public String icon;

    public CateogryDTO(Category category){
        this.id = category.getId();
        this.title = category.getTitle();
        this.icon = category.getIcon();
    }
}
