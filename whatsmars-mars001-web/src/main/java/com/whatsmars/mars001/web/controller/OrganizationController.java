package com.whatsmars.mars001.web.controller;

import com.whatsmars.mars001.service.OrganizationService;
import com.whatsmars.common.pojo.Result;
import com.whatsmars.mars001.domain.enums.*;
import com.whatsmars.mars001.domain.permission.Permission;
import com.whatsmars.mars001.domain.query.OrganizationQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by shenhongxi on 15/4/13.
 */
@Controller
@RequestMapping(value = "/organization")
@Permission(roles = {UserTypeEnum.ADMIN})
public class OrganizationController extends BaseController{

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/list")
    public ModelAndView list(@RequestParam(value = "status",defaultValue = "-1") Integer status,
                             @RequestParam(value = "name",required = false) String name,
                             @RequestParam(value = "email",required = false) String email,
                             @RequestParam(value = "code",required = false) String code,
                             @RequestParam(value = "page",required = false,defaultValue = "1") Integer page) {
        ModelAndView mav = new ModelAndView();
        OrganizationQuery query = new OrganizationQuery();
        query.setCurrentPage(page);
        if (StringUtils.isNotBlank(name)) {
            query.setName(name);
        }
        if (StringUtils.isNotBlank(email)) {
            query.setEmail(email);
        }
        if (StringUtils.isNotBlank(code)) {
            query.setCode(code);
        }
        query.setStatus(status);
        Result result = this.organizationService.list(query);
        mav.addAllObjects(result.getAll());
        mav.addObject("statusEnums", OrganizationStatusEnum.values());
        mav.addObject("levelEnums", OrganizationLevelEnum.values());
        mav.addObject("emailValidating", OrganizationStatusEnum.EMAIL_VALIDATING.code);
        mav.addObject("notSubmited", OrganizationStatusEnum.MATERIAL_NOT_SUBMITTED.code);
        mav.addObject("auditing", OrganizationStatusEnum.MATERIAL_AUDITING.code);
        mav.addObject("auditedSuccess", OrganizationStatusEnum.MATERIAL_AUDITED_SUCCESS.code);
        mav.addObject("auditedFailed", OrganizationStatusEnum.MATERIAL_AUDITED_FAILED.code);
        return mav;
    }


}
