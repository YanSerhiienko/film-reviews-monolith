package com.seyan.reviewmonolith.profile;

import com.seyan.reviewmonolith.exception.profile.ProfileNotFoundException;
import com.seyan.reviewmonolith.profile.dto.ProfileCreationDTO;
import com.seyan.reviewmonolith.profile.dto.ProfileMapper;
import com.seyan.reviewmonolith.profile.dto.ProfileUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public Profile createProfile(ProfileCreationDTO dto) {
        Profile profile = profileMapper.mapPofileCreationDTOToProfile(dto);
        return profileRepository.save(profile);
    }

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException(
                String.format("No profile found with the provided ID: %s", id)
        ));
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile updateProfile(ProfileUpdateDTO dto) {
        Profile profile = profileRepository.findById(dto.id()).orElseThrow(() -> new ProfileNotFoundException(
                String.format("No profile found with the provided ID: %s", dto.id())
        ));
        Profile mapped = profileMapper.mapProfileUpdateDTOToProfile(dto, profile);
        return profileRepository.save(mapped);
    }

    public void deleteProfile(Long id) {
        profileRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException(
                String.format("Cannot delete profile:: No profile found with the provided ID: %s", id)));
        profileRepository.deleteById(id);
    }
}
