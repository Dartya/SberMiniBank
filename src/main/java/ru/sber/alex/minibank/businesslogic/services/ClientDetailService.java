package ru.sber.alex.minibank.businesslogic.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sber.alex.minibank.app.config.ClientRoleEnum;
import ru.sber.alex.minibank.data.entities.ClientEntity;
import ru.sber.alex.minibank.data.jparepository.ClientRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Расширенная реализация сервиса сущности БД "Клиент". Используется Spring Security.
 */
@Service
@Slf4j
public class ClientDetailService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ClientEntity client = clientRepository.findByLogin(s);
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(ClientRoleEnum.USER.name()));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(client.getLogin(), client.getPass(), roles);
        return userDetails;
    }
}
