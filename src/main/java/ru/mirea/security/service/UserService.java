package ru.mirea.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mirea.security.domain.UserAccount;
import ru.mirea.security.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount account = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }

}
