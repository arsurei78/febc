package net.telos.web.controller;

import net.telos.cmmn.web.BaseResponse;
import net.telos.web.repository.mongo.entity.FireSensorInfo;
import net.telos.web.service.impl.FireSensorServiceImpl;
import net.telos.web.dto.req.sensor.ReqFireSensorDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class SensorController {

    private final FireSensorServiceImpl fireSensorService;

    @PostMapping
    @ApiOperation(tags = "1.센서 데이터 ", value = "저장 테스트")
    public BaseResponse<String> saveSensor(@RequestBody ReqFireSensorDto reqDto) {
        return fireSensorService.saveSensorInfo(reqDto);
    }

    @GetMapping
    @ApiOperation(tags = "1.센서 데이터 ", value = "저장 검색")
    public BaseResponse<List<FireSensorInfo>> getSensorInfo() {
        return fireSensorService.getSensorInfo();
    }

}
