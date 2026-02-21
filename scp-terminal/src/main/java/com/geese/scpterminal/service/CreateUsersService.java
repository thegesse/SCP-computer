package com.geese.scpterminal.service;

import com.geese.scpterminal.model.Users;
import org.springframework.stereotype.Service;

@Service
public class CreateUsersService {

    public Users dClassPersonel(){
        return new Users("David", "1234", "Level 0");
    }

    public Users researcherPersonel(){
        return new Users("Dr.Dark", "BrightSux", "Level 3");
    }

    public Users o5CouncilMember(){
        return new Users("O5-3", "IamErgoCogito", "Level 5");
    }
}
