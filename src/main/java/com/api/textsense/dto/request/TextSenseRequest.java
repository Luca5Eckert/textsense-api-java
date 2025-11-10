package com.api.textsense.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TextSenseRequest(@NotBlank(message = "The text can't be null") @Size(max = 300, message = "The text can't be more than 300 characteres") String text) {
}
