package com.geese.scpterminal.service;

import com.geese.scpterminal.model.Users;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final CreateUsersService createUsersService;

    public AuthService(CreateUsersService createUsersService) {
        this.createUsersService = createUsersService;
    }

    public Optional<Users> login(String username, String password) {
        List<Users> personnel = List.of(
                createUsersService.dClassPersonel(),
                createUsersService.researcherPersonel(),
                createUsersService.o5CouncilMember()
        );
        return personnel.stream()
                .filter(users -> users.getUsername().equals(username) && users.getPassword().equals(password))
                .findFirst();
    }
}
