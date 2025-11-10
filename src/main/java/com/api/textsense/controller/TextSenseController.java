package com.api.textsense.controller;

import com.api.textsense.dto.request.TextSenseRequest;
import com.api.textsense.dto.response.TextSenseResponse;
import com.api.textsense.service.TextSenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextSenseController {

    private final TextSenseService textSenseService;

    private final TextSenseMapper textSenseMapper;

    public TextSenseController(TextSenseService textSenseService, TextSenseMapper textSenseMapper) {
        this.textSenseService = textSenseService;
        this.textSenseMapper = textSenseMapper;
    }

    @GetMapping("/analyse")
    public ResponseEntity<TextSenseResponse> analyse(@RequestBody @Valid TextSenseRequest textSenseRequest){
        var textSense = textSenseService.analyse(textSenseRequest);
        return ResponseEntity
                .ok( textSenseMapper.toResponse(textSense));
    }

}
