package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat; // Импорт аннотации для форматирования JSON
import com.fasterxml.jackson.annotation.JsonProperty; // Импорт аннотации для задания имени свойства в JSON
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder; // Импорт аннотации для генерации паттерна Builder
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder // Генерация паттерна Builder для создания объектов класса
@AllArgsConstructor
@NoArgsConstructor
public class StatRequest {
    private int id; // Идентификатор запроса (может быть сгенерирован на сервере)

    @NotNull
    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotNull
    @NotBlank
    private String ip; // IP-адрес клиента, отправившего запрос

    @NotNull
    @JsonProperty("timestamp") // Указание имени свойства в JSON
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Форматирование временной метки
    private LocalDateTime timestamp; // Временная метка запроса
}