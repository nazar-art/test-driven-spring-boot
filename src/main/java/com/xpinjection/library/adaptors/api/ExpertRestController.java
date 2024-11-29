package com.xpinjection.library.adaptors.api;

import com.xpinjection.library.adaptors.api.dto.NewExpert;
import com.xpinjection.library.service.ExpertService;
import com.xpinjection.library.service.dto.CreateExpertDto;
import com.xpinjection.library.service.exception.InvalidRecommendationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "experts")
@RestController
@AllArgsConstructor
public class ExpertRestController {

    private final ExpertService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/experts", produces = MediaType.APPLICATION_JSON_VALUE)
    NewExpert addExpert(@RequestBody @Valid CreateExpertDto expert) {
        long id = service.addExpert(expert);
        return new NewExpert(id);
    }

    @ExceptionHandler(InvalidRecommendationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad recommendations")
    void onSaveError() {
    }
}
