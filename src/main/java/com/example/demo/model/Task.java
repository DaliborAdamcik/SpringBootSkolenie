package com.example.demo.model;

import javax.persistence.*;

@Entity()
@Table(name = "task")
@SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", initialValue = 1, allocationSize = 1)
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_id_seq")
    private Long id;

    @Column(name = "task_name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_user_id")
    private User createdBy;

    public Task() {
    }

    public Task(String name, User createdBy) {
        this.name = name;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
