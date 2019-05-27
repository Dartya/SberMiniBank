package ru.sber.alex.minibank.layers.services;

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

@Service
public class ClientDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ClientService clientService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ClientEntity client = clientService.getClient(s);
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(ClientRoleEnum.USER.name()));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(client.getLogin(), client.getPass(), roles);
        return userDetails;
    }
}
