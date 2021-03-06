package ubb.gamestore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ubb.gamestore.core.domain.GSUser;
import ubb.gamestore.core.service.UserService;
import ubb.gamestore.core.service.UserServiceImpl;
import ubb.gamestore.web.converter.UserConverter;
import ubb.gamestore.web.dto.UserDTO;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<UserDTO> get(){
        List<GSUser> users = userService.getUsers();
        return userConverter.convertModelsToDtos(users);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserDTO saveUser(@RequestBody UserDTO userDTO){
        logger.trace("saveUser - UserController -> method entered, userDTO = {}", userDTO);
        Optional<GSUser> user1 = userService.getUserByUsername(userDTO.getUsername());
        Optional<GSUser> user2 = userService.getUserByEmailAddress(userDTO.getEmail());
        if(user1.isPresent() || user2.isPresent()){
            logger.trace("saveUser - UserController -> user was already present");
            return null;
        }
        GSUser addedUser = userService.save(userConverter.convertDtoToModel(userDTO));
        logger.trace("saveUser - UserController -> user added, addedUser = {}", addedUser);
        return userConverter.convertModelToDto(addedUser);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    UserDTO checkUser(@RequestBody String[] args){
        String username = args[0];
        String password = args[1];
        logger.trace("checkUser - UserController -> method entered, username = {}, password = {}", username, password);
        Optional<GSUser> gsUser = userService.getUserByUsername(username);
        if(gsUser.isPresent() && gsUser.get().getPassword().equals(password)){
            logger.trace("checkUser - UserController -> method entered, user = {}", gsUser);
            return userConverter.convertModelToDto(gsUser.get());
        }
        return null;
    }
}
