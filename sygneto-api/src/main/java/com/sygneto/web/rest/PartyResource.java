package com.sygneto.web.rest;

import com.sygneto.config.Constants;
import com.sygneto.domain.Address;
import com.sygneto.domain.BankDetails;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Employees;
import com.sygneto.domain.ErrorCode;
import com.sygneto.domain.Item;
import com.sygneto.domain.Party;
import com.sygneto.domain.Product;
import com.sygneto.domain.SOPayment;
import com.sygneto.domain.SalesOrder;
import com.sygneto.domain.Sector;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.AddressRepository;
import com.sygneto.repository.BankDetailsRepository;
import com.sygneto.repository.PartyRepository;
import com.sygneto.service.AddressService;
import com.sygneto.service.PartyService;
import com.sygneto.service.impl.PartyServiceImpl;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiOperation;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for managing {@link com.sygneto.domain.Party}.
 */
@RestController
@RequestMapping("/api")
public class PartyResource {

	private final Logger log = LoggerFactory.getLogger(PartyResource.class);

	private static final String ENTITY_NAME = "party";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	AddressService addressService;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	PartyRepository partyRepository;
	
	@Autowired
	BankDetailsRepository bankDetailsRepository;
	
	private final PartyService partyService;
	
	@Autowired
	CustomResponse customResponse;
	public PartyResource(PartyService partyService) {
		this.partyService = partyService;
	}


	/**
	 * {@code POST  /parties} : Create a new party.
	 *
	 * @param party
	 *            the party to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new party, or with status {@code 400 (Bad Request)} if the
	 *         party has already an ID.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	
	@ApiOperation(value="Create New Party/Organization")
	@PostMapping("/party")
	public Object createParty(@RequestBody Party party) throws URISyntaxException {
		log.debug("REST request to save Party : {}", party);
		if (party.getPartyId() != null) {
			throw new BadRequestAlertException("A new party cannot already have an ID", ENTITY_NAME, "idexists");
		}
		if (party.getPartyName() == null) {
			throw new BadRequestAlertException("A Party Name can not be null", ENTITY_NAME, "");
		}
		Party result = partyService.save(party);
		
		return customResponse.success("Party created", result);

	}

	/**
	 * {@code PUT  /parties} : Updates an existing party.
	 *
	 * @param party
	 *            the party to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated party, or with status {@code 400 (Bad Request)} if the
	 *         party is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the party couldn't be updated.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@ApiOperation(value="Update existing Party/Organization Details")
	@PutMapping("/party")
	public Object updateParty(@RequestBody Party party) throws URISyntaxException {
		log.debug("REST request to update Party : {}", party);
		if (party.getPartyId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Party result = partyService.save(party);
		return customResponse.success("Party updated", result);
	}

	/**
	 * {@code GET  /parties} : get all the parties.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of parties in body.
	 */
	@ApiOperation(value="Get all Party/Organization Details")
	    @GetMapping("/party")
	    public Object getAllParties() {
		List<Party> result = partyService.findAll();
		for (Party party : result) {
			List<Address> result1 = addressService.findAllByPartyId(party.getPartyId());
			List<BankDetails> BankdetailsList = bankDetailsRepository.findByPartyId(party.getPartyId());
			party.setBankDetails(BankdetailsList);
			party.setAddress(result1);
		}
	
		if (result != null&& !result.isEmpty()) {
			return customResponse.success("Party found", result);

		} else {
			return customResponse.failure(404, "Party not found");

		}
	}
	  

	/**
	 * {@code GET  /parties/:id} : get the "id" party.
	 *
	 * @param id
	 *            the id of the party to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the party, or with status {@code 404 (Not Found)}.
	 */
	
	
	    @ApiOperation(value="Get Party/Organization Details as per Party/Organization Id")
		@GetMapping("/party/{id}")
		public Object getPartyByName(@PathVariable Long id) {
			log.debug("REST request to get Party : {}", id);
			Optional<Party> result = partyService.findOne(id);
			List<Address> result1 = addressService.findAllByPartyId(result.get().getPartyId());
			List<BankDetails> BankdetailsList = bankDetailsRepository.findByPartyId(result.get().getPartyId());
			result.get().setBankDetails(BankdetailsList);
			
			System.out.println("@@@@@@@@@@@@@@@@@"+result1);
			result.get().setAddress(result1);
			if (result != null && result.isPresent()) {
				return customResponse.success("Party found", result);

			} else {
				return customResponse.failure(404, "Party not found");

			}
		}
		
	
	/**
	 * {@code DELETE  /parties/:id} : delete the "id" party.
	 *
	 * @param id
	 *            the id of the party to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	
	@ApiOperation(value="Get all party Details who is customer")
	@GetMapping("/getpartyByCustomer")
	public Object getParty() {
		log.debug("REST request to get getpartyBySupplier : {}");
		String isCus="yes";
		List<Party> result= partyRepository.findByIsCustomer(isCus);
		for (Party party : result) {
			List<Address> result1 = addressService.findAllByPartyId(party.getPartyId());
			List<BankDetails> BankdetailsList = bankDetailsRepository.findByPartyId(party.getPartyId());
			party.setBankDetails(BankdetailsList);
			party.setAddress(result1);
		}
		
		if(result ==null ||result.isEmpty()) {
			return customResponse.failure(404, "customer data Not found");
		}else {
			return customResponse.success("customer found", result);
		}
	}
	
	@ApiOperation(value="Get all party Details who is supplier")
	@GetMapping("/getpartyBySupplier")
	public Object getParty1() {
		log.debug("REST request to get getpartyByCustomer : {}");
		String isSup="yes";
		List<Party> result= partyRepository.findByIsSupplier(isSup);
		for (Party party : result) {
			List<Address> result1 = addressService.findAllByPartyId(party.getPartyId());
			List<BankDetails> BankdetailsList = bankDetailsRepository.findByPartyId(party.getPartyId());
			party.setBankDetails(BankdetailsList);
			party.setAddress(result1);
		}
	
		if(result ==null ||result.isEmpty()) {
			return customResponse.failure(404, "supplier data Not found");
		}else {
			return customResponse.success("supplier found", result);
		}
	}
	@ApiOperation(value="Get all party Details who is Organization")
	@GetMapping("/getpartyByOrganization")
	public Object getParty2() {
		log.debug("REST request to get getpartyByOrganization : {}");
		String isOrg="yes";
		List<Party> result= partyRepository.findByIsOrganisation(isOrg);
		for (Party party : result) {
			List<Address> result1 = addressService.findAllByPartyId(party.getPartyId());
			List<BankDetails> BankdetailsList = bankDetailsRepository.findByPartyId(party.getPartyId());
			party.setBankDetails(BankdetailsList);
			party.setAddress(result1);
		}
		
		if(result ==null ||result.isEmpty()) {
			return customResponse.failure(404, "supplier data Not found");
		}else {
			return customResponse.success("supplier found", result);
		}
	}
	
	
/*	@GetMapping("/getActiveparties")
	public Object getActiveEmp() {
		log.debug("REST request to get getActiveEmp : {}");
		String status="active";
		List<Party> result= partyRepository.findByPartyStatus(status);
		if(result ==null ||result.isEmpty()) {
			return customResponse.failure(404, "Employee data Not found");
		}else {
			return customResponse.success("Employee found", result);
		}
	}*/
	
	@ApiOperation(value="Upload logo of organization/Party")
	@PostMapping("/uploadImageForLogo")
	public Object uploadImageForLogo(@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam(value ="partyId") Long partyId,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		SygnetoResponse sygnetoResponse=new SygnetoResponse();
		Optional<Party> result = partyService.findOneByPartyId(partyId);
		
		List<Address> result2 = addressService.findAllByPartyId(result.get().getPartyId());
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
					result.get().setThumpNailUrl(onlineUrl);
					//result.setThumpNailPath(Constants.SERVER_URL + onlineUrl);
				} else {
					onlineUrl = Constants.DATA + uniqueName + "." + extension;
					result.get().setThumbNailFilePath(Constants.SERVER_URL + onlineUrl);
					result.get().setThumpNailUrl(onlineUrl);
					// item.setImageUrl(onlineUrl);

				}
				onlineUrl = Constants.DATA + uniqueName + "." + extension;
				result.get().setImagePath(Constants.SERVER_URL + onlineUrl);
				result.get().setImageUrl(onlineUrl);
			
			} catch (Exception e) {
			}
		}}
		Party result1 = partyService.save(result.get());
		sygnetoResponse.setStatus("200");
		sygnetoResponse.setMessage("successfully uploaded image");
		sygnetoResponse.setData(result1);

		return sygnetoResponse;
	}
	
	
	@ApiOperation(value="Delete Party/Organization Details")
	@DeleteMapping("/party/{partyId}")
	public Object deleteParty(@PathVariable Long partyId) {
		log.debug("REST request to delete Party : {}", partyId);
		Optional<Party> result = partyService.findOneByPartyId(partyId);
		if (result != null && result.isPresent()) {
			try {
				if(result.get().getAddress() !=null) {
					List<Address> result1 = addressService.findAllByPartyId(result.get().getPartyId());
					for (Address address : result1) {
						addressRepository.deleteById(address.getAddressId());
					}
				}
				partyService.delete(partyId);
				return customResponse.success("Party deleted", null);
			} catch (Exception e) {
				return customResponse.failure(400, "Party not deleted");
			}

		} else {
			return customResponse.failure(404, "Party not found");
		}
	}
	
}
		
	/*}
	@GetMapping("/getuser")
	public Object getActiveEmp() {
		log.debug("REST request to get getActiveEmp : {}");
		String status="active";
		List<Party> result= partyRepository.findByPartyStatus(status);
		if(result ==null ||result.isEmpty()) {
			return customResponse.failure(404, "Employee data Not found");
		}else {
			return customResponse.success("Employee found", result);
		}*/
	
	
	

