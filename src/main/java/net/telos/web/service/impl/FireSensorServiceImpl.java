package net.telos.web.service.impl;

import net.telos.cmmn.web.BaseResponse;
import net.telos.web.dto.req.sensor.ReqFireSensorDto;
import net.telos.web.repository.mongo.entity.FireSensorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FireSensorServiceImpl {

   // private final FireSensorInfoRepository fireSensorInfoRepository;

    public BaseResponse<List<FireSensorInfo>> getSensorInfo(){
        ///return new BaseResponse<>(fireSensorInfoRepository.getFireSensoList());
        return new BaseResponse<>();
    }

    public BaseResponse<String> saveSensorInfo(ReqFireSensorDto reqDto){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = null;
        for(int i = 0; i < 1000; i++) {
            FireSensorInfo entity = reqDto.toEntity();
            if (i < 200 ) {
                localDateTime = now.minusDays(3);
            } else if (i < 400 ) {
                localDateTime = now.minusDays(2);
            } else if (i < 600 ) {
                localDateTime = now.minusDays(1);
            } else if (i < 700 ) {
                localDateTime = now;
            } else if (i < 800 ) {
                localDateTime = now.plusDays(1);
            } else if (i < 900 ) {
                localDateTime = now.plusDays(2);
            } else {
                localDateTime = now.plusDays(3);
            }
            entity.setRegDate(localDateTime);
            //fireSensorInfoRepository.save(entity);
        }

        return new BaseResponse<>();
    }
}
