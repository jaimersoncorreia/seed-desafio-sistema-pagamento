package tech.bacuri.sispay.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bacuri.sispay.entity.Hello;
import tech.bacuri.sispay.repository.HelloRepository;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class HelloController {
    private final HelloRepository helloRepository;


    @GetMapping({"/", ""})
    public ResponseEntity<?> hello() {
        System.out.println("hello()");
        return ResponseEntity.ok(Map.of("hello", "word"));
    }

    @PostMapping("/hello")
    public ResponseEntity<?> teste(){
        Hello hello = new Hello();
        hello.confirmar();
        helloRepository.save(hello);
        return ResponseEntity.ok(hello);
    }
}
