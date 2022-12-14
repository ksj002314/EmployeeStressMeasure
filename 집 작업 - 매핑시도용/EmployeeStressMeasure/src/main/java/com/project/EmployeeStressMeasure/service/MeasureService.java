package com.project.EmployeeStressMeasure.service;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.project.EmployeeStressMeasure.controller.MeasureController;
import com.project.EmployeeStressMeasure.domain.Employees;
import com.project.EmployeeStressMeasure.domain.Measure;
import com.project.EmployeeStressMeasure.domain.Member;
import com.project.EmployeeStressMeasure.model.measure.MeasureCreationRequest;
import com.project.EmployeeStressMeasure.repository.EmployeesRepository;
import com.project.EmployeeStressMeasure.repository.MeasureRepository;
import com.project.EmployeeStressMeasure.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MeasureService {
	private final EmployeesRepository employeesRepository;
	private final MeasureRepository measureRepository;
	
	private final MemberRepository memberRepository;
	
	public List<Measure> readMeasures() {
		return measureRepository.findAll();
	}

    public Measure readMeasure(Long id) {
        Optional<Measure> measure = measureRepository.findById(id);
        if (measure.isPresent()) {
            return measure.get();
        }
        throw new EntityNotFoundException("Cant find any measure under given id");
    }
    
    // ?????? ??????
  	public Page<Measure> boardList(Pageable pageable) {
  	    int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
  	    pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
  	    return measureRepository.findAll(pageable);
  	}
  	
  	/* ?????? ??????(??????)
  	public Page<Measure> boardSearchList(String searchKeyword,  Pageable pageable) {
  	    int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
  	    pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
  	    return measureRepository.findByEmployees_EmpNoContaining(searchKeyword, pageable);
  	}
  	*/
  	
  	//?????? ??????(?????? or ?????????)
  	public Page<Measure> boardSearchList(String searchKeyword, Pageable pageable) {
  	    int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
  	    pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
  	    return measureRepository.findByEmployees_EmpNoContainingOrNameContaining(searchKeyword, pageable);
  	}
  	
  	
  	// ?????? ?????? ??????
  	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;
  	

	public void init() {
		try {
			Files.createDirectories(Paths.get(uploadPath));
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload folder!");
		}
	}
	
  	
  	
    public Measure createMeasure (MeasureCreationRequest request, MultipartFile file, String username) {
    	Member member1 = memberRepository.findByUserid(username);
    	
    	Optional<Employees> employees = employeesRepository.findByEmpNo(request.getEmpNo());	// ??????????????? EmpNo ?????? - request??? ????????? eMpNo??? ?????????
        
    	if (!employees.isPresent()) {
            throw new EntityNotFoundException("employees Not Found");
        }
        
    	try {
			if (file.isEmpty()) {
				throw new Exception("ERROR : File is empty.");
			}
			Path root = Paths.get(uploadPath);
			if (!Files.exists(root)) {
				init();
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, root.resolve(file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
    	
        
        Measure measureToCreate = new Measure();	// ?????? ??????
        
        measureToCreate.setMember(member1);
        
        measureToCreate.setFilename(file.getOriginalFilename());
        measureToCreate.setSize(file.getSize());
        measureToCreate.setUrl(MvcUriComponentsBuilder.fromMethodName(MeasureController.class,
				"serveFile", file.getOriginalFilename()).build().toString());
        
        BeanUtils.copyProperties(request, measureToCreate);	
        measureToCreate.setEmployees(employees.get());	// ????????? Measure??? ?????????????????? Employees???  ????????? findBy??? ?????? EmpNo????????? ?????????. 
        return measureRepository.save(measureToCreate);
    }
  	
  
    // ????????? ?????????
    public Stream<Path> loadAll() {
		try {
				Path root = Paths.get(uploadPath);
				return Files.walk(root, 1)
	              .filter(path -> !path.equals(root));
	      }
	      catch (IOException e) {
	          throw new RuntimeException("Failed to read stored files", e);
	      }
    }
    
    // ????????? ?????????
    public Path load(String filename) {
    	return Paths.get(uploadPath).resolve(filename);
    }
    
    // ????????? ?????????
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }
    
  	
   
    public Measure updateMeasure(Long id, MeasureCreationRequest request) {
    	Optional<Employees> employees = employeesRepository.findByEmpNo(request.getEmpNo());
        if (!employees.isPresent()) {
            throw new EntityNotFoundException("employees Not Found");
        }
        Optional<Measure> optionalMeasure = measureRepository.findById(id);
        if (!optionalMeasure.isPresent()) {
            throw new EntityNotFoundException("measure Not Found");
        }
        Measure measure = optionalMeasure.get();
        //measure.setCreateDate(request.getCreateDate());
        measure.setStatus(request.getStatus());	
        measure.setEmployees(employees.get());
        return measureRepository.save(measure);
    }
    
    public void deleteMeasure(Long id) {
    	measureRepository.deleteById(id);
    }
    

}
