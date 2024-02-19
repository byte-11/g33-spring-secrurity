package uz.pdp.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;

@Controller
public class AuthoritiesController {

    @ResponseBody
    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
//    @Secured({"ADMIN", "MANAGER"})
//    @RolesAllowed({"ADMIN", "MANAGER"})
    public String adminPage(){
        return "ADMIN PAGE";
    }

    @ResponseBody
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'MANAGER')")
//    @Secured({"ADMIN", "USER", "MANAGER"})
//    @RolesAllowed({"ADMIN", "USER", "MANAGER"})
    public String usersPage(){
        return "USERS PAGE";
    }

    @ResponseBody
    @GetMapping("/managers")
    @PreAuthorize("hasRole('MANAGER')")
//    @Secured("MANAGER")
//    @RolesAllowed({"MANAGER"})
    public String managerPage(){
        return "MANAGER PAGE";
    }

    @GetMapping("/admin_delete")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('DELETE')")
    public String deleteAdminPermission(){
        return "delete_admin_perm";
    }
}
