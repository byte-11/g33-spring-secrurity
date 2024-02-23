package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.domain.User;
import uz.pdp.dto.UserUpdateDto;
import uz.pdp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public String getUserById(
            @PathVariable("id") Long id
    ){
        return userService.findById(id).toString();
    }

    @PutMapping
    public String update(
            @RequestBody UserUpdateDto updateDto
            ){
        return userService.updateUser(updateDto).toString();
    }

    @GetMapping("/delete/{id}")
    public String  delete(
        @PathVariable("id") Long id
    ){
        userService.delete(id);
        return Boolean.TRUE.toString();
    }

    @GetMapping
    @ResponseBody
    public String getAll(){
        return userService.getAll().toString();
    }
}
