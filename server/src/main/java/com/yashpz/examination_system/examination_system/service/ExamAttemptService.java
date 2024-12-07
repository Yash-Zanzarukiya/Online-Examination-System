package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.ExamResponse.ExamAttemptRequestDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ExamAttemptMapper;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.User;
import com.yashpz.examination_system.examination_system.repository.ExamAttemptRepository;
import com.yashpz.examination_system.examination_system.repository.ExamRepository;
import com.yashpz.examination_system.examination_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class ExamAttemptService {

//    private final ExamAttemptRepository examAttemptRepository;
//    private final ExamRepository examRepository;
//    private final UserRepository userRepository;
//
//    public ExamAttemptService(ExamAttemptRepository examAttemptRepository, ExamRepository examRepository, UserRepository userRepository) {
//        this.examAttemptRepository = examAttemptRepository;
//        this.examRepository = examRepository;
//        this.userRepository = userRepository;
//    }
//
//    public void createExamAttempt(ExamAttemptRequestDTO examAttemptRequestDTO) {
//        Exam exam = examRepository.findById(examAttemptRequestDTO.getExamId())
//                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND,"Exam not found"));
//        User user = userRepository.findById(examAttemptRequestDTO.getUserId())
//                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "User not found"));
//
//        ExamAttempt examAttempt = ExamAttemptMapper.toEntity(examAttemptRequestDTO, exam, user);
//
//        examAttemptRepository.save(examAttempt);
//    }
//
//    public void updateExamAttempt(ExamAttemptRequestDTO examAttemptRequestDTO) {
//        ExamAttempt examAttempt = examAttemptRepository.findByUserIdAndExamId(examAttemptRequestDTO.getUserId(), examAttemptRequestDTO.getExamId());
//        ExamAttemptMapper.updateEntity(examAttempt, examAttemptRequestDTO);
//        examAttemptRepository.save(examAttempt);
//    }
}
