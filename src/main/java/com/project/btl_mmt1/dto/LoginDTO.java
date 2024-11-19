package com.project.btl_mmt1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data //toString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {

    @JsonProperty("username")
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "Password can not be blank")
    private String password;
}
