package net.telos.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

// 개발 환경에서만 적용
@Profile({ "dev", "prod", "stg"})
@Configuration
@EnableSwagger2
public class SwaggerConfig  {
    
    // 인증 정보 키
    private static final String AUTH = "Authorization";

    @Bean
    public Docket api(TypeResolver typeResolver) {

        String description = """
        <p>JWT Rest API 서비스<br></p>
        <p></p>
        <p>Authorization 토큰을 등록(Authorize)해야 API 호출이 가능합니다.</p>
          <p><strong>1. 외부 접속에서(Unity등) 로그인/로그아웃</strong> 메뉴를 통해 생성하며 로그인시 반환 데이터에 tokenList에서 access_token과 refresh_token정보를 확인하실수 있습니다. </p>
          <p>2. Token은 DEV / PRODUCT환경에 따라 Secret KEY가 달라 서로 다른 환경에서 교차 사용이 불가합니다.</p>
          <p>&nbsp;&nbsp; 또한 Swagger는 Production 환경에서는 접근할 수 없습니다.</p>
          <p><strong>★반환데이터에 관하여</strong></p>
          <p> - 요청의 경우, code(응답 성공여부), message(에러 메세지), data(표시 데이터), validate(유효성 체크 에러)형식으로 반환됩니다.</p>
          <p> - code(응답성공여부)의 경우, SUCCESS(성공), ERROR(시스템에러), ERROR_LOGIN(재 로그인이 필요), VALIDATE(입력체크 에러)가 반환됩니다.</p>
          <p> - SUCCESS의 경우, message의 값이나 data값이 없는 상태로 반환될수 있습니다.</p>
          <p> - ERROR, ERROR_LOGIN의 경우, message에 에러내용이 적혀서 반환됩니다.</p>
          <p> - SYSTEM_ERROR의 경우, 서버측에 Exception이 발생하였을때 반환됩니다. 에러 메세지는 특별히 설정하지 않을 예정입니다.</p>
          <p> - VALIDATE_ERROR(입력체크 에러)의 경우, validate에 field, message의 페어의 오브젝트가 리스트 형태로 반환되어있습니다.</p>
          <p> - field의 경우, 에러가 발생한 요청 항목이고, message는 발생 에러 내용이 됩니다.  예) password 값이 null인경우, field에 password가 message에 패스워드를 입력하세요가 반환 됩니다.</p>
          <p>3. 회원 로그인시 로그인 식별자를 DB에 저장하여 요청시 DB저장 요청자와 JWT에 보관된 식별자가 동일한지 판단하여 2중 로그인을 방지 합니다.</p>
        """;
        Contact contact = new Contact("JWT REST API", "", "");

        ApiInfo apiInfo = new ApiInfo("JWT REST API",
                description,
                "API 1.0",
                "",
                contact,
                "Apache License Version 2.0",
                "https://www.thetelos.net", Arrays.asList());

        return new Docket(DocumentationType.OAS_30)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.telos.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    private Set<String> getConsumeContentTypes(){
        Set<String> consume = new HashSet<>();
        consume.add("application/json;charset=UTF-8");
        return consume;
    }

    private ApiKey apiKey(){
        return new ApiKey(AUTH, AUTH, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(AUTH, authorizationScopes));
    }
}
