package org.sparcs.hackathon.hteam.mozipserver.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparcs.hackathon.hteam.mozipserver.dtos.form.FormResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.entities.Form;
import org.sparcs.hackathon.hteam.mozipserver.repositories.FormRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("forms")
public class FormController {

    private final FormRepository formRepository;

    @GetMapping()
    FormResponseDto getByRecruitmentId(@RequestParam Long recruitmentId) {
        Form form = formRepository.findByRecruitmentId(recruitmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new FormResponseDto(form);
    }
}
