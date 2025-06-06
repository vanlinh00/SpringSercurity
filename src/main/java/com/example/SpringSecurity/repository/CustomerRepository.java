package com.example.SpringSecurity.repository;


import com.example.SpringSecurity.entity.hibernate.Customer;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class  CustomerRepository {
/*
//Hibernate có thể dùng theo 2 kiểu phổ biến này để tương tác với database trong Java:
2. Dùng SessionFactory và Session (Hibernate native API)
Đây là API thuần Hibernate, có nhiều tính năng nâng cao và tùy biến hơn JPA.

Cần quản lý session thủ công (open, close), hoặc có thể tích hợp với Spring để tự động quản lý session


    Nếu bạn làm project chuẩn Spring Boot, muốn dễ bảo trì, dễ thay đổi, dùng JPA EntityManager sẽ tốt hơn.

Nếu bạn muốn tận dụng tính năng nâng cao của Hibernate, tối ưu sâu, hoặc có codebase legacy Hibernate thuần,
 dùng SessionFactory - Session có thể phù hợp.
 */

    private final SessionFactory sessionFactory;



    public List<Customer> findAll() {
        try (Session session = sessionFactory.openSession()) {
       return session.createQuery("FROM Customer", Customer.class).list();

        }
    }


//    public List<CustomerDTO> findAll() {
//        try (Session session = sessionFactory.openSession()) {
//            return session.createQuery(
//                    "SELECT new com.example.SpringSecurity.dto.CustomerDTO(c.id, c.name) FROM Customer c",
//                    CustomerDTO.class
//            ).list();
//        }
//    }



    public Optional<Customer> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Customer customer = session.get(Customer.class, id);
            return Optional.ofNullable(customer);
        }
    }


    public Customer save(Customer customer) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(customer);
            tx.commit();
            return customer;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }


    public Optional<Customer> update(Long id, Customer updatedCustomer) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            Customer existingCustomer = session.get(Customer.class, id);
            if (existingCustomer == null) {
                return Optional.empty();
            }
            tx = session.beginTransaction();
            existingCustomer.setName(updatedCustomer.getName());
            // cập nhật thêm các trường khác nếu có
            session.update(existingCustomer);
            tx.commit();
            return Optional.of(existingCustomer);
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }


    public boolean delete(Long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            Customer customer = session.get(Customer.class, id);
            if (customer == null) {
                return false;
            }
            tx = session.beginTransaction();
            session.delete(customer);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
