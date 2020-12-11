package com.warner_dair.configurations;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Component
public class ThymeleafBean{

    // Return the URL with lang request parameter removed.
    // This can then be replaced (using Thymeleaf) by a different lang=iso_code
    public String removeLangFromUrl()
    {
        return ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam("lang").toUriString();
    }
}
