package com.api.textsense.controller;

import com.api.textsense.dto.request.TextSenseRequest;
import com.api.textsense.dto.response.TextSenseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextSenseController {

    @GetMapping("/analyse")
    public ResponseEntity<TextSenseResponse> analyse(@Valid TextSenseRequest textSenseRequest){
        return ResponseEntity
                .ok(new TextSenseResponse(null, null, null));
    }

}
