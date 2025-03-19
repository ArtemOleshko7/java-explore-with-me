package ru.practicum;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder; // Импорт класса для построения RestTemplate
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory; // Импорт класса для построения URI

import java.sql.Timestamp; // Импорт класса для работы с временными метками
import java.time.Instant; // Импорт класса для работы с временными моментами
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class StatsClient extends BaseClient {
    // Форматтер для форматирования даты
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Formatter.DATE_FORMAT);

    // Имя приложения, загружаемое из конфигурации
    @Value("${server.application.name:ewm-main-service}")
    private String applicationName;

    // URL сервера, загружаемый из конфигурации
    @Value("${server.application.url}")
    private String serverUrl;

    // Конструктор, принимающий URL сервера и RestTemplateBuilder
    public StatsClient(String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl)) // Установка обработчика URI
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory()) // Установка фабрики запросов
                        .build() // Построение RestTemplate
        );
    }

    // Метод для сохранения информации о запросе (hit)
    public ResponseEntity<Object> saveHit(HttpServletRequest request) {
        // Создание объекта StatRequest с данными о запросе
        final StatRequest hit = StatRequest.builder()
                .app(applicationName) // Установка имени приложения
                .uri(request.getRequestURI()) // Установка URI запроса
                .ip(request.getRemoteAddr()) // Установка IP-адреса клиента
                .timestamp(Timestamp.from(Instant.now()).toLocalDateTime()) // Установка временной метки
                .build(); // Построение объекта StatRequest
        return post(hit); // Отправка POST-запроса с данными о hit
    }

    // Метод для получения статистики по запросам
    public ResponseEntity<Object> getHit(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        // Построение URI для запроса статистики
        StringBuilder uriBuilder = new StringBuilder("/stats?start={start}&end={end}");
        // Создание карты параметров для запроса
        Map<String, Object> parameters = Map.of(
                "start", start.format(formatter), // Форматирование начала периода
                "end", end.format(formatter) // Форматирование конца периода
        );

        // Если указаны URI, добавляем их в параметры
        if (uris != null) {
            parameters.put("uris", String.join(",", uris)); // Объединение URI в строку
        }
        // Если требуется уникальная статистика, добавляем параметр
        if (unique) {
            parameters.put("unique", true);
        }
        return get(uriBuilder.toString(), parameters); // Отправка GET-запроса с параметрами
    }
}