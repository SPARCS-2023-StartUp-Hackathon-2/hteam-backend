package org.sparcs.hackathon.hteam.mozipserver.entities;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.sparcs.hackathon.hteam.mozipserver.enums.ApplicantState;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Recruitment recruitment;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonType")
    @Column(columnDefinition = "json")
    private Map<String, Object> formContent;

    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    private ApplicantState formState;

    @Enumerated(EnumType.STRING)
    private ApplicantState interviewState;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Applicant(
        Recruitment recruitment,
        String name,
        String email,
        String phoneNumber,
        Map<String, Object> formContent
    ) {
        this.recruitment = recruitment;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.formState = ApplicantState.UNDEFINED;
        this.interviewState = ApplicantState.UNDEFINED;
        this.formContent = formContent;
        this.uuid = UUID.randomUUID().toString();
        this.submittedAt = LocalDateTime.now();
    }
}
