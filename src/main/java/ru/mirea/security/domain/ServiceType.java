package ru.mirea.security.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "service_types")
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean active = true;

    public UUID getId() {return id; }
    public void setId(UUID id) {this.id = id; }
    public String getName() {return name; }
    public void setName(String name) {this.name = name; }
    public boolean getActive() {return active; }
    public void setActive(Boolean active) {this.active = active; }

}
