package ru.otus.crm.model;

import org.hibernate.Hibernate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Cloneable {
    @Id
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @OneToOne(mappedBy = "address", targetEntity = Client.class, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Client client;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address(String street) {
        this.id = null;
        this.street = street;
    }

    @Override
    public Address clone() {
        try {
            Address clonedAddress = (Address) super.clone();
            if (this.client != null) {
                clonedAddress.client = this.client.clone();
            }
            return clonedAddress;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed for Address", e);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        Address actualAddress = (Address) Hibernate.unproxy(this);
        Address expectedAddress = (Address) Hibernate.unproxy(address);

        if (!actualAddress.getId().equals(expectedAddress.getId())) return false;
        return actualAddress.getStreet().equals(expectedAddress.getStreet());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getStreet().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}