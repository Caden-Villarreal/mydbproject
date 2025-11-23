package com.example.mydbproject.controller;

import com.example.mydbproject.model.Inventory;
import com.example.mydbproject.model.Lens;
import com.example.mydbproject.repository.InventoryRepository;
import com.example.mydbproject.repository.LensRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/items")
public class ItemsController {

    private final InventoryRepository inventoryRepository;
    private final LensRepository lensRepository;

    public ItemsController(InventoryRepository inventoryRepository, LensRepository lensRepository) {
        this.inventoryRepository = inventoryRepository;
        this.lensRepository = lensRepository;
    }

    @GetMapping
    public String listItems(Model model) {
        model.addAttribute("items", inventoryRepository.findAll());
        return "items-list";
    }

    @GetMapping("/new")
    public String newItemForm(Model model) {
        model.addAttribute("item", new Inventory());
        model.addAttribute("lenses", lensRepository.findAll());
        return "item-form";
    }

    @GetMapping("/{id}/edit")
    public String editItemForm(@PathVariable Long id, Model model) {
        Optional<Inventory> opt = inventoryRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/items";
        }
        model.addAttribute("item", opt.get());
        model.addAttribute("lenses", lensRepository.findAll());
        return "item-form";
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute Inventory item,
                           @RequestParam(required = false) Long lensId) {

        if (lensId != null) {
            Optional<Lens> optLens = lensRepository.findById(lensId);
            optLens.ifPresent(item::setLens);
        } else {
            item.setLens(null);
        }

        inventoryRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
        }
        return "redirect:/items";
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam(required = false) String q, Model model) {
        if (q == null || q.isEmpty()) {
            model.addAttribute("items", inventoryRepository.findAll());
        } else {
            model.addAttribute("items",
                    inventoryRepository.findByWarehouseLocationContainingIgnoreCase(q));
        }
        model.addAttribute("q", q);
        return "items-search";
    }
}