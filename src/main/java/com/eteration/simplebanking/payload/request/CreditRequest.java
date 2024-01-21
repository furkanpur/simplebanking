package com.eteration.simplebanking.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreditRequest {

    /**
     * The amount of transaction.
     */
    @NotNull
    @NotEmpty
    @NotBlank
    private Double amount;
}
