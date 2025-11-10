package com.api.textsense.controller;

import com.api.textsense.dto.request.TextSenseRequest;
import com.api.textsense.dto.response.TextSenseResponse;
import com.api.textsense.service.TextSenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextSenseController {

    private final TextSenseService textSenseService;

    public TextSenseController(TextSenseService textSenseService) {
        this.textSenseService = textSenseService;
    }

    @GetMapping("/analyse")
    public ResponseEntity<TextSenseResponse> analyse(@Valid TextSenseRequest textSenseRequest){
        var response = textSenseService.analyse(textSenseRequest);
        return ResponseEntity
                .ok(response);
    }

}
