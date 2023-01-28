package org.sparcs.hackathon.hteam.mozipserver.controllers;


import java.util.List;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.Authorize;
import org.sparcs.hackathon.hteam.mozipserver.config.auth.CurrentUser;
import org.sparcs.hackathon.hteam.mozipserver.entities.Applicant;
import org.sparcs.hackathon.hteam.mozipserver.entities.Interviewer;
import org.sparcs.hackathon.hteam.mozipserver.entities.Recruitment;
import org.sparcs.hackathon.hteam.mozipserver.entities.User;
import org.sparcs.hackathon.hteam.mozipserver.enums.ApplicantState;
import org.sparcs.hackathon.hteam.mozipserver.repositories.ApplicantRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.InterviewerRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.RecruitmentRepository;
import org.sparcs.hackathon.hteam.mozipserver.repositories.UserRespository;
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

        for (Interviewer interviewer :interviewers) {
            sendMail(
                mailSender,
                interviewer.getEmail(),
                user.getGroupName() + " " + recruitment.getName() + " 인터뷰 스케줄 선택 요청 안내드립니다.",
                "아래 링크에서 일정을 잡아주세요.\n" + INTERVIEWER_URL_PREFIX + interviewer.getUuid());
        }
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

        for (Applicant applicant :applicants) {
            if (applicant.getFormState().equals(ApplicantState.PASS)) {
                sendMail(
                    mailSender,
                    applicant.getEmail(),
                    user.getGroupName() + " " + recruitment.getName() + " 서류 전형 결과 안내드립니다.",
                    "합격하셨습니다.\n아래 링크에서 면접 일정을 잡아주세요.\n\n" + APPLICANT_URL_PREFIX + applicant.getUuid()
                );
            } else {
                sendMail(
                    mailSender,
                    applicant.getEmail(),
                    user.getGroupName() + " " + recruitment.getName() + " 서류 전형 결과 안내드립니다.",
                    "불합격하셨습니다.\n죄송하지만 다음 기회에 재지원 부탁드리겠습니다."
                );
            }
        }
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

        for (Applicant applicant :applicants) {
            if (applicant.getInterviewState().equals(ApplicantState.PASS)) {
                sendMail(
                    mailSender,
                    applicant.getEmail(),
                    user.getGroupName() + " " + recruitment.getName() + " 면접 전형 결과 안내드립니다.",
                    "축하드립니다! 최종 합격하셨습니다."
                );
            } else {
                sendMail(
                    mailSender,
                    applicant.getEmail(),
                    user.getGroupName() + " " + recruitment.getName() + " 면접 전형 결과 안내드립니다.",
                    "불합격하셨습니다.\n죄송하지만 다음 기회에 재지원 부탁드리겠습니다."
                );
            }
        }
    }

    private void sendMail(MailSender mailSender, String to, String subject, String text) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(to);
        smm.setSubject(subject);
        smm.setText(text);
        mailSender.send(smm);
    }
}
