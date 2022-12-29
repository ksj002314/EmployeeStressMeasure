package com.project.EmployeeStressMeasure.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.project.EmployeeStressMeasure.domain.Measure;
import com.project.EmployeeStressMeasure.model.measure.MeasureCreationRequest;
import com.project.EmployeeStressMeasure.service.EmployeesService;
import com.project.EmployeeStressMeasure.service.MeasureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController 
@RequiredArgsConstructor
@RequestMapping(value = "/api/measure")
public class MeasureController {
	
	private final MeasureService measureService;
	
	//CRUD
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Measure> createMeasure (@RequestPart MeasureCreationRequest request, @RequestPart MultipartFile file,  Authentication authentication) {
		String username = authentication.getName();
		return ResponseEntity.ok(measureService.createMeasure(request, file, username));
	}
	
	// imageFile 같이 전송 
//	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//	public void saveMeasure(@RequestPart MeasureCreationRequest request, @RequestPart MultipartFile imgFile) {
//		log.info("사번 : {}, 상태 : {}, 이미지 : {}",request.getEmpNo(), request.getStatus(), imgFile);
//	}
	
	// 파일 전송
//	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//	public String saveMeasure(@ModelAttribute Measure measure) {
//		measureService.createMeasure(measure);
//		return "measure/success";
//	}
	
	// 다운로드 경로
	@GetMapping(value="download")
	public ResponseEntity<Resource> serveFile(@RequestParam(value="filename") String filename) {

    	Resource file = measureService.loadAsResource(filename);
    	return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	
	// 이미지 전체 리스트 
	@GetMapping("/fileList")
    public ResponseEntity<List<Measure>> getListFiles() {
        List<Measure> fileInfos = measureService.loadAll()
          .map(path ->{
        	  Measure data = new Measure();
              String filename = path.getFileName().toString();
              data.setFilename(filename);
              data.setUrl(MvcUriComponentsBuilder.fromMethodName(MeasureController.class,
                        "serveFile", filename).build().toString());
              try {
                data.setSize(Files.size(path));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
              return data;
          })
          .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
	
	
	@GetMapping
	public ResponseEntity<Page<Measure>> measureList (Pageable pageable, String searchKeyword) {				
		if(searchKeyword == null) {
			return ResponseEntity.ok(measureService.boardList(pageable));
		} else {
			return ResponseEntity.ok(measureService.boardSearchList(searchKeyword, pageable));
		}
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Measure> readMeasure (@PathVariable Long id) {
	    return ResponseEntity.ok(measureService.readMeasure(id));
	}
	
	@PutMapping("/{id}")
	 public ResponseEntity<Measure> updateMeasure (@PathVariable("id") Long id, @RequestBody MeasureCreationRequest request) {
        return ResponseEntity.ok(measureService.updateMeasure(id, request));
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMeasure (@PathVariable Long id) {
		measureService.deleteMeasure(id);
        return ResponseEntity.ok().build();
    }
	
	
}

  
	 
