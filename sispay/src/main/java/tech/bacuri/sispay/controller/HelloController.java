package tech.bacuri.sispay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping({"/", ""})
    public ResponseEntity<?> hello() {
        System.out.println("hello()");
        return ResponseEntity.ok(Map.of("hello", "word"));
    }
}
