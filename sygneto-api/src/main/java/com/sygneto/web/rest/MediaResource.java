package com.sygneto.web.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sygneto.config.Constants;
import com.sygneto.domain.Authority;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemCategory;
import com.sygneto.domain.ItemPrice;
import com.sygneto.domain.Media;
import com.sygneto.domain.Role;
import com.sygneto.domain.Sector;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.AuthorityRepository;
import com.sygneto.repository.MediaRepository;
import com.sygneto.repository.RoleRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.RoleService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing media.
 */
@RestController
@RequestMapping("/api")
public class MediaResource {

	private final Logger log = LoggerFactory.getLogger(MediaResource.class);

	private static final String ENTITY_NAME = "media";

	
	@Autowired
	CustomResponse customeResponce;

	@Value("${jhipster.clientApp.name}")
    private String applicationName;
	
	@Autowired
	MediaRepository mediaRepository;
	
	@ApiOperation(value="Add Media like Image,Video,Document in Media")
	@PostMapping("/uploadMedia")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object singleFileUpload(@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam(value ="imageName", required = false) String imageName,
			@RequestParam(value ="fileName", required = false) String fileName, @RequestParam(value ="status", required = false) String status,
			@RequestParam(value ="itemCode", required = false) String itemCode, @RequestParam(value ="mode", required = false) String mode,
			//@RequestParam(value ="createMode", required = false) String createMode, 
	    RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		log.debug("request comes for create media : {}");
		SygnetoResponse res = new SygnetoResponse();
		Set<Media> mediaList=new HashSet<Media>();
		
		File f3 = new File(Constants.ORIGINAL_FILE_PATH);
		Item item = new Item();
		ItemCategory itemCategory = new ItemCategory();
		ItemPrice itemPrice=new ItemPrice();
		
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
						media.setThumbNailUrl(onlineUrl);
						// item.setImageUrl(onlineUrl);
						// item.setCompressedFilePath(onlineUrl);
						media.setThumbNailPath(Constants.SERVER_URL + onlineUrl);
					} else {
						onlineUrl = Constants.DATA + uniqueName + "." + extension;

						media.setThumbNailUrl(onlineUrl);
						
						media.setThumbNailPath(Constants.SERVER_URL + onlineUrl);
						// item.setImageUrl(onlineUrl);

					}
					onlineUrl = Constants.DATA + uniqueName + "." + extension;
					media.setBaseImageUrl(onlineUrl);
					media.setBaseImagePath(Constants.SERVER_URL + onlineUrl);
					media.setImageName(imageName);
					
					media.setItemCode(itemCode);
					//media.setMode(mode);
					//media.setCreateMode(createMode);
					media.setFileName(fileName);
					media.setItemType(extension);
					media.setStatus(status);
					
					Media mediaResult=mediaRepository.save(media);
					mediaList.add(mediaResult);
					
				} catch (Exception e) {
				}
			}
			return customeResponce.success("Item created", mediaList);
		}
		return null;
	}
	@ApiOperation(value="Delete Specific Media Item as per Media Id")
	@DeleteMapping("/media/{id}")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public SygnetoResponse deleteItem(@PathVariable Long id) throws Exception {
		log.debug("REST request to delete media : {}", id);
		SygnetoResponse res = new SygnetoResponse();

		if (id != null) {

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
			Media item = mediaRepository.findBymId(id);
			System.out.println("@@@@@@@@@@"+item);
			String str = "";
			String str1 = "";
			String str2 = "";
			String str3 = "";
			if (item != null) {
				System.out.println("@@@@@@@@@@"+item);
				String imageUrl = item.getBaseImageUrl();
				String thumpNailUrl = item.getThumbNailUrl();
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
				mediaRepository.deleteById(item.getmId());
				res.setStatusCode(200);
				res.setStatus("Success");
				res.setMessage("media deleted");
				return res;

			} else {
				res.setStatusCode(404);
				res.setStatus("failure");
				res.setMessage("Media Not found");
				return res;
			}
		} else {
			res.setStatusCode(404);
			res.setStatus("failure");
			res.setMessage("Media Not found");
			return res;
		}
	}
	
	@ApiOperation(value="Get All Media Items")
	@GetMapping("/media")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllIteams() {
		log.debug("REST request to get all item");
			List<Media> result = mediaRepository.findAll();
		if (result != null&& !result.isEmpty()) {
			return customeResponce.success("media found", result);

		} else {
			return customeResponce.failure(404, "media not found");

		}
	}
	
	@ApiOperation(value="Get Specific Media Item as per Media Id")
	@GetMapping("/media/{id}")
	public Object getItem(@PathVariable Long id) {
		log.debug("REST request to get item : {}", id);
	     Media result = mediaRepository.findBymId(id);
	
		if (result !=null) {
			return customeResponce.success("media found", result);

		} else {
			return customeResponce.failure(404, "media not found");

		}
	}
}
	