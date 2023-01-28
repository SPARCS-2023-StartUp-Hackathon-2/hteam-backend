package org.sparcs.hackathon.hteam.mozipserver.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.Authorize;
import org.sparcs.hackathon.hteam.mozipserver.dtos.form.FormResponseDto;
import org.sparcs.hackathon.hteam.mozipserver.dtos.form.FormUpsertRequestDto;
import org.sparcs.hackathon.hteam.mozipserver.entities.Form;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.sparcs.hackathon.hteam.mozipserver.repositories.FormRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Authorize
    @GetMapping
    FormResponseDto getByRecruitmentId(@RequestParam Long recruitmentId) {
        Form form = formRepository.findByRecruitmentId(recruitmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new FormResponseDto(form);
    }

    @Authorize
    @PatchMapping
    void upsert(
        @RequestParam Long recruitmentId,
        @RequestBody FormUpsertRequestDto formUpsertRequestDto
    ) {
        Form form = formRepository.findByRecruitmentId(recruitmentId).orElse(null);

        if (form == null) {
            form = new Form(new Recruitment(recruitmentId));
        }

        form.setContent(formUpsertRequestDto.getContent());

        formRepository.save(form);
    }
}
