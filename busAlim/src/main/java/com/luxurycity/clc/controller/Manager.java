package com.luxurycity.clc.controller;

import java.io.*;
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manager")
public class Manager {
//	@Autowired
//	ManagerDao mgDao;
	
	@RequestMapping("/manager.clc")
	public ModelAndView info(ModelAndView mv) {

		String str = "";
		ArrayList<String> line = new ArrayList<String>();
		
		try{
            File file = new File("E:\\web\\spring\\source\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\webapps\\busAlim\\log\\log.log");
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            while((str = bufReader.readLine()) != null) {
            	line.add(str);
            }
            System.out.println(line);
            Collections.reverse(line);
            bufReader.close();
        }catch (FileNotFoundException e) {
        	e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

		mv.addObject("LINE", line);
		mv.setViewName("manager/manager");
		return mv;
	}
}
