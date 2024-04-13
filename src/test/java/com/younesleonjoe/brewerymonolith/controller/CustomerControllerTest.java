package com.younesleonjoe.brewerymonolith.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.younesleonjoe.brewerymonolith.entity.Customer;
import com.younesleonjoe.brewerymonolith.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

  @Mock CustomerRepository customerRepository;
  @InjectMocks CustomerController controller;
  List<Customer> customerList;
  UUID id;
  Customer customer;
  MockMvc mockMvc;

  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() throws JsonProcessingException {
    customer =
        Customer.builder().id(UUID.randomUUID()).name("John Doe").apiKey(UUID.randomUUID()).build();
    customerList = new ArrayList<Customer>();
    customerList.add(customer);
    customer.setId(UUID.randomUUID());
    customerList.add(customer);
    final String tmpId = "493410b3-dd0b-4b78-97bf-289f50f6e74f";
    id = UUID.fromString(tmpId);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void findCustomers() throws Exception {

    mockMvc
        .perform(get("/customers/find"))
        .andExpect(status().isOk())
        .andExpect(view().name("customers/findCustomers"))
        .andExpect(model().attributeExists("customer"));
    verifyNoInteractions(customerRepository);
  }

  @Test
  void processFindFormReturnMany() throws Exception {

    when(customerRepository.findAllByNameLike(anyString())).thenReturn(customerList);

    mockMvc
        .perform(get("/customers").flashAttr("customer", customer))
        .andExpect(status().isOk())
        .andExpect(view().name("customers/customerList"))
        .andExpect(model().attribute("selections", hasSize(2)));
  }

  @Test
  void showCustomer() throws Exception {

    when(customerRepository.findById(id))
        .thenReturn(Optional.of(Customer.builder().id(id).build()));

    mockMvc
        .perform(get("/customers/" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("customers/customerDetails"))
        .andExpect(model().attribute("customer", hasProperty("id", is(id))));
  }

  @Test
  void initCreateForm() throws Exception {

    mockMvc
        .perform(get("/customers/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("customers/createCustomer"))
        .andExpect(model().attributeExists("customer"));

    verifyNoInteractions(customerRepository);
  }

  @Test
  void processCreateForm() throws Exception {

    when(customerRepository.save(ArgumentMatchers.any()))
        .thenReturn(Customer.builder().id(id).build());
    mockMvc
        .perform(post("/customers/new").flashAttr("customer", customer))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/customers/" + id));

    verify(customerRepository).save(ArgumentMatchers.any());
  }

  @Test
  void initUpdateForm() throws Exception {

    when(customerRepository.findById(id))
        .thenReturn(Optional.of(Customer.builder().id(id).build()));

    mockMvc
        .perform(get("/customers/" + id + "/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("customers/createOrUpdateCustomer"))
        .andExpect(model().attributeExists("customer"));

    verifyNoMoreInteractions(customerRepository);
  }

  @Test
  void processUpdateForm() throws Exception {

    when(customerRepository.save(ArgumentMatchers.any()))
        .thenReturn(Customer.builder().id(id).build());

    mockMvc
        .perform(post("/customers/" + id + "/edit"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/customers/" + id));

    verify(customerRepository).save(ArgumentMatchers.any());
  }
}
