package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.constants.PaginationConstants;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchEventParams {
    private String text;

    private List<Long> categories;

    private Boolean paid;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeStart;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeEnd;

    @Builder.Default
    private Boolean onlyAvailable = false;

    private String sort;

    @PositiveOrZero
    @Builder.Default
    private Integer from = PaginationConstants.DEFAULT_FROM;
    @Positive
    @Builder.Default
    private Integer size = PaginationConstants.DEFAULT_SIZE;
}