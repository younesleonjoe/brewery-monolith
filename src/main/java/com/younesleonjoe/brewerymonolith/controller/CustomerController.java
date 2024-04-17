package com.younesleonjoe.brewerymonolith.controller;

import com.younesleonjoe.brewerymonolith.entity.Customer;
import com.younesleonjoe.brewerymonolith.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/customers")
@Controller
@RequiredArgsConstructor
public class CustomerController {

  // TODO add service
  private final CustomerRepository customerRepository;

  @RequestMapping("/find")
  public String findCustomers(Model model) {
    model.addAttribute("customer", Customer.builder().build());
    return "customers/findCustomers";
  }

  @GetMapping
  public String processFindFormReturnMany(Customer customer, BindingResult result, Model model) {

    // ToDO: Add Service
    List<Customer> customers = customerRepository.findAllByNameContaining(customer.getName());

    if (customers.isEmpty()) {

      result.rejectValue("name", "error.notFound", "Not Found");
      return "customers/findCustomers";

    } else if (customers.size() == 1) {

      customer = customers.getFirst();
      return "redirect:/customers/" + customer.getId();

    } else {

      model.addAttribute("selections", customers);
      return "customers/customerList";
    }
  }

  @GetMapping("/{id}")
  public ModelAndView showCustomer(@PathVariable UUID id) {

    ModelAndView mav = new ModelAndView("customers/customerDetails");
    // ToDO: Add Service
    mav.addObject(customerRepository.findById(id).get());
    return mav;
  }

  @GetMapping("/new")
  public String initCreateForm(Model model) {

    model.addAttribute("customer", Customer.builder().build());
    return "customers/createCustomer";
  }

  @PostMapping("/new")
  public String processCreateForm(Customer customer) {

    // ToDO: Add Service
    Customer newCustomer = Customer.builder().name(customer.getName()).build();

    Customer savedCustomer = customerRepository.save(newCustomer);
    return "redirect:/customers/" + savedCustomer.getId();
  }

  @GetMapping("/{customerId}/edit")
  public String initUpdateForm(@PathVariable UUID customerId, Model model) {

    if (customerRepository.findById(customerId).isPresent())
      model.addAttribute("customer", customerRepository.findById(customerId).get());

    return "customers/createOrUpdateCustomer";
  }

  @PostMapping("/{id}/edit")
  public String processUpdateForm(@Validated Customer customer, BindingResult result) {

    if (result.hasErrors()) {
      return "beers/createOrUpdateCustomer";
    } else {
      // ToDO: Add Service
      Customer existingCustomer = customerRepository.findById(customer.getId())
          .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customer.getId()));
      
      if (!existingCustomer.getVersion().equals(customer.getVersion())) {
        // Versions don't match, handle optimistic locking conflict
        throw new RuntimeException("Optimistic locking conflict: Customer has been modified by another user.");
      }
      
      existingCustomer.setName(customer.getName());
      Customer savedCustomer = customerRepository.save(existingCustomer);
      return "redirect:/customers/" + savedCustomer.getId();
    }
  }
}
