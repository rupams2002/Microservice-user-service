package com.dailycodebuffer.user.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dailycodebuffer.user.VO.Department;
import com.dailycodebuffer.user.VO.ResponseTemplateVO;
import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	public User saveUser(User user) {
		log.info("Inside saveUser method of UserService");
		return userRepository.save(user);
	}

//	public User findUserById(Long userId) {
//		log.info("Inside findUserById method of UserService");
//		//User user = userRepository.findByUserId(userId);
//		return userRepository.findByUserId(userId) ;
//	}

	public ResponseTemplateVO getUserWithDepartment(Long userId) {
		log.info("Inside getUserWithDepartment method of UserService");
		ResponseTemplateVO vo = new ResponseTemplateVO();
		User user = userRepository.findByUserId(userId);
		
		URI uri = null;
		try {
			//uri = new URI("http://localhost:9001/departments/" + user.getDepartmentId());
			uri = new URI("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId());
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		Department department = restTemplate.getForObject(uri, Department.class);		

		vo.setUser(user);
		vo.setDepartment(department);
		
		return vo;
	}

}
