package ru.practicum;

import org.springframework.http.*;
import org.springframework.lang.Nullable; // Импорт аннотации для обозначения параметров, которые могут быть null
import org.springframework.web.client.HttpStatusCodeException; // Импорт исключения для обработки ошибок HTTP
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class BaseClient {
    protected final RestTemplate restTemplate; // Объект для выполнения HTTP-запросов

    // Конструктор, принимающий RestTemplate в качестве параметра
    public BaseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Метод для выполнения POST-запроса
    protected <T> ResponseEntity<Object> post(T body) {
        return makeAndSendRequest(HttpMethod.POST, "/hit", null, body); // Отправка на указанный путь
    }

    // Метод для выполнения GET-запроса
    protected <T> ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null);
    }

    // Метод для создания и отправки HTTP-запроса
    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> parameters, @Nullable T body) {
        // Создание HTTP-запроса с телом и заголовками
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

        try {
            assert parameters != null;
            // Выполнение HTTP-запроса и возврат ответа
            return restTemplate.exchange(path, method, requestEntity, Object.class, parameters);
        } catch (HttpStatusCodeException e) {
            // Обработка исключения при ошибке HTTP
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
    }

    // Метод для установки стандартных заголовков для HTTP-запросов
    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Установка типа контента в JSON
        headers.setAccept(List.of(MediaType.APPLICATION_JSON)); // Установка принимаемого типа контента в JSON
        return headers; // Возврат настроенных заголовков
    }
}