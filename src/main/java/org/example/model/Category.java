package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

@Entity
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title_en;
    private String title_ar;
    private String icon;
    private Boolean active = true;
    private Boolean payed = false;


    //store the image

    private String img_type;
    private String img_name;
    @Lob
    private byte[] img_data;

    public String getTitle() {
        String currentLang = LocaleContextHolder.getLocale().getLanguage();
        return currentLang.equals("ar") ? title_ar : title_en;
    }

    @OneToMany( mappedBy = "category")
    private List<Product> products;
}
