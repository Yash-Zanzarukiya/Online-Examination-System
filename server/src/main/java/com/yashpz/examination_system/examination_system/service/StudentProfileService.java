package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Roles;
import com.yashpz.examination_system.examination_system.dto.StudentProfile.ExcelStudentData;
import com.yashpz.examination_system.examination_system.dto.StudentProfile.StudentDataDTO;
import com.yashpz.examination_system.examination_system.dto.StudentProfile.StudentProfileRequestDTO;
import com.yashpz.examination_system.examination_system.dto.StudentProfile.StudentProfileResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.StudentProfileMapper;
import com.yashpz.examination_system.examination_system.model.Auth;
import com.yashpz.examination_system.examination_system.model.StudentProfile;
import com.yashpz.examination_system.examination_system.model.User;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.repository.AuthRepository;
import com.yashpz.examination_system.examination_system.repository.StudentProfileRepository;
import com.yashpz.examination_system.examination_system.repository.UserRepository;
import com.yashpz.examination_system.examination_system.utils.ResourceAccessUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserService userService;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final CollegeService collegeService;
    private final StudentDataFileService studentDataFileService;
    private final ResourceAccessUtil resourceAccessUtil;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public StudentProfileResponseDTO createStudentProfile(StudentProfileRequestDTO studentProfileDTO) {
        StudentProfile existingProfile = studentProfileRepository.findByUserId(studentProfileDTO.getUserId());

        if (existingProfile!=null)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Student Profile Already Exists");

        User user = userService.fetchUserById(studentProfileDTO.getUserId());
        College college = collegeService.fetchCollegeById(studentProfileDTO.getCollegeId());

        StudentProfile studentProfile = StudentProfileMapper.toEntity(studentProfileDTO);
        studentProfile.setUser(user);
        studentProfile.setCollege(college);

        studentProfileRepository.save(studentProfile);

        return StudentProfileMapper.toResponseDTO(studentProfile);
    }

    public StudentProfileResponseDTO getStudentProfileByUserId(UUID userId) {
        StudentProfile studentProfile = studentProfileRepository.findByUserId(userId);

        if (studentProfile==null)
            throw new ApiError(HttpStatus.NOT_FOUND, "Student Profile Not Found");

        return StudentProfileMapper.toResponseDTO(studentProfile);
    }

    public StudentProfileResponseDTO getStudentProfileById(UUID profileId) {
        StudentProfile studentProfile = fetchStudentProfileById(profileId);
        return StudentProfileMapper.toResponseDTO(studentProfile);
    }

    public StudentProfileResponseDTO updateStudentProfile(UUID profileId, StudentProfileRequestDTO studentProfileDTO) {
        StudentProfile existingProfile = fetchStudentProfileById(profileId);

        resourceAccessUtil.AdminOrOwnerAccess(existingProfile.getUser().getAuth().getId());

        College college = collegeService.fetchCollegeById(studentProfileDTO.getCollegeId());

        StudentProfileMapper.updateEntity(existingProfile, studentProfileDTO);
        existingProfile.setCollege(college);

        studentProfileRepository.save(existingProfile);

        return StudentProfileMapper.toResponseDTO(existingProfile);
    }

    public void deleteStudentProfile(UUID userId) {
        StudentProfile existingProfile = fetchStudentProfileById(userId);

        resourceAccessUtil.AdminOrOwnerAccess(existingProfile.getUser().getAuth().getId());

        studentProfileRepository.deleteById(userId);
    }

    public List<StudentDataDTO> uploadStudentDataFile(MultipartFile studentDataFile, UUID collegeId){
        resourceAccessUtil.AdminOnlyAccess();

        College college = collegeService.fetchCollegeById(collegeId);

        List<ExcelStudentData> excelStudentData = studentDataFileService.processStudentDataFile(studentDataFile);

        List<Auth> authEntities = new ArrayList<>();
        List<User> userEntities = new ArrayList<>();
        List<StudentProfile> studentProfiles = new ArrayList<>();

        for (ExcelStudentData studentData : excelStudentData) {
            if (authRepository.existsByEmail(studentData.getEmail())) continue;

            Auth auth = createAuthFromStudentData(studentData);
            authEntities.add(auth);

            User user = createUserFromStudentData(studentData, auth);
            userEntities.add(user);

            StudentProfile studentProfile = createStudentProfileFromStudentData(studentData, user, college);
            studentProfiles.add(studentProfile);
        }

        authRepository.saveAll(authEntities);
        userRepository.saveAll(userEntities);
        studentProfileRepository.saveAll(studentProfiles);

        return StudentProfileMapper.toStudentDataDTO(studentProfiles);
    }

    public List<StudentDataDTO> getAllStudentData(UUID collegeId, Integer passout){
        resourceAccessUtil.AdminOnlyAccess();

        List<StudentProfile> studentProfiles = studentProfileRepository.findAllByFilters(collegeId, passout);

        return StudentProfileMapper.toStudentDataDTO(studentProfiles);
    }

    // <--------------- Helpers --------------->

    public StudentProfile fetchStudentProfileById(UUID id) {
        return studentProfileRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Student Profile Not Found"));
    }

    public Auth createAuthFromStudentData(ExcelStudentData studentData){
        Auth auth = new Auth();
        auth.setEmail(studentData.getEmail());
        auth.setUsername(studentData.getEmail().split("@")[0].replaceAll("[^a-zA-Z0-9]", ""));
        auth.setPassword(passwordEncoder.encode(studentData.getPhone()));
        auth.setVerified(true);
        return auth;
    }

    public User createUserFromStudentData(ExcelStudentData studentData, Auth auth){
        User user = new User();
        user.setAuth(auth);
        user.setFullName(studentData.getFullName());
        user.setRole(Roles.STUDENT);
        return user;
    }

    public StudentProfile createStudentProfileFromStudentData(ExcelStudentData studentData, User user, College college){
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setUser(user);
        studentProfile.setFullName(studentData.getFullName());
        studentProfile.setBranch(studentData.getBranch());
        studentProfile.setPhone(studentData.getPhone());
        studentProfile.setPassout(studentData.getPassout());
        studentProfile.setCollege(college);
        return studentProfile;
    }

}
