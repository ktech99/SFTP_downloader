package com.mmt.sem.SEM_WORKFLOW_APP;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.lang.String;

import com.jcraft.jsch.JSch;

@Configuration
public class SEMConfigs {


    @Bean
    public JSch jsch(){
        return new JSch();
    }
        
    @Bean
    public Download download(){
    	return new Download("/","demo","password");
    }
    
  
}

