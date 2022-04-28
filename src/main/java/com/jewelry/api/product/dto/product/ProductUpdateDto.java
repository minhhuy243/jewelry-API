package com.jewelry.api.product.dto.product;

import com.jewelry.api.product.validation.annotation.ExistsCategoryCode;
import com.jewelry.api.product.validation.annotation.ExistsGoldType;
import com.jewelry.api.product.validation.annotation.ExistsProductSku;
import com.jewelry.api.product.validation.annotation.ExistsSupplierCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ProductUpdateDto {

    @ExistsProductSku
    private String sku;

    @NotBlank(message = "{product.name.not-blank}")
    @Size(min = 5, max = 100, message = "{product.name.size}")
    private String name;

    @Size(max = 1000, message = "{product.description.size}")
    private String description;

    private Double goldWeight;
    private Double costPrice;
    private Double price;
    private Integer quantity;

    private MultipartFile avatar;

    @ExistsSupplierCode
    private String supplierCode;

    @ExistsCategoryCode
    private String categoryCode;

    @ExistsGoldType
    private Double goldType;

    private List<MultipartFile> newImages;

    private List<String> imagesRemoved;
}
