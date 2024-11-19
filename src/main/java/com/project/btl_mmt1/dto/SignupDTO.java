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
public class SignupDTO {

    @JsonProperty("fullName")
    @NotBlank(message = "fullname is required")
    private String fullName;

    @JsonProperty("username")
    @NotBlank(message = "username is required")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Password can not be blank")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;
}
