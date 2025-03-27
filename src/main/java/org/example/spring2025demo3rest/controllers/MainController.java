package org.example.spring2025demo3rest.controllers;

import org.example.spring2025demo3rest.dataaccess.HomeRepository;
import org.example.spring2025demo3rest.dataaccess.UserRepository;
import org.example.spring2025demo3rest.pojos.Home;
import org.example.spring2025demo3rest.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

/**
 * The main controller for this application. Controllers can be split by the base URL in the request mapping
 */
@Controller
@RequestMapping(path = RESTNouns.VERSION_1)
public class MainController {

    //Wire the ORM
    @Autowired private UserRepository userRepository;
    @Autowired
    private HomeRepository homeRepository;

    /**
     * Get Mapping for all users
     */
    @GetMapping(path = RESTNouns.USER)
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get for a user
     * @param userId
     * @return
     */
    @GetMapping(path = RESTNouns.USER + RESTNouns.ID)
    public @ResponseBody Optional<User> getUser(@PathVariable("id") Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Post Mapping for a new users
     * @param name name
     * @param email email
     * @return
     */
    @PostMapping(path = RESTNouns.USER)
    public @ResponseBody User createUser(
            @RequestParam String name, @RequestParam String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    /**
     * Delete Mapping for a user by ID
     * @param userId The ID of the user to delete
     * @return A response indicating success or failure
     */
    @DeleteMapping(path = RESTNouns.USER + RESTNouns.ID)
    public @ResponseBody String deleteUser(@PathVariable("id") Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return "User with ID " + userId + " deleted successfully.";
        } else {
            return "User with ID " + userId + " not found.";
        }
    }

    /**
     * Put mapping for user
     * @param userId
     * @param name
     * @param email
     * @return
     */
    @PutMapping(path = RESTNouns.USER + RESTNouns.ID)
    public @ResponseBody String updateUser(
            @PathVariable("id") Long userId, @RequestParam String name, @RequestParam String email){
        if (userRepository.existsById(userId)) {
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                user.get().setName(name);
                user.get().setEmail(email);
            }
            userRepository.save(user.get());
            return "User with ID " + userId + " updated successfully.";
        } else {
            return "User with ID " + userId + " not found.";
        }

    }

    /**
     * Get all homes for a specific User
     */
    @GetMapping(path = RESTNouns.USER +  RESTNouns.ID + RESTNouns.HOME)
    public @ResponseBody Iterable<Home> getAllHomesByUser(@PathVariable("id") Long userId) {
        Iterable<Home> homes = null;
        if (userRepository.existsById(userId)) {
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
               // homeRepository

                homes = homeRepository.getAllByUserId(userId);
            }
        }

        //TODO handle errors

        return homes;
    }

    /**
     * Create a home for a user
     * @param userId user id
     * @param dateBuilt date built
     * @param value value of the home as an int
     * @return
     */
    @PostMapping(path = RESTNouns.USER + RESTNouns.ID + RESTNouns.HOME)
    public @ResponseBody Home createHomeByUser(
            @PathVariable("id") Long userId,
            @RequestParam LocalDate dateBuilt, @RequestParam int value) {
//
        Home home = null;
        if (userRepository.existsById(userId)) {
            Optional<User> user = userRepository.findById(userId);

            home = new Home();
            home.setValue(value);
            home.setDateBuilt(dateBuilt);
            home.setUser(user.get());
            homeRepository.save(home);

        }

        //TODO handle error codes

        return home;
    }

    /**
     * Put mapping for home
     * @param userId user id
     * @param dateBuilt date built
     * @param value value of the home as an int
     */
    @PutMapping(path = RESTNouns.USER + RESTNouns.USER_ID + RESTNouns.HOME + RESTNouns.HOME_ID)
    public @ResponseBody String updateHomeByUser(
            @PathVariable("user_id") Long userId, @PathVariable("home_id") Long homeId, @RequestParam LocalDate dateBuilt, @RequestParam int value){
        if (userRepository.existsById(userId) && homeRepository.existsById(homeId)) {
            Optional<User> user = userRepository.findById(userId);
            Optional<Home> home = homeRepository.findById(homeId);
            if(user.isPresent() && home.isPresent()){
//                user.get().setName(name);
//                user.get().setEmail(email);
                home.get().setDateBuilt(dateBuilt);
                home.get().setValue(value);

                homeRepository.save(home.get());
                userRepository.save(user.get());
            }
            return "Home with ID " + homeId + " updated successfully.";
        } else {
            return "Home with ID " + homeId + " not found.";
        }

    }

}
