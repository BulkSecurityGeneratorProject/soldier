package com.test.test.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Arm.
 */
@Entity
@Table(name = "arm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Arm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "attack")
    private Integer attack;

    @Column(name = "defence")
    private Integer defence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Arm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public Arm price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAttack() {
        return attack;
    }

    public Arm attack(Integer attack) {
        this.attack = attack;
        return this;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefence() {
        return defence;
    }

    public Arm defence(Integer defence) {
        this.defence = defence;
        return this;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Arm arm = (Arm) o;
        if (arm.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, arm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Arm{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", attack='" + attack + "'" +
            ", defence='" + defence + "'" +
            '}';
    }
}
