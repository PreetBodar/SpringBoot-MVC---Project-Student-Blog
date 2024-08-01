package BlogAcadamy.BlogAcadamy.controllers;

import BlogAcadamy.BlogAcadamy.Entity.Post;
import BlogAcadamy.BlogAcadamy.Entity.User;
import BlogAcadamy.BlogAcadamy.services.PostService;
import BlogAcadamy.BlogAcadamy.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.validation.Validator;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    static User currentUser;

    @GetMapping("/")
    public ModelAndView landing()
    {
        ModelAndView mv = new ModelAndView("landingPage");
        mv.addObject("note","Login");
        return mv;
    }

    @PostMapping("/logIn")
    public ModelAndView checkLogIn(HttpServletRequest request)
    {
        User user = new User(request.getParameter("username"),request.getParameter("password"),request.getParameter("role"));
        String validity = userService.checkUser(user);
        if(validity.equals("valid"))
        {
            currentUser = user;
            if(currentUser.getRole().equals("student"))
                return loadStudentHome();
            else
                return loadTeacherHome();
        }
        ModelAndView mv = new ModelAndView("landingPage");
        mv.addObject("note","Invalid Credentials");
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView register(HttpServletRequest request)
    {
        User user = new User(request.getParameter("username"),request.getParameter("password"),request.getParameter("role"));
        String check = userService.checkDuplicate(user);
        ModelAndView mv = new ModelAndView("LandingPage");
        mv.addObject("note","Username Already exists, Try new one.");
        if(check.equals("new")){
            userService.saveUser(user);
            mv.addObject("note","Registered Successfully ! Login ..");
        }
        return mv;
    }

    public ModelAndView loadStudentHome()
    {
        ModelAndView home=new ModelAndView("StudentHome");
        List<Post> posts = postService.getAllPost();
        home.addObject("posts",posts);
        return home;
    }

    public ModelAndView loadTeacherHome()
    {
        ModelAndView home=new ModelAndView("TeacherHome");
        List<Post> posts = postService.getAllPost();
        home.addObject("posts",posts);
        return home;
    }

    @PostMapping("/homePage")
    public ModelAndView reloadTeacherPage(HttpServletRequest request)
    {
       int id = Integer.parseInt(request.getParameter("postId"));
        System.out.println(id);
        Post post =  postService.getPost(id);
        String liking = request.getParameter("liking");
        if(liking.equals("like"))
            postService.addLike(post);
        else
            postService.addDislike(post);
        String comment = request.getParameter("comment");
        postService.addComment(post,comment);
        return loadTeacherHome();
    }

    @PostMapping("/Student")
    public ModelAndView reloadStudentPage(HttpServletRequest request)
    {
        Post post = new Post(currentUser.getUsername(),request.getParameter("title"),request.getParameter("content"));
        postService.savePost(post);
        return loadStudentHome();
    }

}
