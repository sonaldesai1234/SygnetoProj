package com.sygneto.web.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sygneto.config.Constants;
import com.sygneto.domain.Address;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Employees;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemCategory;
import com.sygneto.domain.ItemInward;
import com.sygneto.domain.ItemPrice;
import com.sygneto.domain.Media;
import com.sygneto.domain.Product;
import com.sygneto.domain.PurchaseRequest;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.InverdItemsRepository;
import com.sygneto.repository.ItemOutwardRepository;
import com.sygneto.repository.ItemRepository;
import com.sygneto.repository.MediaRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.ItemService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link com.sygneto.domain.ItemCategory}.
 */
@RestController
@RequestMapping("/api")
public class ItemResource {

	private final Logger log = LoggerFactory.getLogger(ItemResource.class);

	private static final String ENTITY_NAME = "item";

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	InverdItemsRepository inverdItemsRepository;

	@Autowired
	ItemOutwardRepository itemOutwardRepository;
	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ItemService itemService;

	public ItemResource(ItemService itemService) {
		this.itemService = itemService;
	}
	@Autowired
	CustomResponse customeResponce;
	
	@Autowired
    MediaRepository mediaRepository;
	
	@PostMapping("/item")
	public Object createItem(@RequestBody Item item) throws URISyntaxException {
		log.debug("REST request to save Item : {}", item);
		if (item.getItemId() != null) {
			throw new BadRequestAlertException("A new itemInward cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Item result = itemService.save(item);
	
		return customeResponce.success("Item  added successfully", result);
	}
	
	@PutMapping("/item")
	public Object updateItem(@RequestBody Item item) throws URISyntaxException {
		log.debug("REST request to update Item : {}", item);
		if (item.getItemId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		
		Item result = itemService.save(item);
		  
		return customeResponce.success("Item  Updated Successfully", result);
	}	
	@GetMapping("/item")
	public Object getAllItem() {
		log.debug("REST request to get all item");
		List<Item> result = itemService.findAll();
		for (Item item : result) {
			Long available = inverdItemsRepository.getTotalQtyInstock(item.getItemId());
			if (available != null) {
				item.setTotalQtyInStock(available);
			} else {
				item.setTotalQtyInStock(0L);
			}
		}

		if (result != null && !result.isEmpty()) {
			return customeResponce.success("Item  found", result);
		} else {
			return customeResponce.failure(404, "Item  Not found");
		}
	}
	
	
	  
	@GetMapping("/item/{id}")
	public Object getItem(@PathVariable Long id) {
		log.debug("REST request to get item : {}", id);
		Optional<Item> result = itemService.findOne(id);
		if (result.isPresent()) {
			return customeResponce.success("Item   found", result);
		} else {
			return customeResponce.failure(404, "Item  Not found");
		}
	}
	@GetMapping("/itemByCategory/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getItemByCategoryWise(@PathVariable Long id) {
		log.debug("REST request to get item by itemByCategory : {}", id);
		List<Item> result=itemRepository.findByItemCategoryItemCategoryId(id);
		if(result ==null || result.isEmpty()) {
			return customeResponce.failure(404, "Item not found");
		}else {
			return customeResponce.success("Item found", result);
		}
		
		
	}
	@DeleteMapping("/item/{id}")
	public Object deleteItem(@PathVariable Long id) {
		log.debug("REST request to get item : {}", id);
		itemService.delete(id);
		SygnetoResponse res = new SygnetoResponse();
		List<Item> result=itemRepository.findByItemCategoryItemCategoryId(id);
		res.setStatusCode(200);
		res.setStatus("Success");
		res.setMessage("Item and associated media are deleted");
		return res;
	}
	/*@PostMapping("/item")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object singleFileUpload(@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam(value ="parentItemId" , required = false) Long parentItemId, @RequestParam(value ="itemCode", required = false) String itemCode,
			@RequestParam(value ="itemName", required = false) String itemName, @RequestParam(value ="itemNumber", required = false) String itemNumber,
			@RequestParam(value ="sac", required = false) String sac, @RequestParam(value ="unitOfMeasurement", required = false) String unitOfMeasurement,
			@RequestParam(value ="totalQtyInStock", required = false) Long totalQtyInStock, @RequestParam(value ="minQtyRequired", required = false) Long minQtyRequired,
			@RequestParam(value ="itemSpecification", required = false) String itemSpecification,
			@RequestParam(value ="itemDescription", required = false) String itemDescription, @RequestParam(value ="status", required = false) String status,
			@RequestParam(value ="itemCategoryId", required = false) Long itemCategoryId,
			@RequestParam(value = "itemId", required = false) Long itemId,
			@RequestParam(value = "imageUrl", required = false) String imageUrl,
			@RequestParam(value = "thumpNailUrl", required = false) String thumpNailUrl,
			@RequestParam(value = "itemPrice", required = false) float itemPrice,
			@RequestParam(value = "unitQuantity", required = false) Long unitQuantity,
			@RequestParam(value="mediaId",required=false)Long itemPriceId,
			@RequestPart(value = "medias", required = false) String medias,
	    RedirectAttributes redirectAttributes, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException, JSONException {
		log.debug("request comes for create item : {}");
		SygnetoResponse res = new SygnetoResponse();
		Set<Media> mediaList=new HashSet<Media>();
		File f3 = new File(Constants.ORIGINAL_FILE_PATH);
		Item item = new Item();
		ItemCategory itemCategory = new ItemCategory();
		ItemPrice itemPrice1=new ItemPrice();
		
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

		System.out.println("@@@@@@@@@@@@@@@@@@@@"+files);
		if(files !=null) {
			for (MultipartFile file : files) {
				Media media=new Media();
				if (file.isEmpty()) {
					redirectAttributes.addFlashAttribute("message", "Please select a file to upload");

					res.setStatusCode(404);
					res.setStatus("Please select a file to upload");
					return res;
				}
				try {
					String fileNameForExt = file.getOriginalFilename();
					String extension = FilenameUtils.getExtension(fileNameForExt);

					if (extension.isEmpty()) {

						res.setStatusCode(404);
						res.setStatus("Please select a correct file to upload");
						return res;
					}
					Calendar cal = new GregorianCalendar();
					Double randomValue = Math.random() * 100;
					String uniqueName = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH)
							+ cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.HOUR) + cal.get(Calendar.MINUTE)
							+ cal.get(Calendar.SECOND) + cal.get(Calendar.MILLISECOND) + +cal.get(Calendar.MINUTE)
							+ cal.get(Calendar.MILLISECOND) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH)
							+ cal.get(Calendar.HOUR) + cal.get(Calendar.SECOND) + cal.get(Calendar.YEAR)
							+ randomValue.intValue();

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
						media.setThumpNailUrl(onlineUrl);
						
						// item.setImageUrl(onlineUrl);
						// item.setCompressedFilePath(onlineUrl);
						media.setThumpNailPath(Constants.SERVER_URL + onlineUrl);
					} else {
						onlineUrl = Constants.DATA + uniqueName + "." + extension;

						media.setThumpNailUrl(onlineUrl);
						
						media.setThumpNailPath(Constants.SERVER_URL + onlineUrl);
						// item.setImageUrl(onlineUrl);

					}
					onlineUrl = Constants.DATA + uniqueName + "." + extension;
					// item.setItemCategory(itemCategory);
					itemCategory.setItemCategoryId(itemCategoryId);
					
					//item.setItemPrice(itemPrice);
					
					item.setItemCategory(itemCategory);
					media.setBaseImageUrl(onlineUrl);
					media.setBaseImagePath(Constants.SERVER_URL + onlineUrl);
					media.setImageName(itemName);
					
					media.setStatus("ENABLED");
					media.setItemType(extension);
					media.setItemCode(itemCode);
					
					item.setUnitQuantity(unitQuantity);
					item.setItemPrice(itemPrice);
					item.setItemCode(itemCode);
					item.setItemDescription(itemDescription);
					item.setItemName(itemName);
					item.setItemNumber(itemNumber);
					item.setItemSpecification(itemSpecification);
					item.setMinQtyRequired(minQtyRequired);
					item.setParentItemId(parentItemId);
					item.setSac(sac);
					item.setStatus(status);
					item.setTotalQtyInStock(totalQtyInStock);
					item.setUnitOfMeasurement(unitOfMeasurement);

					Media mediaResult=mediaRepository.save(media);
					System.out.println("@@@@@@@@@@@@@@@@@@@@"+mediaResult);
					//result.get().setBaseImageUrl(onlineUrl);
					mediaList.add(mediaResult);
				
				} catch (Exception e) {
				}
			}
			item.setMedia(mediaList);
			Item result = itemService.save(item);
			for (Media media2 : mediaList) {
				Item itemObj=new Item();
				itemObj.setItemId(result.getItemId());
				media2.setItem(itemObj);
				Media mediaResult=mediaRepository.save(media2);
			}
             if(itemPriceId !=null) {
            	 getmediaInItem(itemPriceId, result);
             }
			JSONObject objects=null;
			if (medias != null) {

				JSONArray jsonArray = new JSONArray(medias);
				for(int i = 0; i < jsonArray.length(); i++)
				{
				      objects = jsonArray.getJSONObject(i);
				      System.out.println("@@@@@@@@@@@@"+objects);
				      ObjectMapper objectMapper1 = new ObjectMapper();
				      Media media = objectMapper1.readValue(objects.toString(), Media.class);
				  	Media mediaObj=mediaRepository.findBymId(media.getmId());
				      if(mediaObj.getItem() == null) {
				    	  Item itemObj=new Item();
							itemObj.setItemId(result.getItemId());
							mediaObj.setItem(itemObj);;
							Media mediaResult=mediaRepository.save(mediaObj);
						}else {
							Media mediaOBJ=new Media();
							mediaOBJ.setBaseImagePath(mediaObj.getBaseImagePath());
							mediaOBJ.setBaseImageUrl(mediaObj.getBaseImageUrl());
							mediaOBJ.setImageName(mediaObj.getImageName());
							mediaOBJ.setStatus(mediaObj.getStatus());
							mediaOBJ.setThumpNailPath(mediaObj.getThumpNailPath());
							mediaOBJ.setThumpNailUrl(mediaObj.getThumpNailUrl());
							mediaOBJ.setItemCode(mediaObj.getItemCode());
							mediaOBJ.setItemType(mediaObj.getItemType());
							mediaOBJ.setMode(mediaObj.getMode());
							mediaOBJ.setCreateMode(mediaObj.getCreateMode());
							Item itemObj1=new Item();
							itemObj1.setItemId(result.getItemId());
							mediaOBJ.setItem(itemObj1);
							Media mediaResult=mediaRepository.save(mediaOBJ);
						}
				}
			}
			// result.setImagePath(Constants.SERVER_URL + result.getImageUrl());
			// result.setThumpNailPath(Constants.SERVER_URL + result.getThumpNailUrl());
			return customeResponce.success("Item created", result);
		}else {
			System.out.println("!!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			itemCategory.setItemCategoryId(itemCategoryId);
			itemPrice1.setItemPriceId(itemPriceId);
			//item.setItemPrice(itemPrice);
			item.setItemCategory(itemCategory);
			item.setImageUrl(onlineUrl);
			item.setUnitQuantity(unitQuantity);
			item.setItemPrice(itemPrice);
			item.setItemCode(itemCode);
			item.setItemDescription(itemDescription);
			item.setItemName(itemName);
			item.setItemNumber(itemNumber);
			item.setItemSpecification(itemSpecification);
			item.setMinQtyRequired(minQtyRequired);
			item.setParentItemId(parentItemId);
			item.setSac(sac);
			item.setStatus(status);
			item.setTotalQtyInStock(totalQtyInStock);
			item.setUnitOfMeasurement(unitOfMeasurement);

			Item result = itemService.save(item);
			System.out.println("@@@@@@@@@@@@@"+result.getItemId());
		
			if(itemPriceId !=null) {
				getmediaInItem(itemPriceId, result);
			}
			JSONObject objects=null;
			if (medias != null) {

				JSONArray jsonArray = new JSONArray(medias);
				for(int i = 0; i < jsonArray.length(); i++)
				{
				      objects = jsonArray.getJSONObject(i);
				      System.out.println("@@@@@@@@@@@@"+objects);
				      ObjectMapper objectMapper1 = new ObjectMapper();
				      Media media = objectMapper1.readValue(objects.toString(), Media.class);
				  	Media mediaObj=mediaRepository.findBymId(media.getmId());
				      if(mediaObj.getItem() == null) {
				    	  Item itemObj=new Item();
							itemObj.setItemId(result.getItemId());
							mediaObj.setItem(itemObj);;
							Media mediaResult=mediaRepository.save(mediaObj);
						}else {
							Media mediaOBJ=new Media();
							mediaOBJ.setBaseImagePath(mediaObj.getBaseImagePath());
							mediaOBJ.setBaseImageUrl(mediaObj.getBaseImageUrl());
							mediaOBJ.setImageName(mediaObj.getImageName());
							mediaOBJ.setStatus(mediaObj.getStatus());
							mediaOBJ.setThumpNailPath(mediaObj.getThumpNailPath());
							mediaOBJ.setThumpNailUrl(mediaObj.getThumpNailUrl());
							mediaOBJ.setItemCode(mediaObj.getItemCode());
							mediaOBJ.setItemType(mediaObj.getItemType());
							mediaOBJ.setMode(mediaObj.getMode());
							mediaOBJ.setCreateMode(mediaObj.getCreateMode());
							Item itemObj1=new Item();
							itemObj1.setItemId(result.getItemId());
							mediaOBJ.setItem(itemObj1);
							Media mediaResult=mediaRepository.save(mediaOBJ);
						}
				}
			}
		
			return customeResponce.success("Item created", result);
		}
	}*/

	/*public void getmediaInItem(Long mediaId, Item result) {
		log.debug("REST request to add getmediaInItem");

		Media mediaObj=mediaRepository.findBymId(mediaId);
		System.out.println("@@@@@@@@@@@@@@@!!!!!!!!!!!"+mediaObj);
		
		if(mediaObj.getItem() == null) {
			Item itemObj=new Item();
			itemObj.setItemId(result.getItemId());
			mediaObj.setItem(itemObj);
			Media mediaResult=mediaRepository.save(mediaObj);
		}else {
			Media media=new Media();
			media.setBaseImagePath(mediaObj.getBaseImagePath());
			media.setBaseImageUrl(mediaObj.getBaseImageUrl());
			media.setImageName(mediaObj.getImageName());
			media.setStatus(mediaObj.getStatus());
			media.setThumpNailPath(mediaObj.getThumpNailPath());
			media.setThumpNailUrl(mediaObj.getThumpNailUrl());
			Item itemObj=new Item();
			itemObj.setItemId(result.getItemId());
			media.setItem(itemObj);
			Media mediaResult=mediaRepository.save(media);
		}
	
	}*/
	
	
/*	@PutMapping("/item")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object singleFileUploads(@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam(value ="parentItemId" , required = false) Long parentItemId, @RequestParam(value ="itemCode", required = false) String itemCode,
			@RequestParam(value ="itemName", required = false) String itemName, @RequestParam(value ="itemNumber", required = false) String itemNumber,
			@RequestParam(value ="sac", required = false) String sac, @RequestParam(value ="unitOfMeasurement", required = false) String unitOfMeasurement,
			@RequestParam(value ="totalQtyInStock", required = false) Long totalQtyInStock, @RequestParam(value ="minQtyRequired", required = false) Long minQtyRequired,
			@RequestParam(value ="itemSpecification", required = false) String itemSpecification,
			@RequestParam(value ="itemDescription", required = false) String itemDescription, @RequestParam(value ="status", required = false) String status,
			@RequestParam(value ="itemCategoryId", required = false) Long itemCategoryId,
			@RequestParam(value = "itemId", required = false) Long itemId,
			@RequestParam(value = "imageUrl", required = false) String imageUrl,
			@RequestParam(value = "thumpNailUrl", required = false) String thumpNailUrl,
			@RequestParam(value = "itemPrice", required = false) float itemPrice,
			@RequestParam(value = "unitQuantity", required = false) Long unitQuantity,
			@RequestParam(value="mediaId",required=false)Long itemPriceId,
			@RequestPart(value = "medias", required = false) String medias,
			RedirectAttributes redirectAttributes, HttpServletRequest request) throws JSONException, JsonParseException, JsonMappingException, IOException {
		SygnetoResponse res = new SygnetoResponse();
		Set<Media> mediaList=new HashSet<Media>();
		log.debug("request comes for create item : {}");
		
		if (itemId == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		
		Optional<Item> item = itemService.findOne(itemId);

		File f3 = new File(Constants.ORIGINAL_FILE_PATH);

		ItemCategory itemCategory = new ItemCategory();
		
		
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

		if(files !=null) {
			for (MultipartFile file : files) {
				Media media=new Media();
				if (file.isEmpty()) {
					redirectAttributes.addFlashAttribute("message", "Please select a file to upload");

					res.setStatusCode(404);
					res.setStatus("Please select a file to upload");
					return res;
				}
				try {
					String fileNameForExt = file.getOriginalFilename();
					String extension = FilenameUtils.getExtension(fileNameForExt);

					if (extension.isEmpty()) {

						res.setStatusCode(404);
						res.setStatus("Please select a correct file to upload");
						return res;
					}
					Calendar cal = new GregorianCalendar();
					Double randomValue = Math.random() * 100;
					String uniqueName = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH)
							+ cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.HOUR) + cal.get(Calendar.MINUTE)
							+ cal.get(Calendar.SECOND) + cal.get(Calendar.MILLISECOND) + +cal.get(Calendar.MINUTE)
							+ cal.get(Calendar.MILLISECOND) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH)
							+ cal.get(Calendar.HOUR) + cal.get(Calendar.SECOND) + cal.get(Calendar.YEAR)
							+ randomValue.intValue();

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
						media.setThumpNailUrl(onlineUrl);
						// item.setImageUrl(onlineUrl);
						// item.setCompressedFilePath(onlineUrl);
						media.setThumpNailPath(Constants.SERVER_URL + onlineUrl);
					} else {
						onlineUrl = Constants.DATA + uniqueName + "." + extension;

						media.setThumpNailUrl(onlineUrl);
						
						media.setThumpNailPath(Constants.SERVER_URL + onlineUrl);
						// item.setImageUrl(onlineUrl);

					}
					onlineUrl = Constants.DATA + uniqueName + "." + extension;
					// item.setItemCategory(itemCategory);
					itemCategory.setItemCategoryId(itemCategoryId);
					
					//item.setItemPrice(itemPrice);
					
					if (itemCategoryId != null) {
						itemCategory.setItemCategoryId(itemCategoryId);
						item.get().setItemCategory(itemCategory);
					}
					media.setBaseImageUrl(onlineUrl);
					media.setBaseImagePath(Constants.SERVER_URL + onlineUrl);
					media.setImageName(itemName);
					media.setStatus("ENABLED");
					media.setItemType(extension);
					media.setItemCode(itemCode);
					if (itemCode != null) {
						item.get().setItemCode(itemCode);
					}
					if (itemDescription != null) {
						item.get().setItemDescription(itemDescription);
					}
					if (itemName != null) {
						item.get().setItemName(itemName);
					}
					if (itemNumber != null) {
						item.get().setItemNumber(itemNumber);
					}
					if (itemSpecification != null) {
						item.get().setItemSpecification(itemSpecification);
					}
					if (minQtyRequired != null) {
						item.get().setMinQtyRequired(minQtyRequired);
					}
					if (parentItemId != null) {
						item.get().setParentItemId(parentItemId);
					}
					if (sac != null) {
						item.get().setSac(sac);
					}
					if (status != null) {
						item.get().setStatus(status);
					}
					
					if (unitQuantity != null) {
						item.get().setUnitQuantity(unitQuantity);
					}
				
					if (itemPrice != 0.0f) {
						item.get().setItemPrice(itemPrice);
					}
					
					if (totalQtyInStock != null) {
						item.get().setTotalQtyInStock(totalQtyInStock);
					}
					if (unitOfMeasurement != null) {
						item.get().setUnitOfMeasurement(unitOfMeasurement);
					}

					Media mediaResult=mediaRepository.save(media);
					System.out.println("@@@@@@@@@@@@@@@@@@@@"+mediaResult);
					//result.get().setBaseImageUrl(onlineUrl);
					mediaList.add(mediaResult);
				
				} catch (Exception e) {
				}
			}
			//item.get().setMedia(mediaList);
			Item result = itemService.save(item.get());
			for (Media media2 : mediaList) {
				Item itemObj=new Item();
				itemObj.setItemId(result.getItemId());
				//media2.setItem(itemObj);
				Media mediaResult=mediaRepository.save(media2);
			}
			if(itemPriceId !=null) {
				getmediaInItem(itemPriceId, result);
			}
			JSONObject objects=null;
			if (medias != null) {

				JSONArray jsonArray = new JSONArray(medias);
				for(int i = 0; i < jsonArray.length(); i++)
				{
				      objects = jsonArray.getJSONObject(i);
				      System.out.println("@@@@@@@@@@@@"+objects);
				      ObjectMapper objectMapper1 = new ObjectMapper();
				      Media media = objectMapper1.readValue(objects.toString(), Media.class);
				  	Media mediaObj=mediaRepository.findBymId(media.getmId());
				      if(mediaObj.getItem() == null) {
				    	  Item itemObj=new Item();
							itemObj.setItemId(result.getItemId());
							mediaObj.setItem(itemObj);;
							Media mediaResult=mediaRepository.save(mediaObj);
						}else {
							Media mediaOBJ=new Media();
							mediaOBJ.setBaseImagePath(mediaObj.getBaseImagePath());
							mediaOBJ.setBaseImageUrl(mediaObj.getBaseImageUrl());
							mediaOBJ.setImageName(mediaObj.getImageName());
							mediaOBJ.setStatus(mediaObj.getStatus());
							mediaOBJ.setThumpNailPath(mediaObj.getThumpNailPath());
							mediaOBJ.setThumpNailUrl(mediaObj.getThumpNailUrl());
							mediaOBJ.setItemCode(mediaObj.getItemCode());
							mediaOBJ.setItemType(mediaObj.getItemType());
							mediaOBJ.setMode(mediaObj.getMode());
							mediaOBJ.setCreateMode(mediaObj.getCreateMode());
							Item itemObj1=new Item();
							itemObj1.setItemId(result.getItemId());
							mediaOBJ.setItem(itemObj1);
							Media mediaResult=mediaRepository.save(mediaOBJ);
						}
				}
			}
			// result.setImagePath(Constants.SERVER_URL + result.getImageUrl());
			// result.setThumpNailPath(Constants.SERVER_URL + result.getThumpNailUrl());
			return customeResponce.success("Item created", result);
		} else {
			if (itemCategoryId != null) {
				itemCategory.setItemCategoryId(itemCategoryId);
				item.get().setItemCategory(itemCategory);
			}
			if (itemPriceId != null) {
				itemPrice.setItemPriceId(itemPriceId);
				item.get().setItemPrice(itemPrice);
			}

			if (itemCode != null) {
				item.get().setItemCode(itemCode);
			}

			if (itemDescription != null) {
				item.get().setItemDescription(itemDescription);
			}

			if (itemName != null) {
				item.get().setItemName(itemName);
			}

			if (itemNumber != null) {
				item.get().setItemNumber(itemNumber);
			}

			if (itemSpecification != null) {
				item.get().setItemSpecification(itemSpecification);
			}

			if (minQtyRequired != null) {
				item.get().setMinQtyRequired(minQtyRequired);
			}

			if (parentItemId != null) {
				item.get().setParentItemId(parentItemId);
			}

			if (sac != null) {
				item.get().setSac(sac);
			}

			if (status != null) {
				item.get().setStatus(status);
			}

			if (totalQtyInStock != null) {
				item.get().setTotalQtyInStock(totalQtyInStock);
			}

			if (unitOfMeasurement != null) {
				item.get().setUnitOfMeasurement(unitOfMeasurement);
			}

			if (imageUrl != null) {
				item.get().setImageUrl(imageUrl);
			}
			if (thumpNailUrl != null) {
				item.get().setThumpNailUrl(thumpNailUrl);
			}

			if (unitQuantity != null) {
				item.get().setUnitQuantity(unitQuantity);
			}
		
			if (itemPrice != 0.0f) {
				item.get().setItemPrice(itemPrice);
			}
			
			Item result = itemService.save(item.get());
			
			if(itemPriceId !=null) {
				getmediaInItem(itemPriceId, result);
			}
			
			
			JSONObject objects=null;
			if (medias != null) {

				JSONArray jsonArray = new JSONArray(medias);
				for(int i = 0; i < jsonArray.length(); i++)
				{
				      objects = jsonArray.getJSONObject(i);
				      System.out.println("@@@@@@@@@@@@"+objects);
				      ObjectMapper objectMapper1 = new ObjectMapper();
				      Media media = objectMapper1.readValue(objects.toString(), Media.class);
				  	Media mediaObj=mediaRepository.findBymId(media.getmId());
				      if(mediaObj.getItem() == null) {
				    	  Item itemObj=new Item();
							itemObj.setItemId(result.getItemId());
							mediaObj.setItem(itemObj);;
							Media mediaResult=mediaRepository.save(mediaObj);
						}else {
							Media mediaOBJ=new Media();
							mediaOBJ.setBaseImagePath(mediaObj.getBaseImagePath());
							mediaOBJ.setBaseImageUrl(mediaObj.getBaseImageUrl());
							mediaOBJ.setImageName(mediaObj.getImageName());
							mediaOBJ.setStatus(mediaObj.getStatus());
							mediaOBJ.setThumpNailPath(mediaObj.getThumpNailPath());
							mediaOBJ.setThumpNailUrl(mediaObj.getThumpNailUrl());
							mediaOBJ.setItemCode(mediaObj.getItemCode());
							mediaOBJ.setItemType(mediaObj.getItemType());
							mediaOBJ.setMode(mediaObj.getMode());
							mediaOBJ.setCreateMode(mediaObj.getCreateMode());
							Item itemObj1=new Item();
							itemObj1.setItemId(result.getItemId());
							mediaOBJ.setItem(itemObj1);
							Media mediaResult=mediaRepository.save(mediaOBJ);
						}
				}
			}
			res.setStatusCode(200);
			res.setStatus("Success");
			res.setMessage("Item found");
			res.setData(result);
			return res;
		}
	}

	@GetMapping("/item")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllIteams() {
		log.debug("REST request to get all item");
		List<Item> result = itemService.findAll();
		
		for (Item item : result) {
			Set<Media> result1 = mediaRepository.findByItemItemId(item.getItemId());
			//item.setMedia(result1);
		}
		if (result != null&& !result.isEmpty()) {
			return customeResponce.success("Item found", result);

		} else {
			return customeResponce.failure(404, "Item not found");

		}
	}

	@GetMapping("/item/{id}")
	public Object getItem(@PathVariable Long id) {
		log.debug("REST request to get item : {}", id);
		Optional<Item> result = itemService.findOne(id);
		
		Set<Media> result1 = mediaRepository.findByItemItemId(result.get().getItemId());
		//result.get().setMedia(result1);
		System.out.println("!!!!!!!!!!!!!"+result);
		if (result.isPresent()) {
			result.get().getItemSupplierParty();
			return customeResponce.success("Item found", result);

		} else {
			return customeResponce.failure(404, "Item not found");

		}
	}
	
	@GetMapping("/itemByCategory/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getItemByCategoryWise(@PathVariable Long id) {
		log.debug("REST request to get item by itemByCategory : {}", id);
		List<Item> result=itemRepository.findByItemCategoryItemCategoryId(id);
		if(result ==null || result.isEmpty()) {
			return customeResponce.failure(404, "Item not found");
		}else {
			return customeResponce.success("Item found", result);
		}
		
		
	}
	// imageUrl": "/sygneto/data/2019922114027352403529221127201992.jpg",

	@DeleteMapping("/item/{id}")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public SygnetoResponse deleteItem(@PathVariable Long id) throws Exception {
		log.debug("REST request to delete item : {}", id);
		SygnetoResponse res = new SygnetoResponse();

		if (id != null) {
		Optional<Item> result = itemService.findOne(id);
		
		Set<Media> result1 = mediaRepository.findByItemItemId(result.get().getItemId());
		//result.get().setMedia(result1);
		System.out.println("!!!!!!!!!!!!!"+result);
		
		for (Media media : result1) {
			File f3 = new File(Constants.ORIGINAL_FILE_PATH);
			if (!f3.isDirectory()) {
				f3.mkdirs();
			}
			File f4 = new File(Constants.ORIGINAL_FILE_PATH);

			if (!f4.isDirectory()) {
				f4.mkdirs();
			}
			String newFilePath = f4.toString();
			String filePath5 = f3.toString();
			String str = "";
			String str1 = "";
			String str2 = "";
			String str3 = "";
			
			String imageUrl = media.getBaseImageUrl();
			String thumpNailUrl = media.getThumpNailUrl();
			StringTokenizer stk = new StringTokenizer(imageUrl, "/");
			while (stk.hasMoreTokens()) {
				str = stk.nextToken();
				if (!str.equalsIgnoreCase("sygneto") || !str.equalsIgnoreCase("data")) {
					str1 = str;
				}
			}

			StringTokenizer stringToken = new StringTokenizer(thumpNailUrl, "/");
			while (stringToken.hasMoreTokens()) {
				str2 = stringToken.nextToken();
				if (!str2.equalsIgnoreCase("sygneto") || !str2.equalsIgnoreCase("data")) {
					str3 = str2;
				}
			}

			try {
				Files.deleteIfExists(Paths.get(filePath5 + File.separator + str1));
				Files.deleteIfExists(Paths.get(newFilePath + File.separator + str3));
			} catch (NoSuchFileException e) {
				log.debug("No such file/directory exists");
			}
			mediaRepository.deleteById(media.getmId());
			
		}
		itemService.delete(id);
		res.setStatusCode(200);
		res.setStatus("Success");
		res.setMessage("Item and associated media are deleted");
		return res;
		}else {
			res.setStatusCode(404);
			res.setStatus("failure");
			res.setMessage("Item Not found");
			return res;
		}
	}*/
}
