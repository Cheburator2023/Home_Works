package ru.otus.atmemulator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AtmEmulatorApplicationTests {

    @Autowired
    private AtmService atmService;

    @Test
    public void contextLoads() {
        // Проверяем, что контекст загружен и сервис не равен null
        assertNotNull(atmService, "AtmService должен быть инициализирован");
    }

    @Test
    public void testGetAccountBalance() {
        // Проверяем начальный баланс счета
        int initialBalance = atmService.getAccountBalance();
        assertEquals(0, initialBalance, "Начальный баланс должен быть равен 0");
    }
}
