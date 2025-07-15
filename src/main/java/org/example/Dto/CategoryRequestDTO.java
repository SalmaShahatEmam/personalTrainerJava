package org.example.Dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
public class CategoryRequestDTO {

    @NotBlank(message = "this field is required!")
    private String title_en;

    @NotBlank(message = "this field is required!")
    private String title_ar;

    @NotBlank(message = "this field is required!")
    private String icon;

    private Boolean active;
    private Boolean payed;

    /*
    {
    "title_en" : "math",
    "title_ar":"حساب",
    "icon":"icon",
    "active":true,
    "payed":true
    * */
}
