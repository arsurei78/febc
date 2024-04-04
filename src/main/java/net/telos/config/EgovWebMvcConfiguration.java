package net.telos.config;

import net.telos.cmmn.web.EgovBindingInitializer;
import net.telos.web.service.impl.UserLoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class EgovWebMvcConfiguration extends WebMvcConfigurationSupport {

	private final UserLoginServiceImpl userLoginService;
	private final Environment environment;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String location = environment.getProperty("app.file.upload-dir");
		// 업로드 매핑
		registry.addResourceHandler("/uploads/**").addResourceLocations(location);

		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");

	}


	/**
	 *  Get요청시 파라메터 변환
	 *  현재 trim, date형식 변환
	 */
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter rmha = super.createRequestMappingHandlerAdapter();
		rmha.setWebBindingInitializer(new EgovBindingInitializer());
		return rmha;
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// AuthenticationPrincipal 사용시 등록
		argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
	}

	/**
	 * 필터 정의
	 * UTF-8 강제전환
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<CharacterEncodingFilter> encodingFilterBean() {
		FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setForceEncoding(true);
		filter.setEncoding("UTF-8");
		registrationBean.setFilter(filter);
		return registrationBean;
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
		impl.setUserDetailsService(userLoginService);
		impl.setHideUserNotFoundExceptions(false) ;
		return impl ;
	}

}
