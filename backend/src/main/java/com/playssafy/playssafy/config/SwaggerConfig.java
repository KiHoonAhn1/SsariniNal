package com.playssafy.playssafy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EnableSwagger2 // Swagger2를 사용하겠다.
@Configuration // 설정 파일 명시.
public class SwaggerConfig {
    private String version = "v1";
    private String title = "PLAYSSAFY REST API";
    private String description = "PLAYSSAFY REST API Docs";

    @Bean
    public Docket api() {
        // Global 응답 메세지 추가
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("처리 완료.").build());
        responseMessages.add(new ResponseMessageBuilder().code(404).message("페이지를 찾을 수 없습니다.").build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message("서버 에러.").
                responseModel(new ModelRef("Error")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(getConsumeContentTypes()) // 입력 형식 정의(아래 메서드 참고)
                .produces(getProduceContentTypes()) // 출력 형식 정의(아래 메서드 참고)
                .apiInfo(apiInfo()) // 문서 정보 정의(아래 메서드 참고)
                .useDefaultResponseMessages(false) // 기본 설정된 Response 메세지 설정을 사용하지 않는다.
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages); // GET, POST 요청에 대해서 일반적으로 위에 설정한 응답 메세지를 출력해주어라
    }

    // Swagger에서 받을 수 있는 데이터 형식을 정의한다(Request).
    private Set<String> getConsumeContentTypes() {
        // HashSet 자료구조를 활용하여 관련 타입을 저장한다.
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8"); // JSON
        consumes.add("application/xml;charset=UTF-8"); // XML
        return consumes;
    }

    // Swagger에서 반환할 데이터 형식을 정의한다(Response).
    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    // 해당 API 문서의 정보를 명시해주는 메서드
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title) // API 문서 제목
                .description(description) // API 문서에 대한 설명. HTML 입력 가능.
                .version(version) // 버전 명시
                .build();
        // 이외에도 contact, license, licenseUrl 등을 설정할 수 있다.
    }
}
