package com.javabom.bomplatform.web.mission.controller;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.web.mission.controller.dto.request.MissionRegisterDto;
import com.javabom.bomplatform.web.mission.controller.dto.response.MissionDto;
import com.javabom.bomplatform.web.mission.controller.dto.response.MissionDtos;
import com.javabom.bomplatform.web.mission.service.MissionService;
import com.javabom.bomplatform.web.utils.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(tags="Mission")
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class MissionController {

    private static final String DEFAULT_PAGE_NO = "0";
    private static final String DEFAULT_PAGE_SIZE = "20";

    private final MissionService missionService;

    @ApiOperation(value = "Mission Register", notes = "미션 저장")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid Uri, File Format"),
    })
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

    @ApiOperation(value = "Get Mission", notes = "미션 조회(단건)")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid Mission Id"),
            @ApiResponse(code = 404, message = "Not found Mission")
    })
    @GetMapping(value = "/mission/{missionId}")
    public ResponseEntity<MissionDto> getMission(@PathVariable("missionId") final Long missionId) {
        final Mission mission = missionService.getMission(missionId);
        return ResponseEntity.ok().body(toMissionDto(mission));
    }

    @ApiOperation(value = "Get Mission", notes = "미션 조회(다중)")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid User"),
            @ApiResponse(code = 404, message = "Not Found Mission or Page")
    })
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
