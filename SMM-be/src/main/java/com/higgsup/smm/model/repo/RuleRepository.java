package com.higgsup.smm.model.repo;

import com.higgsup.smm.model.entity.Page;
import com.higgsup.smm.model.entity.Role;
import com.higgsup.smm.model.entity.Rule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DangThanhLinh on 09/01/2017.
 */
@Repository
public interface RuleRepository extends CrudRepository<Rule, Long> {
    Rule findByRuleId(Long ruleId);

    Rule findByRuleWords(String words);

    Rule findByRuleWordsAndPage(String words, String pageId);

    List<Rule> findByPage(Page pageId);
}