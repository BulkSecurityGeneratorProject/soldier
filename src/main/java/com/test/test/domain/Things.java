package com.test.test.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Things.
 */
@Entity
@Table(name = "things")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Things implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "jhi_type")
    private Integer type;

    @Column(name = "belong")
    private Integer belong;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public Things pid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getType() {
        return type;
    }

    public Things type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBelong() {
        return belong;
    }

    public Things belong(Integer belong) {
        this.belong = belong;
        return this;
    }

    public void setBelong(Integer belong) {
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
        Things things = (Things) o;
        if (things.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, things.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Things{" +
            "id=" + id +
            ", pid='" + pid + "'" +
            ", type='" + type + "'" +
            ", belong='" + belong + "'" +
            '}';
    }
}
