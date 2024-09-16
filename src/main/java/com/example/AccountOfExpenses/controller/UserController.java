package com.example.AccountOfExpenses.controller;

import com.example.AccountOfExpenses.form.UserForm;
import com.example.AccountOfExpenses.domain.User;
import com.example.AccountOfExpenses.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping("/register-user-information")
    public String insert(@Valid UserForm userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userForm", userForm);
            return "register";
        }

        User user = userService.findByEmail(userForm.getEmail());
        if (user != null) {
            model.addAttribute("error", "このメールアドレスはすでに登録されています");
            return "register";
        }

        User newUser = new User();
        newUser.setUsername(userForm.getUsername());
        newUser.setEmail(userForm.getEmail());
        newUser.setPassword(userForm.getPassword()); // パスワード暗号化が必要

        userService.insert(newUser);
        return "redirect:/login";
    }

    @PostMapping("/login-to-my-page")
    public String loginSuccessRedirect(UserForm userForm, Model model) {
        String email = userForm.getEmail();
        String password = userForm.getPassword();

        User authenticatedUser = userService.findByEmailAndPassword(email, password);
        if (authenticatedUser != null) {
            session.setAttribute("username", authenticatedUser.getUsername());
            session.setAttribute("id", authenticatedUser.getId());
            return "redirect:/to-my-page";
        } else {
            model.addAttribute("errorMessage", "メールアドレスまたはパスワードが間違っています");
            return "login";
        }
    }

    @GetMapping("/to-registration-page")
    public String toRegisterPage(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @GetMapping("/login")
    public String toLoginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "メールアドレスまたはパスワードが間違っています");
        }
        model.addAttribute("userForm", new UserForm());
        return "login";
    }

    @GetMapping("/to-my-page")
    public String toMyPage(HttpSession session, Model model) {
        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return "redirect:/login";
        }
        model.addAttribute("id", id);
        return "my-page";
    }
}
