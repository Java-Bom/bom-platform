package com.javabom.bomplatform.web.mission.controller;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.web.mission.controller.dto.request.MissionRegisterDto;
import com.javabom.bomplatform.web.mission.controller.dto.response.MissionDto;
import com.javabom.bomplatform.web.mission.controller.dto.response.MissionDtos;
import com.javabom.bomplatform.web.mission.service.MissionService;
import com.javabom.bomplatform.web.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class MissionController {

    private static final String DEFAULT_PAGE_NO = "0";
    private static final String DEFAULT_PAGE_SIZE = "20";

    private final MissionService missionService;

    @PostMapping(value = "/mission")
    public void register(MissionRegisterDto missionRegisterDto, MultipartFile missionFile) throws IOException {
        final String filePath = uploadFile(missionFile);
        missionService.register(missionRegisterDto.getRepositoryUri(), missionRegisterDto.getMissionStep(), filePath);
    }

    private String uploadFile(final MultipartFile missionFile) throws IOException {
        final String fileName = StringUtils.cleanPath(Objects.requireNonNull(missionFile.getOriginalFilename()));
        FileUploadUtil.saveFile(fileName, missionFile);
        return "";
    }

    @GetMapping(value = "/mission/{missionId}")
    public ResponseEntity<MissionDto> getMission(@PathVariable("missionId") final Long missionId) {
        final Mission mission = missionService.getMission(missionId);
        return ResponseEntity.ok().body(toMissionDto(mission));
    }

    @GetMapping(value = "/mission")
    public ResponseEntity<MissionDtos> getMission(@RequestParam(defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
        List<Mission> mission = missionService.getMission(pageNo, pageSize);
        List<MissionDto> missionDtos = mission.stream()
                .map(this::toMissionDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new MissionDtos(missionDtos));
    }

    private MissionDto toMissionDto(final Mission mission) {
        return MissionDto.builder()
                .id(mission.getId())
                .fileUri(mission.getFilePath())
                .missionState(mission.getMissionState())
                .repositoryUri(mission.getRepositoryUrl())
                .missionStep(mission.getMissionStep())
                .createDate(mission.getCreateDate().toString())
                .build();
    }
}
