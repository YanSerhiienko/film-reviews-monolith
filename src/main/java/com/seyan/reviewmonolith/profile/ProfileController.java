package com.seyan.reviewmonolith.profile;

import com.seyan.reviewmonolith.profile.dto.ProfileCreationDTO;
import com.seyan.reviewmonolith.profile.dto.ProfileMapper;
import com.seyan.reviewmonolith.profile.dto.ProfileResponseDTO;
import com.seyan.reviewmonolith.profile.dto.ProfileUpdateDTO;
import com.seyan.reviewmonolith.responseWrapper.CustomResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
@RestController
public class ProfileController {
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponseWrapper<ProfileResponseDTO>> createProfile(@RequestBody @Valid ProfileCreationDTO dto) {
        Profile profile = profileService.createProfile(dto);
        ProfileResponseDTO response = profileMapper.mapProfileToProfileResponseDTO(profile);
        CustomResponseWrapper<ProfileResponseDTO> wrapper = CustomResponseWrapper.<ProfileResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Profile has been successfully created")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<CustomResponseWrapper<ProfileResponseDTO>> updateProfile(@RequestBody @Valid ProfileUpdateDTO dto) {
        Profile profile = profileService.updateProfile(dto);
        ProfileResponseDTO response = profileMapper.mapProfileToProfileResponseDTO(profile);
        CustomResponseWrapper<ProfileResponseDTO> wrapper = CustomResponseWrapper.<ProfileResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Profile has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<CustomResponseWrapper<ProfileResponseDTO>> deleteProfile(@PathVariable("id") Long profileId) {
        profileService.deleteProfile(profileId);
        CustomResponseWrapper<ProfileResponseDTO> wrapper = CustomResponseWrapper.<ProfileResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Profile has been deleted")
                .data(null)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseWrapper<ProfileResponseDTO>> profileDetails(@PathVariable("id") Long profileId) {
        Profile profile = profileService.getProfileById(profileId);
        ProfileResponseDTO response = profileMapper.mapProfileToProfileResponseDTO(profile);
        CustomResponseWrapper<ProfileResponseDTO> wrapper = CustomResponseWrapper.<ProfileResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Profile details")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomResponseWrapper<List<ProfileResponseDTO>>> getAllProfiles() {
        List<Profile> allProfiles = profileService.getAllProfiles();
        List<ProfileResponseDTO> response = profileMapper.mapProfileToProfileResponseDTO(allProfiles);
        CustomResponseWrapper<List<ProfileResponseDTO>> wrapper = CustomResponseWrapper.<List<ProfileResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all profiles")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
