package com.rws.nitin.user.service.payloads;

import com.rws.nitin.user.service.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private Integer productId;
    @NotEmpty
    private String productName;
    @NotEmpty
    private Integer productPrice;
    @NotEmpty
    private String productColor;
    private String productImage;
    private UserDto user;
}
