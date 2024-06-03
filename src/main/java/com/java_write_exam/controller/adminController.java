package com.java_write_exam.controller;

import com.java_write_exam.DTO.PasswordDTO;
import com.java_write_exam.DTO.UserClaim;
import com.java_write_exam.VO.JWTAndUserInfoVO;
import com.java_write_exam.common.LoginMessage;
import com.java_write_exam.entity.Employee;
import com.java_write_exam.service.EmployeeService;
import com.java_write_exam.util.JwtUtil;
import com.java_write_exam.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class adminController {

    @Autowired          //自动注入
    private EmployeeService empService;

    @PostMapping("/login")
    public Result<JWTAndUserInfoVO> user_login(@RequestBody Employee emp) {
        log.info("登录:{}", emp);
        // 通过service层做校验
        Employee e = empService.login(emp);
        if (e!=null && e.getStatue()==0) {
            return Result.error("用户账号禁用");
        }
        if (e != null){
            // 成功则下发JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("name", e.getName());
            claims.put("account", e.getAccount());
            String jwt = JwtUtil.createJWT(claims);
            // 下发令牌
            JWTAndUserInfoVO jU = new JWTAndUserInfoVO();
            jU.setJwt(jwt);
            jU.setId(e.getId());
            jU.setName(e.getName());
            jU.setPower(e.getPower());
            return Result.success(jU);

        }
        return Result.error(LoginMessage.ACCOUNT_ERROR);
    }

    @PutMapping("/resetPassword")
    public Result<String> resetPassword(@RequestBody PasswordDTO passwordDTO) {
        log.info("修改密码：{}", passwordDTO);
        Boolean b = empService.resetPassword(passwordDTO);
        return b?Result.success():Result.error("密码错误");
    }
}
