package com.smartanalyzer.rules;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
public class RuleLoader {
    private List<Rule> loadedRules;

    public RuleLoader() {
        this.loadedRules = new ArrayList<>();
    }


    public List<Rule> loadBuiltInRules() {
        loadedRules.clear();
        ServiceLoader<Rule> serviceLoader = ServiceLoader.load(Rule.class);

        for (Rule rule : serviceLoader) {
            loadedRules.add(rule);
            System.out.println("✓ Loaded rule: " + rule.getRuleName());
        }

        return loadedRules;
    }
}