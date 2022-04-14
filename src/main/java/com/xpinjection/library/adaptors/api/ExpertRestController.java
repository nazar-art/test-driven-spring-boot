package com.xpinjection.library.adaptors.api;

import com.xpinjection.library.adaptors.api.dto.ExpertState;
import com.xpinjection.library.domain.Expert;
import com.xpinjection.library.exception.InvalidRecommendationException;
import com.xpinjection.library.service.ExpertService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class ExpertRestController {

    private final ExpertService service;

    @PostMapping(path = "/experts", produces = MediaType.APPLICATION_JSON_VALUE)
    ExpertState addExpert(@RequestBody @Valid Expert expert) {
        long id = service.add(expert);
        return new ExpertState(id);
    }

    @ExceptionHandler(InvalidRecommendationException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Bad recommendations")
    void onSaveError() {
    }
}
