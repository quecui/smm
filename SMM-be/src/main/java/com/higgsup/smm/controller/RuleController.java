package com.higgsup.smm.controller;

import com.higgsup.smm.dto.RuleDTO;
import com.higgsup.smm.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by DangThanhLinh on 09/01/2017.
 */
@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    //add rule
    @PostMapping("/rule")
    public RuleDTO addRule(@RequestBody RuleDTO ruleDTO) {
        return ruleService.addRule(ruleDTO);
    }

    //edit rule
    @PutMapping("/rule/{rule_id}")
    public RuleDTO editRule(@PathVariable("rule_id") Long ruleId, @RequestBody RuleDTO ruleDTO) {
        return ruleService.editRule(ruleId, ruleDTO);
    }

    //get rule
    @GetMapping("/rule/{rule_id}")
    public RuleDTO getRule(@PathVariable("rule_id") Long ruleId) {
        return ruleService.getRule(ruleId);
    }

    //list rule of page
    @GetMapping("/rule/page/{page_id}")
    public List<RuleDTO> getListRule(@PathVariable("page_id") String pageId) {
        return ruleService.getListRule(pageId);
    }

    //delete rule
    @DeleteMapping("/delete/rule/{rule_id}")
    public void delRule(@PathVariable("rule_id") Long ruleId) {
        ruleService.delRule(ruleId);
    }


}

