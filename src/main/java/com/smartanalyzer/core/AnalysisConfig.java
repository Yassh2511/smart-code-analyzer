package com.smartanalyzer.core;

// Analysis Configuration
// This config will tell about which rules we should apply by keeping them
// False or true
public class AnalysisConfig
{
    private boolean enablePerformanceRules=true;  // Check for slow code
    private boolean enableSecurityRules=true;   // Check for security issues
    private boolean enableMaintainabiltyRules=true;   // Check for hard to maintain code

    public boolean isEnablePerformanceRules()
    {
        return enablePerformanceRules;
    }
    public void setEnablePerformanceRules(boolean enable)
    {
        this.enablePerformanceRules=enable;
    }
    public boolean isEnableSecurityRules()
    {
        return enableSecurityRules;
    }
    public void setEnableSecurityRules(boolean enable)
    {
        this.enableSecurityRules=enable;
    }
    public boolean isEnableMaintainabilityRules()
    {
        return enableMaintainabiltyRules;
    }
    public void isEnableMaintainabilityRules(boolean enable)
    {
        this.enableMaintainabiltyRules=enable;
    }
}
