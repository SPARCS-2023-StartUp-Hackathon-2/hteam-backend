package org.sparcs.hackathon.hteam.mozipserver.dtos.form;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import org.sparcs.hackathon.hteam.mozipserver.entities.Form;

@Getter
public class FormResponseDto {

    private Long id;
    private Map<String, Object> content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FormResponseDto(Form form) {
        this.id = form.getId();
        this.content = form.getContent();
        this.createdAt = form.getCreatedAt();
        this.updatedAt = form.getUpdatedAt();
    }
}
