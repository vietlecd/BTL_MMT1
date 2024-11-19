package com.project.btl_mmt1.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;

    private String username;

    private String password;

    private String fullName;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
