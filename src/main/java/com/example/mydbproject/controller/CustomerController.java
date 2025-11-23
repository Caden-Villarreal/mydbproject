package com.example.mydbproject.controller;

import com.example.mydbproject.model.Customer;
import com.example.mydbproject.model.CustomerAddressRef;
import com.example.mydbproject.repository.CustomerRepository;
import com.example.mydbproject.repository.CustomerAddressRefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

@Autowired
private CustomerRepository customerRepository;

@Autowired
private CustomerAddressRefRepository addressRepository;

// -------------------
// CUSTOMER CRUD
// -------------------

@GetMapping("")
public String listCustomers(Model model) {
    model.addAttribute("customers", customerRepository.findAll());
    return "customers-list";
}

@GetMapping({"/new", "/edit/{id}"})
public String customerForm(@PathVariable(required = false) Long id, Model model) {
    Customer customer = (id != null) ?
            customerRepository.findById(id).orElse(new Customer()) :
            new Customer();
    model.addAttribute("customer", customer);
    return "customer-form";
}

@PostMapping("/save")
public String saveCustomer(@RequestParam(required = false) Long id,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String email,
                           @RequestParam String phone) {

    Customer customer = (id != null) ?
            customerRepository.findById(id).orElse(new Customer()) :
            new Customer();

    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    customer.setEmail(email);
    customer.setPhone(phone);

    customerRepository.save(customer);
    return "redirect:/customers";
}

@GetMapping("/delete/{id}")
public String deleteCustomer(@PathVariable Long id) {
    customerRepository.deleteById(id);
    return "redirect:/customers";
}

@GetMapping("/search")
public String searchCustomers(@RequestParam(required = false) String q, Model model) {
    List<Customer> results = customerRepository.findAll();
    if (q != null && !q.isEmpty()) {
        String search = q.toLowerCase();
        results = results.stream()
                .filter(c -> c.getFirstName().toLowerCase().contains(search)
                        || c.getLastName().toLowerCase().contains(search)
                        || c.getEmail().toLowerCase().contains(search))
                .toList();
    }
    model.addAttribute("customers", results);
    model.addAttribute("q", q);
    return "customers-search";
}

// -------------------
// CUSTOMER ADDRESS CRUD
// -------------------

@GetMapping("/{customerId}/addresses")
public String listAddresses(@PathVariable Long customerId, Model model) {
    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id: " + customerId));
    List<CustomerAddressRef> addresses = addressRepository.findByCustomerId(customerId);
    model.addAttribute("customer", customer);
    model.addAttribute("addresses", addresses);
    return "customer-address-list";
}

@GetMapping("/{customerId}/addresses/new")
public String newAddressForm(@PathVariable Long customerId, Model model) {
    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id: " + customerId));
    CustomerAddressRef address = new CustomerAddressRef();
    address.setCustomer(customer);
    model.addAttribute("address", address);
    return "customer-address-form";
}

@GetMapping("/{customerId}/addresses/edit/{addressId}")
public String editAddressForm(@PathVariable Long customerId,
                              @PathVariable Long addressId,
                              Model model) {
    CustomerAddressRef address = addressRepository.findById(addressId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid address Id: " + addressId));
    model.addAttribute("address", address);
    return "customer-address-form";
}

@PostMapping("/{customerId}/addresses/save")
public String saveAddress(@PathVariable Long customerId,
                          @RequestParam(required = false) Long addressId,
                          @RequestParam String addressType,
                          @RequestParam String street,
                          @RequestParam String city,
                          @RequestParam String state,
                          @RequestParam String zipCode) {

    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id: " + customerId));

    CustomerAddressRef address = (addressId != null) ?
            addressRepository.findById(addressId).orElse(new CustomerAddressRef()) :
            new CustomerAddressRef();

    address.setCustomer(customer);
    address.setAddressType(addressType);
    address.setStreet(street);
    address.setCity(city);
    address.setState(state);
    address.setZipCode(zipCode);

    addressRepository.save(address);
    return "redirect:/customers/" + customerId + "/addresses";
}

@GetMapping("/{customerId}/addresses/delete/{addressId}")
public String deleteAddress(@PathVariable Long customerId,
                            @PathVariable Long addressId) {
    addressRepository.deleteById(addressId);
    return "redirect:/customers/" + customerId + "/addresses";
}

}