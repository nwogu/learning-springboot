package com.gabrielnwogu.elevatorSystem.domains;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderNumber;

    @Column(columnDefinition = "TEXT")
    private String problem;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    public Complaint() {
    }

    public Complaint(String orderNumber, String problem) {
        this.orderNumber = orderNumber;
        this.problem = problem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint = (Complaint) o;
        return Objects.equals(id, complaint.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", problem='" + problem + '\'' +
                '}';
    }
}
