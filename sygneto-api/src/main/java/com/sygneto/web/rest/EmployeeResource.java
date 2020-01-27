package com.sygneto.web.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.config.Constants;
import com.sygneto.config.SecurityConfiguration;
import com.sygneto.domain.Address;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Employees;
import com.sygneto.domain.ErrorCode;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemPriceHistory;
import com.sygneto.domain.Media;
import com.sygneto.domain.Party;
import com.sygneto.domain.Product;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.domain.User;
import com.sygneto.repository.AddressRepository;
import com.sygneto.repository.EmployeeRepository;
import com.sygneto.repository.UserRepository;
import com.sygneto.service.EmployeeService;
import com.sygneto.service.ItemInwardService;
import com.sygneto.service.ItemOutwardService;
import com.sygneto.service.ItemService;
import com.sygneto.service.PartyService;
import com.sygneto.service.ProductService;
import com.sygneto.service.PurchaseRequestService;
import com.sygneto.service.UserService;
import com.sygneto.web.rest.errors.BadRequestAlertException;
import com.sygneto.web.rest.errors.EmailAlreadyUsedException;
import com.sygneto.web.rest.errors.LoginAlreadyUsedException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import springfox.documentation.swagger.web.ApiKeyVehicle;

/**
 * REST controller for managing {@link com.sygneto.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

	private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PartyService partyService;
	
	
	@Autowired
	ProductService productService;
	
	private static final String ENTITY_NAME = "employees";

	
	@Autowired
	private UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AddressRepository addressRepository;

	@Autowired
	ItemService itemService;
	
	@Autowired 
	ItemInwardService itemInwardService;
	
	@Autowired
	ItemOutwardService itemOutwardService;
	
	@Autowired
	PurchaseRequestService purchaseRequestService;
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final EmployeeService employeeService;

	public EmployeeResource(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Autowired
	CustomResponse customeResponce;
	/**
	 * {@code POST  /employees} : Create a new employee.
	 *
	 * @param employee
	 *            the employee to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new employee, or with status {@code 400 (Bad Request)} if
	 *         the employee has already an ID.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
    @ApiOperation(value="Create New Employee")
	@PostMapping("/employee")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createEmployee(@RequestBody Employees employee) throws URISyntaxException {
		log.debug("REST request to save Employee : {}", employee);
	
		if (employee.getEmployeeId() != null) {
			throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
		}
	
		Employees empObj = employeeRepository.findByEmail(employee.getEmail());
		if (empObj != null) {
			throw new EmailAlreadyUsedException();
		}
		
		if (employee.isLoginAllowed()==true) {
		    if (userRepository.findOneByLogin(employee.getUserName().toLowerCase()).isPresent()) {
		           throw new LoginAlreadyUsedException();
		        }}
		
		List<Address> address = new ArrayList<Address>();
		Employees employeeObj = new Employees();
		employeeObj.setUserId(employee.getUserId());
		employeeObj.setUserName(employee.getUserName());
		employeeObj.setEmail(employee.getEmail());
		employeeObj.setStatus(employee.getStatus());
		employeeObj.setAadharNumber(employee.getAadharNumber());
		employeeObj.setAuthorities(employee.getAuthorities());
		employeeObj.setBloodGroup(employee.getBloodGroup());
		employeeObj.setDateOfBirth(employee.getDateOfBirth());
		employeeObj.setDateOfJoining(employee.getDateOfJoining());
		employeeObj.setMobileNumber(employee.getMobileNumber());
		employeeObj.setDateOfRelease(employee.getDateOfRelease());
		employeeObj.setDepartment(employee.getDepartment());
		employeeObj.setDesignation(employee.getDesignation());
		employeeObj.setDrivingLicenseNumber(employee.getDrivingLicenseNumber());
		employeeObj.setDrivingLicenseValidTill(employee.getDrivingLicenseValidTill());
		employeeObj.setEmergencyContactNumber(employee.getEmergencyContactNumber());
		employeeObj.setEmployeeNumber(employee.getEmployeeNumber());
		employeeObj.setImageUrl(employee.getImageUrl());
	   employeeObj.setLoginAllowed(employee.isLoginAllowed());
	   employeeObj.setFirstName(employee.getFirstName());
	   employeeObj.setMiddleName(employee.getMiddleName());
	   employeeObj.setLastName(employee.getLastName());
	   employeeObj.setPanNumber(employee.getPanNumber());
	   employeeObj.setPassportNumber(employee.getPassportNumber());
	   employeeObj.setPassportValidityTill(employee.getPassportValidityTill());
	   employeeObj.setReportTo(employee.getReportTo());
	   employeeObj.setGender(employee.getGender());
	  
		
	    for (Address addressObj: employee.getAddress()) {
	    	Address addressResult = new Address();
	    	addressResult.setArea(addressObj.getArea());
	    	addressResult.setCityName(addressObj.getCityName());
	    	addressResult.setAddressId(addressObj.getAddressId());
	    	addressResult.setAddressLine1(addressObj.getAddressLine1());
	    	addressResult.setAddressLine2(addressObj.getAddressLine2());
	    	addressResult.setAddressType(addressObj.getAddressType());
	    	addressResult.setCountry(addressObj.getCountry());
	    	addressResult.setPinCode(addressObj.getPinCode());
	    	addressResult.setState(addressObj.getState());
	    	addressResult.setFlat(addressObj.getFlat());
	    	addressResult.setLandMark(addressObj.getLandMark());
	    	addressResult.setEmployees(employeeObj);
	    	addressResult.setContact(null);
	    
	    	address.add(addressResult);
		}
	    employeeObj.setAddress(address);
	    Employees result = employeeService.save(employeeObj);
		if (employee.isLoginAllowed()==true) {
		   
			User newUser = userService.createUserFromEmp(result);
			Long userId = newUser.getId();
			result.setUserId(userId);
			System.out.println("*)*)))))))))"+result.getUserId());

		}
		Employees result1 = employeeService.save(result);
		return customeResponce.success("Employee Added Successfully", result1);
	}

	/**
	 * {@code PUT  /employees} : Updates an existing employee.
	 *
	 * @param employee
	 *            the employee to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated employee, or with status {@code 400 (Bad Request)} if the
	 *         employee is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the employee couldn't be
	 *         updated.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
    @ApiOperation(value="Update Employee Details")
	@PutMapping("/employee")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object updateEmployee(@RequestBody Employees employee) throws URISyntaxException {
		log.debug("REST request to update Employee : {}", employee);
		
		if (employee.getEmployeeId() == null) 
		{
			throw new BadRequestAlertException("A employee can have an ID", ENTITY_NAME, "");
		}
		
		 Employees emp = employeeService.findByEmployeeId(employee.getEmployeeId());
		 
	 if(employee.isLoginAllowed()) 
	 {
		 if(!emp.isLoginAllowed())
		 {
			 if (userRepository.findOneByLogin(employee.getUserName().toLowerCase()).isPresent()) {
		           throw new LoginAlreadyUsedException();
		        }
			    User newUser = userService.createUserFromEmp(emp);
				Long userId = newUser.getId();
				employee.setUserId(userId);
				System.out.println("*)*)))))))))"+emp.getUserId());
		 }
		 }
		
	    if (employee.isLoginAllowed())
		{
			if(employee.getUserId() == null) 
			{
				throw new BadRequestAlertException("A user id can not be null", ENTITY_NAME, "");
			}
			else 
			{
				userService.updateUserFromEmp(employee);
			}
		}
		if(employee.getUserId() !=null) 
		{
			if (!employee.isLoginAllowed())
			{
			if(employee.getUserId() == null) 
			{
				throw new BadRequestAlertException("A user id can not be null", ENTITY_NAME, "");
			}else 
			{
				Optional<User> user=userRepository.findById(employee.getUserId());
				user.get().setActivated(false);
				userRepository.save(user.get());
			}
		}
			}
		if(employee.getUserId() !=null) {
			if (employee.isLoginAllowed()) {
				if(employee.getUserId() == null) {
					throw new BadRequestAlertException("A user id can not be null", ENTITY_NAME, "");
				}else {
					Optional<User> user=userRepository.findById(employee.getUserId());
					user.get().setActivated(true);
					userRepository.save(user.get());
				}
			}
		}
		List<Address> address = new ArrayList<Address>();
		Employees employeeObj = new Employees();
		employeeObj.setEmployeeId(employee.getEmployeeId());
		employeeObj.setUserId(employee.getUserId());
		employeeObj.setUserName(employee.getUserName());
		employeeObj.setEmail(employee.getEmail());
		employeeObj.setStatus(employee.getStatus());
		employeeObj.setMobileNumber(employee.getMobileNumber());
		employeeObj.setAadharNumber(employee.getAadharNumber());
		employeeObj.setAuthorities(employee.getAuthorities());
		employeeObj.setBloodGroup(employee.getBloodGroup());
		employeeObj.setDateOfBirth(employee.getDateOfBirth());
		employeeObj.setDateOfJoining(employee.getDateOfJoining());
		employeeObj.setDateOfRelease(employee.getDateOfRelease());
		employeeObj.setDepartment(employee.getDepartment());
		employeeObj.setDesignation(employee.getDesignation());
		employeeObj.setDrivingLicenseNumber(employee.getDrivingLicenseNumber());
		employeeObj.setDrivingLicenseValidTill(employee.getDrivingLicenseValidTill());
		employeeObj.setEmergencyContactNumber(employee.getEmergencyContactNumber());
		employeeObj.setEmployeeNumber(employee.getEmployeeNumber());
		employeeObj.setImageUrl(employee.getImageUrl());
	   employeeObj.setLoginAllowed(employee.isLoginAllowed());
	   employeeObj.setFirstName(employee.getFirstName());
	   employeeObj.setMiddleName(employee.getMiddleName());
	   employeeObj.setLastName(employee.getLastName());
	   employeeObj.setPanNumber(employee.getPanNumber());
	   employeeObj.setPassportNumber(employee.getPassportNumber());
	   employeeObj.setPassportValidityTill(employee.getPassportValidityTill());
	   employeeObj.setReportTo(employee.getReportTo());
	   employeeObj.setGender(employee.getGender());
	   employeeObj.setImageUrl(employee.getImageUrl());
	   employeeObj.setImagePath(employee.getImagePath());
	   employeeObj.setThumbNailUrl(employee.getThumbNailUrl());
	   employeeObj.setThumbNailFilePath(employee.getThumbNailFilePath());
	   
	    for (Address addressObj: employee.getAddress()) {
	    	Address addressResult = new Address();
	    	addressResult.setAddressId(addressObj.getAddressId());
	    	addressResult.setArea(addressObj.getArea());
	    	addressResult.setCityName(addressObj.getCityName());
	    	addressResult.setFlat(addressObj.getFlat());
	    	addressResult.setLandMark(addressObj.getLandMark());
	    	addressResult.setAddressLine1(addressObj.getAddressLine1());
	    	addressResult.setAddressLine2(addressObj.getAddressLine2());
	    	addressResult.setAddressType(addressObj.getAddressType());
	    	addressResult.setCountry(addressObj.getCountry());
	    	addressResult.setPinCode(addressObj.getPinCode());
	    	addressResult.setState(addressObj.getState());
	    	addressResult.setEmployees(employeeObj);
	    	addressResult.setContact(null);
	    	
	    	address.add(addressResult);
		}
	    employeeObj.setAddress(address);
		Employees result = employeeService.save(employeeObj);
		return customeResponce.success("Employee Updated Successfully", result);
	}

	/**
	 * {@code GET  /employees} : get all the employees.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of employees in body.
	 */
    @ApiOperation(value="Get all Employee Details")
	@GetMapping("/employee")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllEmployees() {
		log.debug("REST request to get all Employees");
		
        //List<Employees> result = employeeService.findAll();
		List<Employees>result=employeeService.findAllOrderByDateOfJoiningDesc();
		
		for (Employees employee : result) {
			List<Address> result1 = addressRepository.findByEmployeesEmployeeId(employee.getEmployeeId());
			employee.setAddress(result1);
		}
		if (result != null&&!result.isEmpty()) {
			return customeResponce.success("Employee found", result);
		} else {
			return customeResponce.failure(404, "Employee Not found");
		}
	}
    @ApiOperation(value="Used for Dashboard Count ")
	@GetMapping("/DashboardCount")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllEmployeesCount() {
		log.debug("REST request to get all Employees");
		
       
		Long emp=employeeService.findByCount();
	    Long party = partyService.countByPartyId();
	    Long item=itemService.findCount();
		Long inward = itemInwardService.findByCount();
		Long outward = itemOutwardService.findCount();
        Long purchaseReq=purchaseRequestService.findCount();
        Long supplier=partyService.countByIsSupplier();
        Long customer=partyService.countByIsCustomer();
        Long PRODUCT=productService.findCount();

        

	    Map< String,Long> hm =  new HashMap< String,Long>(); 
	    hm.put("EmployeeCount",emp);
	    hm.put("PartyCount",party);
	    hm.put("ItemCount",item);
	    hm.put("InwardCount",inward);
	    hm.put("OutwardCount",outward);
	    hm.put("PurchaseRequestCount",purchaseReq);
	    hm.put("Totalsupplier", supplier);
	    hm.put("TotalCustomer", customer);
	    hm.put("TotalProduct", PRODUCT);

		return hm;
	
	}
/*	@GetMapping("emp")
	public Object getAllEmpSort() {
		log.debug("REST request to get all Employees");
		
        //List<Employees> result = employeeService.findAll();
	
		List<Employees>result=employeeService.findAllOrderByFirstNameAsc();
		for (Employees employee : result) {
			List<Address> result1 = addressRepository.findByEmployeesEmployeeId(employee.getEmployeeId());
			employee.setAddress(result1);
		}
		if (result != null&&!result.isEmpty()) {
			return customeResponce.success("Employee found", result);
		} else {
			return customeResponce.failure(404, "Employee Not found");
		}
	}
*/
   
	/**
	 * {@code GET  /employees/:id} : get the "id" employee.
	 *
	 * @param id
	 *            the id of the employee to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the employee, or with status {@code 404 (Not Found)}.
	 */
    @ApiOperation(value="Get Employee Details as per Employee Id")
	@GetMapping("/employee/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER})
	public Object getEmployee(@PathVariable Long id) {
		log.debug("REST request to get Employee : {}", id);
		
		Optional<Employees> result = employeeService.findOne(id);
		
		List<Address> result1 = addressRepository.findByEmployeesEmployeeId(result.get().getEmployeeId());
		System.out.println("@@@@@@@@@@@"+result1);
		result.get().setAddress(result1);
		System.out.println("!!!!!!!!!!!!!"+result);
		if (result.isPresent()) {
			return customeResponce.success("Employee found", result);
		} else {
			return customeResponce.failure(404, "Employee Not found");
		}
	}

  
	@PostMapping("/changeStatus/{id}/{status}/{keyword}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object status(@PathVariable Long id,@PathVariable("status") String status, @PathVariable("keyword") String keyword) {
		log.debug("REST request for change the status : {}", id);
		
			if(keyword == "employee" || keyword.equals("employee")) {
				Optional<Employees> result = employeeService.findOne(id);
				if(result==null)
				{
					return customeResponce.failure(404, "Data Not found");
				}
				result.get().setStatus(status);
				Employees finalResult = employeeService.save(result.get());
				if(finalResult==null)
				{
					return customeResponce.failure(404, "Data Not found");
				}
				else
				{
					return customeResponce.success("Data Updated Successfully", finalResult);
				}
			}else if (keyword == "supplier" || keyword.equals("supplier") || keyword == "customer" || keyword.equals("customer")) {
				Optional<Party> result = partyService.findOneByPartyId(id);
				if(result==null)
				{
					return customeResponce.failure(404, "Data Not found");
				}
				result.get().setPartyStatus(status);
				Party finalResult = partyService.save(result.get());
				if(finalResult==null)
				{
					return customeResponce.failure(404, "Data Not found");
				}
				else
				{
					return customeResponce.success("Data Updated Successfully", finalResult);
				}
			}else {
				return null;
			}
			
		}
	
	@ApiOperation(value="Get all Active Employee Details")
	@GetMapping("/getActiveEmp")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getActiveEmp() {
		log.debug("REST request to get getActiveEmp : {}");
	
		String status="active";
		List<Employees> result= employeeRepository.findByStatus(status);
		if(result ==null ||result.isEmpty()) {
			return customeResponce.failure(404, "Employee data Not found");
		}else {
			return customeResponce.success("Employee found", result);
		}
	}
	

	/**
	 * {@code DELETE  /employees/:id} : delete the "id" employee.
	 *
	 * @param id
	 *            the id of the employee to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@ApiOperation(value="Delete Employee Details as per Employee Id")
	@DeleteMapping("/employee/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object deleteEmployee(@PathVariable Long id) {
		log.debug("REST request to delete Employee : {}", id);
		Optional<Employees> result = employeeService.findOne(id);

		if (result.isPresent()) {
				if(result.get().getUserId() !=null ) {
				userService.deleteUser(result.get().getUserId());
				}
				if(result.get().getAddress() !=null) {
					List<Address> result1 = addressRepository.findByEmployeesEmployeeId(result.get().getEmployeeId());
					for (Address address : result1) {
						addressRepository.deleteById(address.getAddressId());
					}
				}
				employeeService.delete(id);
				return customeResponce.success("Employee Deleted Successfully", null);
		
		} else {
			return customeResponce.failure(404, "Employee Not found");
		}

	}
	  @ApiOperation(value="Upload Employee image for Profile")
	@PostMapping("/uploadImageForEmployee/{employeeId}")
	public Object uploadImage(@PathVariable Long employeeId,@RequestParam(value = "file", required = false) MultipartFile[] files,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		SygnetoResponse sygnetoResponse=new SygnetoResponse();
		Optional<Employees> result = employeeService.findOne(employeeId);
		
		List<Address> result2 = addressRepository.findByEmployeesEmployeeId(result.get().getEmployeeId());
		result.get().setAddress(result2);
		
		File f3 = new File(Constants.ORIGINAL_FILE_PATH);
		if (!f3.isDirectory()) {
			f3.mkdirs();
		}
		String filePath = f3.toString();
		
		File f4 = new File(Constants.ORIGINAL_FILE_PATH);

		if (!f4.isDirectory()) {
			f4.mkdirs();
		}
		String newFilePath = f4.toString();
		
		String onlineUrl = "";
		byte[] bytes = null;
		if(files !=null ) {
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
				SygnetoResponse response=new SygnetoResponse();
				
				response.setStatusCode(404);
				response.setStatus("Please select a file to upload");

				ErrorCode error = new ErrorCode();
				error.setCode(404);
				response.setStatusCode(error.getCode());
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

			try {
				String fileNameForExt = file.getOriginalFilename();
				String extension = FilenameUtils.getExtension(fileNameForExt);

				if (extension.isEmpty()) {

					sygnetoResponse.setStatusCode(404);
					sygnetoResponse.setStatus("Please select a correct file to upload");
					return sygnetoResponse;
				}
				Calendar cal = new GregorianCalendar();
				String uniqueName = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH)
						+ cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.HOUR) + cal.get(Calendar.MINUTE)
						+ cal.get(Calendar.SECOND) + cal.get(Calendar.MILLISECOND) + "14589625587456691"
						+ cal.get(Calendar.MINUTE) + cal.get(Calendar.MILLISECOND) + cal.get(Calendar.MONTH)
						+ cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.HOUR) + cal.get(Calendar.SECOND)
						+ cal.get(Calendar.YEAR);

				String unique = new Date().getTime() + "" + new Random(99);
				// String extension=

				/// Files.write(path, bytes);
				File newFile = new File(filePath + File.separator + uniqueName + "." + extension);
				file.transferTo(newFile);
				
				redirectAttributes.addFlashAttribute("message",
						"You successfully uploaded '" + file.getOriginalFilename() + "'");
				
				if ("jpg".equalsIgnoreCase(extension) || "png".equalsIgnoreCase(extension)) {

					BufferedImage inputImage = ImageIO.read(newFile);

					Double ratio = (double) inputImage.getWidth() / (double) inputImage.getHeight();

					boolean flag = ratio > 1;
					int width = (int) (flag ? 100 : 100 * ratio);
					int height = (int) (flag ? 100 / ratio : 100);

					BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

					Graphics2D g2d = outputImage.createGraphics();
					g2d.drawImage(inputImage, 0, 0, width, height, null);
					g2d.dispose();

					// writes to output file
					ImageIO.write(outputImage, extension,
							new File(newFilePath + File.separator + unique + "." + extension));

					onlineUrl = Constants.DATA + unique + "." + extension;
					//result.setThumpNailUrl(onlineUrl);
					result.get().setThumbNailFilePath(Constants.SERVER_URL + onlineUrl);
					result.get().setThumbNailUrl(onlineUrl);
					//result.setThumpNailPath(Constants.SERVER_URL + onlineUrl);
				} else {
					onlineUrl = Constants.DATA + uniqueName + "." + extension;
					result.get().setThumbNailFilePath(Constants.SERVER_URL + onlineUrl);
					result.get().setThumbNailUrl(onlineUrl);
					// item.setImageUrl(onlineUrl);

				}
				onlineUrl = Constants.DATA + uniqueName + "." + extension;
				result.get().setImagePath(Constants.SERVER_URL + onlineUrl);
				result.get().setImageUrl(onlineUrl);
			
			} catch (Exception e) {
			}
		}}else {
			return customeResponce.failure(404, "file not found");
		}
		Employees result1 = employeeService.save(result.get());
		sygnetoResponse.setStatus("200");
		sygnetoResponse.setMessage("successfully uploaded image");
		sygnetoResponse.setData(result1);

		return sygnetoResponse;
	}
}
