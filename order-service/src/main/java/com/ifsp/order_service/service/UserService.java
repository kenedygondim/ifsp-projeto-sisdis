package com.ifsp.order_service.service;


import com.ifsp.order_service.dto.UserDTO;
import com.ifsp.order_service.entity.User;
import com.ifsp.order_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO createUser(UserDTO dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .nome(dto.getNome())
                .endereco(dto.getEndereco())
                .build();

        User saved = userRepository.save(user);
        return toDTO(saved);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .senha(user.getSenha())
                .nome(user.getNome())
                .endereco(user.getEndereco())
                .build();
    }
}