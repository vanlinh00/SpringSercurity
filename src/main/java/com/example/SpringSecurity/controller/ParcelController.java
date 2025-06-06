package com.example.SpringSecurity.controller;


import com.example.SpringSecurity.dto.PostOfficeReportDTO;
import com.example.SpringSecurity.entity.hibernate.Customer;
import com.example.SpringSecurity.entity.hibernate.Parcel;
import com.example.SpringSecurity.service.CustomerService;
import com.example.SpringSecurity.service.ParcelService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
✅ Tổng kết:
✔️ Không dùng JpaRepository, mà dùng EntityManager = Hibernate thuần.

✔️ Vẫn tận dụng Spring Boot để chạy tự động, cấu hình tiện lợi.

✔️ CRUD đầy đủ: create, get, getAll, update, delete.
 */
@RestController
@RequestMapping("/api/public/parcels")
@RequiredArgsConstructor
public class ParcelController {

    private final  ParcelService service;


    private final  CustomerService customerService;



    @PostMapping
    public Parcel create(@RequestBody Parcel parcel) {
        return service.save(parcel);
    }

    @GetMapping("/{id}")
    public Parcel get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<Parcel> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public Parcel update(@PathVariable Long id, @RequestBody Parcel parcel) {
        parcel.setId(id); // cập nhật id
        return service.update(parcel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/report/by-post-office")
    public ResponseEntity<List<PostOfficeReportDTO>> getParcelCountByPostOffice() {
        List<PostOfficeReportDTO> report = service.getParcelCountByPostOffice();
        return ResponseEntity.ok(report);
    }


    // ----- CUSTOMER CRUD -----

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer created = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (customerService.deleteCustomer(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
