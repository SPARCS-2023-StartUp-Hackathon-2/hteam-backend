package org.sparcs.hackathon.hteam.mozipserver.controllers;


import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.Authorize;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.CurrentUser;
import org.sparcs.hackathon.hteam.mozipserver.entities.Applicant;
import org.sparcs.hackathon.hteam.mozipserver.entities.Interviewer;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.sparcs.hackathon.hteam.mozipserver.entities.User;
import org.sparcs.hackathon.hteam.mozipserver.enums.ApplicantState;
import org.sparcs.hackathon.hteam.mozipserver.eventListeners.SendEmailEvent;
import org.sparcs.hackathon.hteam.mozipserver.repositories.ApplicantRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.InterviewerRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.RecruitmentRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.UserRespository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("email")
public class EmailController {

    private static final String INTERVIEWER_URL_PREFIX = "https://mozip.vercel.app/interviewerform/";
    private static final String APPLICANT_URL_PREFIX = "https://mozip.vercel.app/applicantform/";

    private final InterviewerRepository interviewerRepository;
    private final ApplicantRepository applicantRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRespository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Authorize
    @PostMapping("interviewers")
    void sendToInterviewers(
        @CurrentUser Long userId,
        @RequestParam Long recruitmentId
    ) {
        List<Interviewer> interviewers = interviewerRepository.findAllByRecruitmentId(recruitmentId);

        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String[] parts = user.getSmtpHost().split(":");
        MailSender mailSender = getMailSender(
            parts[0],
            Integer.parseInt(parts[1]),
            user.getSmtpEmail(),
            user.getSmtpPassword()
        );

        List<SimpleMailMessage> mailMessages = interviewers.stream().map(interviewer -> createMail(
            interviewer.getEmail(),
            user.getGroupName() + " " + recruitment.getName() + " ????????? ????????? ?????? ?????? ??????????????????.",
            "?????? ???????????? ????????? ???????????????.\n" + INTERVIEWER_URL_PREFIX + interviewer.getUuid())).collect(Collectors.toList());
        eventPublisher.publishEvent(new SendEmailEvent(mailSender, mailMessages));
    }

    @Authorize
    @PostMapping("applicants/form")
    void sendToApplicantsAboutForm(
        @CurrentUser Long userId,
        @RequestParam Long recruitmentId
    ) {
        List<Applicant> applicants = applicantRepository.findAllByRecruitmentId(recruitmentId);

        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String[] parts = user.getSmtpHost().split(":");
        MailSender mailSender = getMailSender(
            parts[0],
            Integer.parseInt(parts[1]),
            user.getSmtpEmail(),
            user.getSmtpPassword()
        );

        List<SimpleMailMessage> mailMessages = applicants.stream().map(applicant -> {
            if (applicant.getFormState().equals(ApplicantState.PASS)) {
                return createMail(
                    applicant.getEmail(),
                    user.getGroupName() + " " + recruitment.getName() + " ?????? ?????? ?????? ??????????????????.",
                    "?????????????????????.\n?????? ???????????? ?????? ????????? ???????????????.\n\n" + APPLICANT_URL_PREFIX
                        + applicant.getUuid()
                );
            } else {
                return createMail(
                    applicant.getEmail(),
                    user.getGroupName() + " " + recruitment.getName() + " ?????? ?????? ?????? ??????????????????.",
                    "????????????????????????.\n??????????????? ?????? ????????? ????????? ????????????????????????."
                );
            }
        }).collect(Collectors.toList());
        eventPublisher.publishEvent(new SendEmailEvent(mailSender, mailMessages));
    }

    @Authorize
    @PostMapping("applicants/interview")
    void sendToApplicantsAboutInterview(
        @CurrentUser Long userId,
        @RequestParam Long recruitmentId
    ) {
        List<Applicant> applicants = applicantRepository.findAllByRecruitmentIdAndFormState(
            recruitmentId,
            ApplicantState.PASS
        );

        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String[] parts = user.getSmtpHost().split(":");
        MailSender mailSender = getMailSender(
            parts[0],
            Integer.parseInt(parts[1]),
            user.getSmtpEmail(),
            user.getSmtpPassword()
        );

        List<SimpleMailMessage> mailMessages = applicants.stream().map(applicant -> {
            if (applicant.getInterviewState().equals(ApplicantState.PASS)) {
                return createMail(
                    applicant.getEmail(),
                    user.getGroupName() + " " + recruitment.getName() + " ?????? ?????? ?????? ??????????????????.",
                    "??????????????????! ?????? ?????????????????????."
                );
            } else {
                return createMail(
                    applicant.getEmail(),
                    user.getGroupName() + " " + recruitment.getName() + " ?????? ?????? ?????? ??????????????????.",
                    "????????????????????????.\n??????????????? ?????? ????????? ????????? ????????????????????????."
                );
            }
        }).collect(Collectors.toList());
        eventPublisher.publishEvent(new SendEmailEvent(mailSender, mailMessages));
    }

    private MailSender getMailSender(String smtpHost, int smtpPort, String smtpAddress, String smtpPassword) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(smtpHost);
        javaMailSender.setPort(smtpPort);
        javaMailSender.setUsername(smtpAddress);
        javaMailSender.setPassword(smtpPassword);

        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.auth", true);
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }

    private SimpleMailMessage createMail(String to, String subject, String text) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(to);
        smm.setSubject(subject);
        smm.setText(text);
        return smm;
    }
}
