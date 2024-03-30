package ru.otus.crm.model;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import jakarta.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "phone")
public class Phone implements Cloneable {

    @Expose
    @Id
    @SequenceGenerator(name = "phone_gen", sequenceName = "phone_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_gen")
    @Column(name = "id")
    private Long id;

    @Expose
    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @Fetch(FetchMode.JOIN)
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }
    public Phone(String number) {
        this.id = null;
        this.number = number;
    }

    @Override
    public Phone clone() {
        try {
            return (Phone) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed for Phone", e);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone phone)) return false;

        if (!getId().equals(phone.getId())) return false;
        return getNumber().equals(phone.getNumber());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getNumber().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}