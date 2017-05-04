package com.test.test.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "image")
    private String image = "assets/image/bigBing.jpg";

    @Column(name = "hp")
    private Integer hp = 100;

    @Column(name = "money")
    private Integer money = 100;

    @Column(name = "strength")
    private Integer strength = 1;

    @Column(name = "agility")
    private Integer agility = 1;

    @Column(name = "attack")
    private Integer attack = 1;

    @Column(name = "defence")
    private Integer defence = 1;

    @Column(name = "fatigue")
    private Integer fatigue = 0;

    @Column(name = "belong")
    private Long belong = (long) 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Player name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public Player mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public Player password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public Player image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getHp() {
        return hp;
    }

    public Player hp(Integer hp) {
        this.hp = hp;
        return this;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getMoney() {
        return money;
    }

    public Player money(Integer money) {
        this.money = money;
        return this;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getStrength() {
        return strength;
    }

    public Player strength(Integer strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getAgility() {
        return agility;
    }

    public Player agility(Integer agility) {
        this.agility = agility;
        return this;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getAttack() {
        return attack;
    }

    public Player attack(Integer attack) {
        this.attack = attack;
        return this;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefence() {
        return defence;
    }

    public Player defence(Integer defence) {
        this.defence = defence;
        return this;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public Integer getFatigue() {
        return fatigue;
    }

    public Player fatigue(Integer fatigue) {
        this.fatigue = fatigue;
        return this;
    }

    public void setFatigue(Integer fatigue) {
        this.fatigue = fatigue;
    }

    public Long getBelong() {
        return belong;
    }

    public Player belong(Long belong) {
        this.belong = belong;
        return this;
    }

    public void setBelong(Long belong) {
        this.belong = belong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        if (player.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", mail='" + mail + "'" +
            ", password='" + password + "'" +
            ", image='" + image + "'" +
            ", hp='" + hp + "'" +
            ", money='" + money + "'" +
            ", strength='" + strength + "'" +
            ", agility='" + agility + "'" +
            ", attack='" + attack + "'" +
            ", defence='" + defence + "'" +
            ", fatigue='" + fatigue + "'" +
            ", belong='" + belong + "'" +
            '}';
    }
}
