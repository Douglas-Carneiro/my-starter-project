package com.gmail.douglas.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.douglas.backend.data.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
