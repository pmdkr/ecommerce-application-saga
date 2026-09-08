package com.pramod.repository;

import com.pramod.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer> {
}
