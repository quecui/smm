package com.higgsup.smm.service;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by DangThanhLinh on 13/01/2017.
 */

public class ConvertString {
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }

    public static List<String> splitCommaRuleWord(String ruleWords) {
        List<String> arr = new ArrayList<>();
        String[] str = ruleWords.split("\\s*,\\s*");
        Collections.addAll(arr, str);
        return arr;
    }

    public static void main(String[] args) {
        String ruleWords = "dang,làm what đợi cơm,            cai      ,  gi,thế";
        System.out.println(splitCommaRuleWord(ruleWords));
    }
}
