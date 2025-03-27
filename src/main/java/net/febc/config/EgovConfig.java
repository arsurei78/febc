package net.febc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	SecurityConfig.class,
	EgovConfigAspect.class,
	EgovConfigCommon.class,
	EgovConfigDatasource.class,
	EgovWebMvcConfiguration.class,
	QueryDslConfig.class,
	CacheConfig.class,
	EgovMongodbConfig.class,
	SwaggerConfig.class
})
public class EgovConfig {

}
