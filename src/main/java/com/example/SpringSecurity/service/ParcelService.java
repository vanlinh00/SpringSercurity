package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.PostOfficeReportDTO;
import com.example.SpringSecurity.entity.hibernate.Parcel;
import com.example.SpringSecurity.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelService {

    private final ParcelRepository repository;

    public ParcelService(ParcelRepository repository) {
        this.repository = repository;
    }

    public Parcel save(Parcel parcel) {
        return repository.save(parcel);
    }

    public Parcel get(Long id) {
        return repository.findById(id);
    }

    public List<Parcel> getAll() {
        return repository.findAll();
    }

    public Parcel update(Parcel parcel) {
        return repository.update(parcel);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public List<PostOfficeReportDTO> getParcelCountByPostOffice() {
        return repository.getParcelCountByPostOffice();
    }
}
