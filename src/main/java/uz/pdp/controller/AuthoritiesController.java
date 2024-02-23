package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.config.sercurity.UserContext;

import javax.annotation.security.RolesAllowed;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authorities")
public class AuthoritiesController {

    private final UserContext userContext;

    @ResponseBody
    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
//    @Secured({"ADMIN", "MANAGER"})
//    @RolesAllowed({"ADMIN", "MANAGER"})
    public String adminPage(){
        return "ADMIN PAGE \n" + userContext.toString();
    }

    @ResponseBody
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'MANAGER')")
//    @Secured({"ADMIN", "USER", "MANAGER"})
//    @RolesAllowed({"ADMIN", "USER", "MANAGER"})
    public String usersPage(){
        return "USERS PAGE \n" + userContext.toString();
    }

    @ResponseBody
    @GetMapping("/managers")
    @PreAuthorize("hasRole('MANAGER')")
//    @Secured("MANAGER")
//    @RolesAllowed({"MANAGER"})
    public String managerPage(){
        return "MANAGER PAGE \n" + userContext.toString();
    }

    @GetMapping("/admin_delete")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('DELETE')")
    public String deleteAdminPermission(){
        return "delete_admin_perm \n" + userContext.toString();
    }
}
