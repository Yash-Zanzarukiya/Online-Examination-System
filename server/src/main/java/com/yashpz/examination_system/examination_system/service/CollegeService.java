package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.CollegeDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.CollegeMapper;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.repository.CollegeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollegeService {

    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    public CollegeDTO createCollege(CollegeDTO collegeDTO) {
        College college = CollegeMapper.toEntity(collegeDTO);
        collegeRepository.save(college);
        return CollegeMapper.toDTO(college);
    }

    public CollegeDTO getCollegeById(UUID id) {
        College college = fetchCollegeById(id);
        return CollegeMapper.toDTO(college);
    }

    public List<CollegeDTO> getAllColleges() {
        List<College> colleges = collegeRepository.findAll();
        return CollegeMapper.toDTO(colleges);
    }

    public CollegeDTO updateCollege(UUID id, CollegeDTO collegeDTO) {
        College college = fetchCollegeById(id);

        CollegeMapper.updateEntity(college, collegeDTO);
        collegeRepository.save(college);

        return CollegeMapper.toDTO(college);
    }

    public void deleteCollege(UUID id) {
        if (!collegeRepository.existsById(id))
            throw new ApiError(HttpStatus.NOT_FOUND, "College Not Found");
        collegeRepository.deleteById(id);
    }

    // <--------------- Helpers --------------->

    public College fetchCollegeById(UUID id) {
        return collegeRepository.findById(id).orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "College Not Found"));
    }
}
