package br.com.escambo.app.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TokenTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemAId;
    private Long itemBId;
    private String userA;
    private String userB;
    private LocalDateTime transactionDate;

    // getters e setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getItemAId() {
        return itemAId;
    }
    public void setItemAId(Long itemAId) {
        this.itemAId = itemAId;
    }
    public Long getItemBId() {
        return itemBId;
    }
    public void setItemBId(Long itemBId) {
        this.itemBId = itemBId;
    }
    public String getUserA() {
        return userA;
    }
    public void setUserA(String userA) {
        this.userA = userA;
    }
    public String getUserB() {
        return userB;
    }
    public void setUserB(String userB) {
        this.userB = userB;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
