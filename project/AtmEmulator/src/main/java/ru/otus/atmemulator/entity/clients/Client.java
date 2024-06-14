package ru.otus.atmemulator.entity.clients;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.atmemulator.entity.accounts.Account;
import ru.otus.atmemulator.author.Author;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Author(name = "Viktor", dateOfCreation = 2024)
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Account> accounts;

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
    }

}