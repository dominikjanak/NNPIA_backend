package cz.janakdom.service;

import cz.janakdom.dao.UserDao;
import cz.janakdom.model.database.User;
import cz.janakdom.model.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public boolean isEmailUnique(String email){
        return userDao.findByEmail(email) == null;
    }

    public void delete(int id) {
        userDao.deleteById(id);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findById(int id) {
        Optional<User> optionalUser = userDao.findById(id);
        return optionalUser.orElse(null);
    }

    public UserDto update(UserDto userDto) {
        User user = findById(userDto.getId());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password", "username");
            userDao.save(user);
        }
        return userDto;
    }

    public User save(UserDto user) {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser, "password");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setPassword(encoder.encode(user.getPassword()));

        return userDao.save(newUser);
    }
}
