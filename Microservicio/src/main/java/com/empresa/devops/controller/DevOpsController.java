package com.empresa.devops.controller;

import com.empresa.devops.dto.DevOpsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DevOpsController {

    private static final String API_KEY =
            "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c";

    private boolean validBody(DevOpsRequest body) {
        return body != null
                && body.getMessage() != null
                && body.getTo() != null
                && body.getFrom() != null
                && body.getTimeToLifeSec() != null;
    }

    @PostMapping("/DevOps")
    public ResponseEntity<?> postDevOps(
            @RequestHeader(value = "X-Parse-REST-API-Key", required = false) String apiKey,
            @RequestHeader(value = "X-JWT-KWY", required = false) String jwt,
            @RequestBody(required = false) DevOpsRequest body) {

        // Validar API Key
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("ERROR");
        }

        // Validar que exista el JWT
        if (jwt == null || jwt.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("ERROR");
        }

        // Validar payload
        if (!validBody(body)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("ERROR");
        }

        return ResponseEntity.ok(
                "{\"message\":\"Hello "
                        + body.getTo()
                        + " your message will be send\"}"
        );
    }

    @RequestMapping(
            value = "/DevOps",
            method = {
                    RequestMethod.GET,
                    RequestMethod.PUT,
                    RequestMethod.DELETE,
                    RequestMethod.PATCH
            }
    )
    public ResponseEntity<String> methodNotAllowed() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("ERROR");
    }
}