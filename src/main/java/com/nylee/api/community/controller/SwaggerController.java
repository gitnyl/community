package com.nylee.api.community.controller;

import com.google.common.base.Optional;
import io.swagger.models.Swagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static springfox.documentation.swagger.common.HostNameProvider.componentsFrom;

@Profile({"local"})
@Controller
@ApiIgnore
public class SwaggerController {
    public static final String DEFAULT_URL = "/v2/api-docs";
    private static final String HAL_MEDIA_TYPE = "application/hal+json";


    private static final Logger logger = LoggerFactory.getLogger(SwaggerController.class);

    @Value("${springfox.documentation.swagger.v2.host:DEFAULT}")
    private String hostNameOverride;

    @Autowired
    private DocumentationCache documentationCache;

    @Autowired
    private ServiceModelToSwagger2Mapper mapper;

    @Autowired
    private JsonSerializer jsonSerializer;

    @Value("${spring.profiles.active}")
    private String active;

    @ApiIgnore
    /*@RequestMapping(value = "${springfox.documentation.swagger.v2.path:" + DEFAULT_URL + "}",
            method = RequestMethod.GET, produces = { APPLICATION_JSON_VALUE, HAL_MEDIA_TYPE })*/
    @RequestMapping(value = DEFAULT_URL,
            method = RequestMethod.GET, produces = { APPLICATION_JSON_VALUE, HAL_MEDIA_TYPE })
    public
    @ResponseBody
    ResponseEntity<Json> getDocumentation(
            @RequestParam(value = "group", required = false) String swaggerGroup,
            HttpServletRequest servletRequest) {
        logger.error("swagger Call!!!!!!! START");
        String groupName = Optional.fromNullable(swaggerGroup).or(Docket.DEFAULT_GROUP_NAME);
        Documentation documentation = documentationCache.documentationByGroup(groupName);
        if (documentation == null) {
            return new ResponseEntity<Json>(HttpStatus.NOT_FOUND);
        }
        Swagger swagger = mapper.mapDocumentation(documentation);
        logger.error("swagger Call!!!!!!!");
        if( active.equals("td19")) {
            logger.error("swagger set Base Path!!!!!!!");
            final UriComponents uriComponents = componentsFrom(servletRequest,"/jcommon");
            swagger.basePath("/jcommon");
            swagger.host(hostName(uriComponents));
        }
        return new ResponseEntity<Json>(jsonSerializer.toJson(swagger), HttpStatus.OK);
    }

    private String hostName(UriComponents uriComponents) {
        if ("DEFAULT".equals(hostNameOverride)) {
            String host = uriComponents.getHost();
            int port = uriComponents.getPort();
            if (port > -1) {
                return String.format("%s:%d", host, port);
            }
            return host;
        }
        return hostNameOverride;
    }
}
