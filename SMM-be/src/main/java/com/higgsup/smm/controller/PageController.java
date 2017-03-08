package com.higgsup.smm.controller;

import com.higgsup.smm.constant.Constant;
import com.higgsup.smm.dto.PageDTO;
import com.higgsup.smm.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by nguye on 10/01/2017.
 */
@RestController
public class PageController {
    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public String savePage(@RequestBody PageDTO pageDTO, HttpServletRequest request) {
        String token = request.getHeader(Constant.AUTH_TOKEN_HEADER);
        pageService.savePage(pageDTO, token);
        return "Succeed";
    }

    @GetMapping(value = "/page")
    public List<PageDTO> getListPage(HttpServletRequest request) {//sao lai load 2 lan nhi ?
        String token = request.getHeader(Constant.AUTH_TOKEN_HEADER);
        return pageService.pageList(token);
    }

    @RequestMapping(value = "/page/{pageId}", method = RequestMethod.GET)
    public PageDTO getPageDetail(@PathVariable String pageId, HttpServletRequest request) {
        String token = request.getHeader(Constant.AUTH_TOKEN_HEADER);
        return pageService.getPageDetail(pageId, token);
    }
}

