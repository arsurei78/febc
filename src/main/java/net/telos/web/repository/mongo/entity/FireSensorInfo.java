package net.telos.web.repository.mongo.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="fire_sensor_data")
public class FireSensorInfo {
    @Id
    private String id;
    @Field("sensor_mac")
    private String macAddress;
    @Field("temp")
    private Double temperature;
    @Field("smoke")
    private Double smoke;
    @Field("flame")
    private Double flame;
    @Field("diff_temp")
    private Double diffTemp;
    @Field("diff_smoke")
    private Double diffSmoke;
    @Field("is_use")
    private Boolean isUse;
    @Field("battery")
    private Double battery;
    @Field("reg_date")
    private LocalDateTime regDate;
}
