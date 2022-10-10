package com.gok.globalsuperstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gok.globalsuperstore.Item;
import com.gok.globalsuperstore.service.StoreService;

@Controller
public class StoreController {

    @Autowired
    StoreService storeService;   
    
    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false)String id ){  

        model.addAttribute("item", storeService.getItemById(id));
        return "form";
    }

    @GetMapping("/inventory")
    public String getInventory(Model model){
        model.addAttribute("items", storeService.getItems());
        return "inventory";
    }

    @PostMapping("/submitItem")
    public String handleSubmit(@Valid Item item, BindingResult result, RedirectAttributes redirectAttributes){
        if(item.getPrice() < item.getDiscount()){
            result.rejectValue("price", "", "Price can not be less than Discount");
        }
        if(result.hasErrors()){
            return "form";
        }
    
        String status= storeService.submitItem(item);
        
        redirectAttributes.addFlashAttribute("status", status);
        return "redirect:/inventory";
    }
    
}
