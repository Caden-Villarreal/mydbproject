package com.example.mydbproject.controller;

import com.example.mydbproject.model.Inventory;
import com.example.mydbproject.model.Lens;
import com.example.mydbproject.repository.InventoryRepository;
import com.example.mydbproject.repository.LensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private LensRepository lensRepository;

    // LIST ALL INVENTORY ITEMS
    @GetMapping("")
    public String listItems(Model model) {
        model.addAttribute("items", inventoryRepository.findAll());
        return "items-list";
    }

    // SHOW FORM TO ADD OR EDIT ITEM
    @GetMapping({"/new", "/edit/{id}"})
    public String itemForm(@PathVariable(required = false) Long id, Model model) {
        Inventory inventory = (id != null) ?
                inventoryRepository.findById(id).orElse(new Inventory()) :
                new Inventory();

        List<Lens> lenses = lensRepository.findAll();
        model.addAttribute("inventory", inventory);
        model.addAttribute("lenses", lenses);

        return "item-form";
    }

    // SAVE OR UPDATE ITEM
    @PostMapping("/save")
    public String saveItem(@RequestParam(required = false) Long inventoryId,
                           @RequestParam Long lensId,
                           @RequestParam Integer stockQuantity,
                           @RequestParam String warehouseLocation) {

        Lens lens = lensRepository.findById(lensId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lens Id: " + lensId));

        Inventory inventory;
        if (inventoryId != null) {
            // Editing existing inventory
            inventory = inventoryRepository.findById(inventoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid inventory Id: " + inventoryId));
        } else {
            // New inventory
            inventory = new Inventory();
        }

        inventory.setLens(lens);
        inventory.setStockQuantity(stockQuantity);
        inventory.setWarehouseLocation(warehouseLocation);

        inventoryRepository.save(inventory);

        return "redirect:/inventory";
    }

    // DELETE ITEM
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        inventoryRepository.deleteById(id);
        return "redirect:/inventory";
    }

    // SEARCH BY WAREHOUSE LOCATION
    @GetMapping("/search")
    public String searchItems(@RequestParam(required = false) String q, Model model) {
        List<Inventory> results = (q == null || q.isEmpty()) ?
                inventoryRepository.findAll() :
                inventoryRepository.findByWarehouseLocationContainingIgnoreCase(q);

        model.addAttribute("items", results);
        model.addAttribute("q", q);
        return "items-search";
    }

}