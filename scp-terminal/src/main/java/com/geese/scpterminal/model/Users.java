package com.geese.scpterminal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {
    private String username;
    private String password;
    private String clearanceLevel;
}
