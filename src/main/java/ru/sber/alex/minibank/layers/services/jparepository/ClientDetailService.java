package ru.sber.alex.minibank.layers.services.jparepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sber.alex.minibank.config.ClientRoleEnum;
import ru.sber.alex.minibank.entities.ClientEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Расширенная реализация сервиса сущности БД "Клиент". Используется Spring Security.
 */
@Service
public class ClientDetailService implements UserDetailsService {

    @Autowired
    private ClientRepo clientRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ClientEntity client = clientRepo.findByLogin(s);
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(ClientRoleEnum.USER.name()));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(client.getLogin(), client.getPass(), roles);
        return userDetails;
    }
}
