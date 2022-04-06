package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PosController {

    @Autowired
    private HttpSession session;

    private PosService posService;

    private Cart cart(){
        Cart c=(Cart)session.getAttribute("cart");
        if(c==null){
            c=new Cart();
            saveCart(c);
        }
        return c;
    }

    //@Autowired
    //public void setCart(Cart cart) {
    //    this.cart = cart;
    //}

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    public void saveCart(Cart cart){
        session.setAttribute("cart",cart);
    }

    @GetMapping("/")
    public String pos(Model model) {
        model.addAttribute("products", posService.products());
        System.out.println(cart().toString());
        System.out.println(posService.products());
        model.addAttribute("cart", cart());
        return "index";
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid, Model model) {
        posService.add(cart(), pid, 1);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart());
        return "index";
    }

    @GetMapping("/decrease")
    public String decrease(@RequestParam(name = "pid") String pid, Model model) {
        cart().decrease(pid,1);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart());
        return "index";
    }

    @GetMapping("/increase")
    public String increase(@RequestParam(name = "pid") String pid, Model model) {
        cart().increase(pid,1);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart());
        return "index";
    }

    @GetMapping("/del")
    public String del(@RequestParam(name = "pid") String pid, Model model) {
        cart().del(pid);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart());
        return "index";
    }

    @GetMapping("/subTotal")
    public String subTotal(Model model) {
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart());
        return "index";
    }

    @GetMapping("/cancel")
    public String cancel(Model model) {
        cart().clear();
        model.addAttribute("products", posService.products());
        model.addAttribute("cart",cart());
        return "index";
    }

    @GetMapping("/charge")
    public String charge(){
        return "pay";
    }

}
