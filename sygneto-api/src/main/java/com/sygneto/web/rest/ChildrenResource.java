package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.Children;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Label;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.ChildrenService;
import com.sygneto.service.LabelService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing Children.
 */
@RestController
@RequestMapping("/api")
public class ChildrenResource {

	private final Logger log = LoggerFactory.getLogger(ChildrenResource.class);

	private static final String ENTITY_NAME = "children";

	private final ChildrenService childrenService;
	
	@Autowired
	LabelService labelService;

	public ChildrenResource(ChildrenService childrenService) {
		this.childrenService = childrenService;
	}

	@Autowired
	CustomResponse customeResponce;

	/**
	 * POST /children : Create a new children.
	 *
	 * @param children
	 *            the children to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         children, or with status 400 (Bad Request) if the children has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/children")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createChildren(@RequestBody Children children) throws URISyntaxException {
		log.debug("REST request to save Children : {}", children);

		if (children.getActionId() != null) {
			throw new BadRequestAlertException("A new children cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Children result = childrenService.save(children);
		if (result == null) {
			return customeResponce.failure(400, "Children not created");
		}

		Label label=new Label();
		label=children.getLabel();
		Children children1=new Children();
		children1.setActionId(result.getActionId());
		label.setChildren(children1);
		
		labelService.save(label);
		
	
		return customeResponce.success("Children created", result);

	}

	/**
	 * PUT /children : Updates an existing children.
	 *
	 * @param children
	 *            the children to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         children, or with status 400 (Bad Request) if the children is not
	 *         valid, or with status 500 (Internal Server Error) if the children
	 *         couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/children")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object updateChildren(@RequestBody Children children) throws URISyntaxException {
		log.debug("REST request to update Children : {}", children);

		if (children.getActionId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Children result = childrenService.save(children);

		if (result == null) {
			return customeResponce.failure(400, "Children not updated");
		}

		return customeResponce.success("Children updated", result);
	}

	/**
	 * GET /children : get all the children.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of children in
	 *         body
	 */
	@GetMapping("/children")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllChildren() {
		log.debug("REST request to get all Children");

		List<Children> ChildrenList = childrenService.findAll();

		if (ChildrenList.isEmpty()) {
			return customeResponce.failure(400, "Children not found");
		}

		return customeResponce.success("Children found", ChildrenList);

	}

	/**
	 * GET /children/:id : get the "id" children.
	 *
	 * @param id
	 *            the id of the children to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the children,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/children/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getChildren(@PathVariable Long id) {
		log.debug("REST request to get Children : {}", id);

		Optional<Children> children = childrenService.findOne(id);

		if (children.isPresent()) {
			return customeResponce.success("Children found", children.get());
		}

		return customeResponce.failure(400, "Children not found");
	}

	/**
	 * DELETE /children/:id : delete the "id" children.
	 *
	 * @param id
	 *            the id of the children to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/children/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object deleteChildren(@PathVariable Long id) {
		log.debug("REST request to delete Children : {}", id);

		try {
			childrenService.delete(id);
			return customeResponce.success("Node found", null);
		} catch (Exception e) {

			return customeResponce.failure(400, "Node Not deleted");
		}

	}
}
