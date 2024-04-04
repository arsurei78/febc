package net.telos.web.dto.req.sensor;

import net.telos.web.repository.mongo.entity.FireSensorInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReqFireSensorDto {
    
    @ApiModelProperty(value = "기기 주소", example = "B000001")
    private String macAddress;
    @ApiModelProperty(value = "온도", example = "B000001")
    private Double temperature;
    @ApiModelProperty(value = "연기", example = "B000001")
    private Double smoke;
    @ApiModelProperty(value = "불꽃", example = "B000001")
    private Double flame;
    @ApiModelProperty(value = "차동온도", example = "B000001")
    private Double diffTemp;
    @ApiModelProperty(value = "차동연기", example = "B000001")
    private Double diffSmoke;
    @ApiModelProperty(value = "사용여부", example = "B000001")
    private Boolean isUser;
    @ApiModelProperty(value = "배터리", example = "B000001")
    private Double battery;


    public FireSensorInfo toEntity() {
        return FireSensorInfo
                .builder()
                .isUse(this.isUser)
                .macAddress(this.macAddress)
                .temperature(this.temperature)
                .smoke(this.smoke)
                .flame(this.flame)
                .diffSmoke(this.diffSmoke)
                .diffTemp(this.diffTemp)
                .battery(this.battery)
                .regDate(LocalDateTime.now())
                .build();
    }
}
