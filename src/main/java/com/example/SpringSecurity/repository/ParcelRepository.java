package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.dto.PostOfficeReportDTO;
import com.example.SpringSecurity.entity.hibernate.Parcel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
//Hibernate có thể dùng theo 2 kiểu phổ biến này để tương tác với database trong Java:
/*Đây là chuẩn của JPA, Hibernate chỉ là một implementation của JPA.

EntityManager là interface tiêu chuẩn, có thể dễ dàng chuyển đổi giữa các provider (Hibernate, EclipseLink...).*/
@Repository
public class ParcelRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Parcel save(Parcel parcel) {
        em.persist(parcel);
        return parcel;
    }

    public Parcel findById(Long id) {
        return em.find(Parcel.class, id);
    }

    public List<Parcel> findAll() {
        return em.createQuery("SELECT p FROM Parcel p", Parcel.class).getResultList();
    }

    @Transactional
    public Parcel update(Parcel parcel) {
        return em.merge(parcel);
    }

    @Transactional
    public void delete(Long id) {
        Parcel parcel = em.find(Parcel.class, id);
        if (parcel != null) {
            em.remove(parcel);
        }
    }


    public List<PostOfficeReportDTO> getParcelCountByPostOffice() {
        return em.createQuery(
                "SELECT new com.example.SpringSecurity.dto.PostOfficeReportDTO(" +
                        "po.name,c.name, COUNT(p.id)) " +
                        "FROM Parcel p JOIN p.postOffice2 po " +
                        "JOIN p.customer c " +
                        "GROUP BY po.name, c.name",
                PostOfficeReportDTO.class
        ).getResultList();
    }


}
