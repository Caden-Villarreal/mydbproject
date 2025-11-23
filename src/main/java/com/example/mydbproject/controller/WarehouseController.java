package com.example.mydbproject.controller;

import com.example.mydbproject.model.WarehouseLocation;
import com.example.mydbproject.repository.WarehouseLocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseLocationRepository warehouseRepo;

    public WarehouseController(WarehouseLocationRepository warehouseRepo) {
        this.warehouseRepo = warehouseRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("warehouses", warehouseRepo.findAll());
        return "warehouse-list";
    }

    @GetMapping("/new")
    public String newWarehouse(Model model) {
        model.addAttribute("warehouse", new WarehouseLocation());
        return "warehouse-form";
    }

    @GetMapping("/{id}/edit")
    public String editWarehouse(@PathVariable Long id, Model model) {
        var w = warehouseRepo.findById(id);
        if (w.isEmpty()) return "redirect:/warehouses";
        model.addAttribute("warehouse", w.get());
        return "warehouse-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute WarehouseLocation warehouse) {
        warehouseRepo.save(warehouse);
        return "redirect:/warehouses";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        warehouseRepo.deleteById(id);
        return "redirect:/warehouses";
    }
}