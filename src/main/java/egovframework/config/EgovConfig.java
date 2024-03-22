package egovframework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	EgovConfigAspect.class,
	EgovConfigCommon.class,
	EgovConfigDatasource.class,
	EgovConfigSecondDatasource.class,
	EgovWebMvcConfiguration.class,
	EgovWebMvcConfiguration.class,
	InitConfig.class,
	QueryDslConfig.class,
	CacheConfig.class
})
public class EgovConfig {

}
