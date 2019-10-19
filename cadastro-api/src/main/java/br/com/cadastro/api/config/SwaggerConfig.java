package br.com.cadastro.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Classe de configuração referente ao Swagger
 * @author Leonardo Araújo
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private final String basePackage = "br.com.cadastro.api";

	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
//                .paths(regex("/.*"))
                .build()
                .apiInfo(metaData());
    }

	public ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Cadastro - API")
				.description("API para cadastro de clientes e análise de crédito")
				.version("1.0")
				.contact(new Contact("Leonardo Araújo", "https://github.com/leoarauj", "leo.arauj@outlook.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licesen.html")
				.build();
	}
}
