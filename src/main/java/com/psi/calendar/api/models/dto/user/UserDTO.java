package com.psi.calendar.api.models.dto.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private Boolean enabled;
    private List<String> roles;
}
