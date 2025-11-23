package com.example.mydbproject.controller;

import com.example.mydbproject.model.Orders;
import com.example.mydbproject.model.OrderLineItem;
import com.example.mydbproject.model.Customer;
import com.example.mydbproject.model.Lens;
import com.example.mydbproject.model.Inventory;
import com.example.mydbproject.repository.OrdersRepository;
import com.example.mydbproject.repository.OrderLineItemRepository;
import com.example.mydbproject.repository.CustomerRepository;
import com.example.mydbproject.repository.LensRepository;
import com.example.mydbproject.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LensRepository lensRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    // LIST ALL ORDERS
    @GetMapping("")
    public String listOrders(Model model) {
        model.addAttribute("orders", ordersRepository.findAll());
        return "orders-list";
    }

    // SHOW FORM FOR ADD/EDIT
    @GetMapping({"/new", "/edit/{id}"})
    public String orderForm(@PathVariable(required = false) Long id, Model model) {
        Orders order = (id != null) ? ordersRepository.findById(id).orElse(new Orders()) : new Orders();

        if (order.getLineItems() == null) {
            order.setLineItems(new ArrayList<>());
        }

        model.addAttribute("order", order);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("lenses", lensRepository.findAll());

        return "order-form";
    }

    // SAVE OR UPDATE ORDER
    @PostMapping("/save")
    public String saveOrder(@RequestParam Long customerId,
                            @RequestParam Long lensId,
                            @RequestParam int quantity,
                            @RequestParam double price,
                            @RequestParam(required = false) Long orderId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id: " + customerId));

        Lens lens = lensRepository.findById(lensId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lens Id: " + lensId));

        Inventory inventory = inventoryRepository.findAll().stream()
                .filter(i -> i.getLens().getLensId().equals(lens.getLensId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for lens ID: " + lensId));

        Orders order;
        OrderLineItem lineItem;

        if (orderId != null) {
            // EDIT existing order
            order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid order Id: " + orderId));

            if (order.getLineItems().isEmpty()) {
                lineItem = new OrderLineItem();
                order.getLineItems().add(lineItem);
            } else {
                lineItem = order.getLineItems().get(0);
            }

            int diff = quantity - lineItem.getQuantity();
            if (inventory.getStockQuantity() < diff) {
                throw new IllegalArgumentException("Not enough stock for lens ID: " + lensId);
            }

            lineItem.setQuantity(quantity);
            lineItem.setPrice(price);
            lineItem.setLens(lens);
            lineItem.setOrder(order);
            inventory.setStockQuantity(inventory.getStockQuantity() - diff);

        } else {
            // NEW order
            order = new Orders();
            order.setOrderDate(LocalDate.now());
            order.setLineItems(new ArrayList<>());

            lineItem = new OrderLineItem();
            lineItem.setQuantity(quantity);
            lineItem.setPrice(price);
            lineItem.setLens(lens);
            lineItem.setOrder(order);

            order.getLineItems().add(lineItem);
            inventory.setStockQuantity(inventory.getStockQuantity() - quantity);
        }

        order.setCustomer(customer);
        order.setTotalAmount(quantity * price);

        ordersRepository.save(order);
        orderLineItemRepository.save(lineItem);
        inventoryRepository.save(inventory);

        return "redirect:/orders";
    }

    // DELETE ORDER
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id: " + id));

        if (order.getLineItems() != null) {
            for (OrderLineItem line : order.getLineItems()) {
                Inventory inv = inventoryRepository.findAll().stream()
                        .filter(i -> i.getLens().getLensId().equals(line.getLens().getLensId()))
                        .findFirst().orElse(null);

                if (inv != null) {
                    inv.setStockQuantity(inv.getStockQuantity() + line.getQuantity());
                    inventoryRepository.save(inv);
                }

                orderLineItemRepository.delete(line);
            }
        }

        ordersRepository.delete(order);
        return "redirect:/orders";
    }
}