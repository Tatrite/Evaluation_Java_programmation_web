package com.ipi.world_cup_b3.controller;

import com.ipi.world_cup_b3.dto.response.ClassementResponse;
import com.ipi.world_cup_b3.service.ClassementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/classements")
public class ClassementController {

    private final ClassementService classementService;

    public ClassementController(ClassementService classementService) {
        this.classementService = classementService;
    }

    @GetMapping("/general")
    public ResponseEntity<List<ClassementResponse>> getClassementGeneral() {
        return ResponseEntity.ok(classementService.getClassementGeneral());
    }

    @GetMapping("/ligue/{id}")
    public ResponseEntity<List<ClassementResponse>> getClassementLigue(@PathVariable Long id) {
        return ResponseEntity.ok(classementService.getClassementLigue(id));
    }
}
