package com.higgsup.smm.service;

import com.higgsup.smm.dto.RuleDTO;
import com.higgsup.smm.model.entity.OAuthUser;
import com.higgsup.smm.model.entity.Page;
import com.higgsup.smm.model.entity.Rule;
import com.higgsup.smm.model.repo.CommentRepository;
import com.higgsup.smm.model.repo.OAuthUserRepository;
import com.higgsup.smm.model.repo.PageRepository;
import com.higgsup.smm.model.repo.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DangThanhLinh on 09/01/2017.
 */
@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private OAuthUserRepository oAuthUserRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private CommentRepository commentRepository;

    public RuleDTO addRule(RuleDTO ruleDTO) {
        Rule rule = new Rule();
        OAuthUser oAuthUser = oAuthUserRepository.findByProviderUserId(ruleDTO.getAssignUserID());
        Page page = pageRepository.findByPageId(ruleDTO.getPageId());

        rule.setApprove(ruleDTO.isApprove());
        rule.setAssignUser(oAuthUser);
        rule.setHide(ruleDTO.isHide());
        rule.setPage(page);
        rule.setRemove(ruleDTO.isRemove());
        rule.setRuleName(ruleDTO.getRuleName());
        rule.setRuleWords(ruleDTO.getRuleWords());

        ruleRepository.save(rule);
        return ruleDTO;
    }

    public RuleDTO editRule(Long ruleId, RuleDTO ruleDTO) {

        Rule rule = ruleRepository.findByRuleId(ruleId);
        OAuthUser oAuthUser = oAuthUserRepository.findByProviderUserId(ruleDTO.getAssignUserID());
        Page page = pageRepository.findByPageId(ruleDTO.getPageId());

        rule.setApprove(ruleDTO.isApprove());
        rule.setAssignUser(oAuthUser);
        rule.setHide(ruleDTO.isHide());
        rule.setPage(page);
        rule.setRemove(ruleDTO.isRemove());
        rule.setRuleName(ruleDTO.getRuleName());
        rule.setRuleWords(ruleDTO.getRuleWords());
        rule = ruleRepository.save(rule);

        return ruleDTO;
    }

    public RuleDTO getRule(Long ruleId) {
        Rule rule = ruleRepository.findByRuleId(ruleId);
        RuleDTO ruleDTO = new RuleDTO();
        ruleDTO.setApprove(rule.isApprove());
        ruleDTO.setAssignUserID(rule.getAssignUser().getProviderUserId());
        ruleDTO.setHide(rule.isHide());
        ruleDTO.setPageId(rule.getPage().getPageId());
        ruleDTO.setRemove(rule.isRemove());
        ruleDTO.setRuleName(rule.getRuleName());
        ruleDTO.setRuleWords(rule.getRuleWords());
        return ruleDTO;
    }

    public void delRule(Long ruleId) {
        Rule rule = ruleRepository.findByRuleId(ruleId);
        ruleRepository.delete(rule);

    }

    public List<RuleDTO> getListRule(String pageId) {
        Page page = pageRepository.findByPageId(pageId);
        List<RuleDTO> listRuleDTO = new ArrayList<>();
        List<Rule> rules = (List<Rule>) ruleRepository.findByPage(page);
        for (Rule rule : rules) {
            RuleDTO ruleDTO = new RuleDTO();
            ruleDTO.setApprove(rule.isApprove());
            ruleDTO.setAssignUserID(rule.getAssignUser().getProviderUserId());
            ruleDTO.setHide(rule.isHide());
            ruleDTO.setPageId(rule.getPage().getPageId());
            ruleDTO.setRemove(rule.isRemove());
            ruleDTO.setRuleName(rule.getRuleName());
            ruleDTO.setRuleWords(rule.getRuleWords());
            listRuleDTO.add(ruleDTO);
        }
        return listRuleDTO;
    }
}
