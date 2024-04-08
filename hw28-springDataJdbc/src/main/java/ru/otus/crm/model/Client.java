package ru.otus.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table("client")
public class Client {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @MappedCollection(idColumn = "client_id", keyColumn = "client_id")
    private Address address;

    @MappedCollection(idColumn = "client_id", keyColumn = "client_id")
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(String name, Address address, List<Phone> phones) {
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        List<Phone> clonedPhones = new ArrayList<>();
        if (this.phones != null) {
            for (Phone phone : this.phones) {
                clonedPhones.add(phone.clone());
            }
        }
        if (this.address != null) {
            return new Client(this.id, this.name, this.address.clone(), clonedPhones);
        }
        return new Client(this.id, this.name, this.address, clonedPhones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;

        if (!getId().equals(client.getId())) return false;
        return getName().equals(client.getName());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
