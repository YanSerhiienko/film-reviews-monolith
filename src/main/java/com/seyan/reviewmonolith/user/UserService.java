package com.seyan.reviewmonolith.user;

import com.seyan.reviewmonolith.user.dto.PageableUserResponseDTO;
import com.seyan.reviewmonolith.user.dto.UserCreationDTO;
import com.seyan.reviewmonolith.user.dto.UserUpdateDTO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User createUser(UserCreationDTO dto);

    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    User updateUser(UserUpdateDTO dto, Long id);

    void deleteUser(Long id);

    PageableUserResponseDTO getAllUsersPageable(int pageNo, int pageSize);
}
