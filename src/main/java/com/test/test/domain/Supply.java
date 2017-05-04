package com.test.test.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Supply.
 */
@Entity
@Table(name = "supply")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Supply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "hp")
    private Integer hp;

    @Column(name = "fatigue")
    private Integer fatigue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Supply name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public Supply price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getHp() {
        return hp;
    }

    public Supply hp(Integer hp) {
        this.hp = hp;
        return this;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getFatigue() {
        return fatigue;
    }

    public Supply fatigue(Integer fatigue) {
        this.fatigue = fatigue;
        return this;
    }

    public void setFatigue(Integer fatigue) {
        this.fatigue = fatigue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Supply supply = (Supply) o;
        if (supply.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, supply.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Supply{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", hp='" + hp + "'" +
            ", fatigue='" + fatigue + "'" +
            '}';
    }
}
