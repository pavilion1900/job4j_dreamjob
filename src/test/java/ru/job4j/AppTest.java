package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    void whenGetString() {
        assertThat(App.getString()).isEqualTo("Hello World!");
    }
}
