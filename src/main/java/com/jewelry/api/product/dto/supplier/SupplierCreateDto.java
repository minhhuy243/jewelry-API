package com.jewelry.api.product.dto.supplier;

import com.jewelry.api.product.validation.annotation.UniqueSupplierCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SupplierCreateDto {

    @UniqueSupplierCode
    private String code;

    @NotBlank(message = "{supplier.name.not-blank}")
    private String name;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "{supplier.phone-number.pattern}")
    private String phoneNumber;

    @Email(message = "{supplier.email.format}")
    private String email;

    private String address;
}
