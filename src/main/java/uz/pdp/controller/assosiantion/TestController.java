package uz.pdp.controller.assosiantion;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.domain.Comment;
import uz.pdp.domain.PassportInfo;
import uz.pdp.domain.User;

import java.util.Date;
import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/test")
@Transactional
public class TestController {

    private final SessionFactory sessionFactory;

    @GetMapping("/user_with_password")
    public String saveUserWithPassport(){
        User user = User.builder()
                .name("alex")
                .password("1234")
                .build();
        /*sessionFactory.getCurrentSession().persist(user);
        System.out.println("[user.id] : " + user.getId());*/
        PassportInfo passportInfo = PassportInfo.builder()
                .firstName("Alex")
                .lastName("Kim")
                .birthDate(new Date())
                .expiryDate(new Date())
                .givenDate(new Date())
                .user(user)
                .build();
        sessionFactory.getCurrentSession()
                .persist(passportInfo);
        return passportInfo.toString();
    }

    @GetMapping("/users/{id}")
    public String getUserById(
            @PathVariable("id") Long id
    ){
        User user = sessionFactory.getCurrentSession()
                .get(User.class, id);
        System.out.println("[user-get-by-id] : " + user.getId()+  " - " + user.getName());
        System.out.println(user.getPassport());
        return user.toString();
    }

    @GetMapping("/passports/{id}")
    public String getPassportById(
            @PathVariable("id") Long id
    ){
        PassportInfo passportInfo = sessionFactory.getCurrentSession()
                .get(PassportInfo.class, id);
        System.out.println("[passport-get-by-id] : " + passportInfo.getId()+  " - " + passportInfo.getFirstName());
        System.out.println(passportInfo.getUser());
        return passportInfo.toString();
    }

    @GetMapping("/user_with_comments")
    public String saveUserAndComment(){
        User user = User.builder()
                .name("alex")
                .password("1234")
                .build();
        List<Comment> comments = List.of(
                Comment.builder().content("hello").time(new Date()).commentBy(user).build(),
                Comment.builder().content("hello1").time(new Date()).commentBy(user).build(),
                Comment.builder().content("hello2").time(new Date()).commentBy(user).build(),
                Comment.builder().content("hello3").time(new Date()).commentBy(user).build(),
                Comment.builder().content("hello4").time(new Date()).commentBy(user).build(),
                Comment.builder().content("hello5").time(new Date()).commentBy(user).build()
        );
        user.setComments(comments);
        sessionFactory.getCurrentSession()
                .persist(user);
        return user.toString();
    }

    @GetMapping("/comments/user/{id}/page/{page}/size/{size}")
    public String getCommentsByUserId(
            @PathVariable("id") Long userId,
            @PathVariable("id") Integer page,
            @PathVariable("id") Integer size
    ){
        Query<Comment> query = sessionFactory.getCurrentSession()
                .createQuery("select c from Comment c where c.commentBy.id =:userId", Comment.class)
                .setParameter("userId", userId);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Comment> list = query.getResultList();
        System.out.println("[list.size] " + list.size());
        return list.toString();
    }


}
