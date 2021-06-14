package com.lidiane.openet;

import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {
    @GetMapping("/")
    public ModelAndView paginainicial(){
        return new ModelAndView("index");
    }
    @GetMapping("/restrito")
    public ModelAndView restrito(HttpServletRequest request){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;    

        if (principal instanceof UserDetails) {
            nome = ((UserDetails)principal).getUsername();
        } else {
            nome = principal.toString();
        }

        User usr = new User(nome);
        UserDao userDao = new UserDao();
        userDao.addUser(usr);

        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        
        //return "Logado com o usuário: "+nome;
        return new ModelAndView("index");
        
    }


    @GetMapping("/fila")
    public ModelAndView fila(ModelMap model){
        User u;
        UserDao userDao = new UserDao();
        u = userDao.getUser();
        model.addAttribute("nome", u.nome);
        return new ModelAndView("/fila", model);
    }

    @GetMapping("/pool")
    public ModelAndView delfila(ModelMap model){
        User u;
        UserDao userDao = new UserDao();
        u = userDao.delUser();
        
        return new ModelAndView("index");
       
        
    }
    @GetMapping("/todos")
    public ModelAndView todos(ModelMap model){
        Queue<User> users = new LinkedList<>();
        UserDao userDao = new UserDao();
        users = userDao.verUsers();
        if (users != null){
            model.addAttribute("usuarios", users);
            return new ModelAndView("/todos", model);
        }else{
            return new ModelAndView("index");
        }
       
        
    }

    

}
