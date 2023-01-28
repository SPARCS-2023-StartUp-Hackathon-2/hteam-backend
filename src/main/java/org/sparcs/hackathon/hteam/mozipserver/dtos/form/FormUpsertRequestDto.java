package org.sparcs.hackathon.hteam.mozipserver.dtos.form;

import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormUpsertRequestDto {

    private Map<String, Object> content;
}
