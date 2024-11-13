package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.CollegeDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.repository.CollegeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CollegeService {

    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    public CollegeDTO createCollege(CollegeDTO collegeDTO) {
        College college = new College();
        college.setName(collegeDTO.getName());

        College savedCollege = collegeRepository.save(college);

        return new CollegeDTO(college.getId(),savedCollege.getName());
    }

    public CollegeDTO getCollegeById(UUID id) {
        Optional<College> collegeOpt = collegeRepository.findById(id);
        College college = collegeOpt.orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "College Not Found"));

        return new CollegeDTO(college.getId(), college.getName());
    }

    public List<CollegeDTO> getAllColleges() {
        return collegeRepository.findAll().stream()
                .map(college -> new CollegeDTO(college.getId(), college.getName()))
                .collect(Collectors.toList());
    }

    public CollegeDTO updateCollege(UUID id, CollegeDTO collegeDTO) {
        Optional<College> collegeOpt = collegeRepository.findById(id);
        College existingCollege = collegeOpt.orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "College Not Found"));

        existingCollege.setName(collegeDTO.getName());
        College updatedCollege = collegeRepository.save(existingCollege);

        return new CollegeDTO(updatedCollege.getId(), updatedCollege.getName());
    }

    public void deleteCollege(UUID id) {
        if (!collegeRepository.existsById(id))
            throw new ApiError(HttpStatus.NOT_FOUND, "College Not Found");
        collegeRepository.deleteById(id);
    }
}
