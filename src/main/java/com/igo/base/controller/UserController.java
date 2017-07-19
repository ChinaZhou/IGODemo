package com.igo.base.controller;

import com.igo.base.dto.ServiceResponse;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/6/19.
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @RequestMapping("/manageUser")
    public String manageUser(Model model) {
        model.addAttribute("hint", "manageUser");
        return  "/base/user_manage";
    }

    @ResponseBody
    @RequestMapping("/showAjaxInfo")
    public ServiceResponse<String> showAjaxInfo(@RequestParam(value = "name") String name ) {
        ServiceResponse<String> response = new ServiceResponse<String>();
        response.setResult(name + System.currentTimeMillis());
        return  response;
    }

}
