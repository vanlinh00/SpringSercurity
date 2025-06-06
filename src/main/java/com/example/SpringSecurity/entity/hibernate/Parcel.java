package com.example.SpringSecurity.entity.hibernate;

import com.example.SpringSecurity.entity.PostOffice;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parcels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String code;

    @JsonBackReference
    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Receiver receiver;

    @ManyToOne
    private PostOffice2 postOffice2;
}
