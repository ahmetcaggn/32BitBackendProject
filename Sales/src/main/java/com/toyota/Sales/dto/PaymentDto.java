package com.toyota.Sales.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PaymentDto {
    private float cashAmount;
    private float creditCardAmount;
}
