package com.example.mydbproject.controller;

import com.example.mydbproject.model.Orders;
import com.example.mydbproject.model.ShippingInfo;
import com.example.mydbproject.repository.OrdersRepository;
import com.example.mydbproject.repository.ShippingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shipping")
public class ShippingInfoController {

    @Autowired
    private ShippingInfoRepository shippingInfoRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    // LIST ALL SHIPPING INFO
    @GetMapping("")
    public String listShipping(Model model) {
        List<ShippingInfo> shippingList = shippingInfoRepository.findAll();
        model.addAttribute("shippingList", shippingList);
        return "shipping-list";
    }

    // SHOW FORM TO ADD OR EDIT SHIPPING
    @GetMapping({"/new", "/edit/{id}"})
    public String shippingForm(@PathVariable(required = false) Long id, Model model) {
        ShippingInfo shipping = (id != null) ?
                shippingInfoRepository.findById(id).orElse(new ShippingInfo()) :
                new ShippingInfo();

        List<Orders> orders = ordersRepository.findAll();
        model.addAttribute("shipping", shipping);
        model.addAttribute("orders", orders);

        return "shipping-form";
    }

    // SAVE OR UPDATE SHIPPING INFO
    @PostMapping("/save")
    public String saveShipping(@ModelAttribute ShippingInfo shipping, @RequestParam Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id: " + orderId));

        shipping.setOrder(order);
        shippingInfoRepository.save(shipping);

        return "redirect:/shipping";
    }

    // DELETE SHIPPING INFO
    @GetMapping("/delete/{id}")
    public String deleteShipping(@PathVariable Long id) {
        shippingInfoRepository.deleteById(id);
        return "redirect:/shipping";
    }
}