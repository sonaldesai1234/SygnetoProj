package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.sygneto.domain.GraphEdge;
import com.sygneto.domain.GraphNode;
import com.sygneto.domain.GraphResponce;
import com.sygneto.domain.Label;
import com.sygneto.domain.Node;
import com.sygneto.domain.NodeLabel;
import com.sygneto.service.ChildrenService;
import com.sygneto.service.LabelService;
import com.sygneto.service.NodeLabelService;
import com.sygneto.service.NodeService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing Node.
 */
@RestController
@RequestMapping("/api")
public class NodeResource {

	private final Logger log = LoggerFactory.getLogger(NodeResource.class);

	private static final String ENTITY_NAME = "node";

	private final NodeService nodeService;

	@Autowired
	ChildrenService childrenService;

	@Autowired
	CustomResponse customeResponce;

	@Autowired
	LabelService labelService;
	
	@Autowired
	NodeLabelService nodeLabelService;

	public NodeResource(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	/**
	 * POST /node : Create a new node.
	 *
	 * @param node
	 *            the node to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         node, or with status 400 (Bad Request) if the node has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/node")
	public Object createNode(@RequestBody Node node) throws URISyntaxException {
		log.debug("REST request to save Node : {}", node);
		if (node.getNodeId() != null) {
			throw new BadRequestAlertException("A new node cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Node result = nodeService.save(node);
		if (result == null) {
			return customeResponce.failure(400, "Node not created");
		}
		if("START".equalsIgnoreCase(result.getNodeType())){
			result.setTreeId(result.getNodeId());
		}
		
		nodeService.save(result);
		if(result.getLabel()!=null)
		{
			NodeLabel nodeLabel=result.getLabel();
			Node node2=new Node();
			node2.setNodeId(result.getNodeId());
			nodeLabel.setNode(node2);
			nodeLabelService.save(nodeLabel);
		}
		Set<Children> childrenList = new HashSet<>();
		
		if (node.getChildren() != null && !node.getChildren().isEmpty()) {
			for (Children children : node.getChildren()) {
				Node node1 = new Node();
				node1.setNodeId(result.getNodeId());
				children.setNode(node1);
				Children children1 = childrenService.save(children);

				Label label = new Label();
				if(children1.getLabel()!=null)
				{
					
				}
				label = children1.getLabel();
				Children children2 = new Children();
				children2.setActionId(children1.getActionId());
				label.setChildren(children2);

				labelService.save(label);

				childrenList.add(children);
			}
		}
		result.setChildren(childrenList);
		return customeResponce.success("Node created", result);
	}

	/**
	 * PUT /node : Updates an existing node.
	 *
	 * @param node
	 *            the node to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         node, or with status 400 (Bad Request) if the node is not valid, or
	 *         with status 500 (Internal Server Error) if the node couldn't be
	 *         updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/node")
	public Object updateNode(@RequestBody Node node) throws URISyntaxException {
		log.debug("REST request to update Node : {}", node);
		if (node.getNodeId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Node result = nodeService.save(node);
		Set<Children> childrenList = new HashSet<>();

		if (result == null) {
			return customeResponce.failure(400, "Node Not updated");
		}
		
		if(result.getLabel()!=null)
		{
			NodeLabel nodeLabel=result.getLabel();
			Node node1=new Node();
			node1.setNodeId(result.getNodeId());
			nodeLabel.setNode(node1);
			nodeLabelService.save(nodeLabel);
		}
		
		if (node.getChildren() != null && !node.getChildren().isEmpty()) {
			for (Children children : node.getChildren()) {
				Node node1 = new Node();
				node1.setNodeId(result.getNodeId());
				children.setNode(node1);

				Children children1 = childrenService.save(children);

				Label label = new Label();
				label = children1.getLabel();
				Children children2 = new Children();
				children2.setActionId(children1.getActionId());
				label.setChildren(children2);

				labelService.save(label);
				childrenList.add(children);
			}
		}

		result.setChildren(childrenList);
		return customeResponce.success("Node updated", result);
	}

	/**
	 * GET /node : get all the node.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of node in body
	 */
	@GetMapping("/node")
	public Object getAllNodes() {
		log.debug("REST request to get all Nodes");
		List<Node> nodeList = nodeService.findAll();

		if (nodeList.isEmpty()) {
			return customeResponce.failure(404, "Node Not found");
		}

		return customeResponce.success("Node found", nodeList);

	}

	/**
	 * GET /node/:id : get the "id" node.
	 *
	 * @param id
	 *            the id of the node to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the node, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping("/node/{id}")
	public Object getNode(@PathVariable Long id) {
		log.debug("REST request to get Node : {}", id);
		Optional<Node> node = nodeService.findOne(id);

		if (node.isPresent()) {
			return customeResponce.success("Node found", node.get());
		}
		return customeResponce.failure(404, "Node Not found");

	}

	@GetMapping("/nodeByTreeId/{id}")
	public Object getNodeByTreeId(@PathVariable Long id) {
		log.debug("REST request to  get NodeByTreeId : {}", id);
		List<Node>  nodeList = nodeService.findByTreeId(id);

		if (nodeList.isEmpty()) {
			return customeResponce.failure(404, "Node Not found");
		}
		return customeResponce.success("Node found", nodeList);

	}
	
	@GetMapping("/graph/{nodeId}")
	public Object getGraphData(@PathVariable String nodeId) {

		GraphResponce graphResponce = new GraphResponce();

		Set<GraphNode> nodeList = new HashSet<>();
		Set<GraphEdge> edgeList = new HashSet<>();

		feedChildren(nodeId, nodeList, edgeList);

		graphResponce.setNode(nodeList);
		graphResponce.setEdge(edgeList);

		return graphResponce;
	}

	/**
	 * DELETE /node/:id : delete the "id" node.
	 *
	 * @param id
	 *            the id of the node to delete
	 * @return the ResponseEntity with status 200 (OK)
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@DeleteMapping("/node/{nodeId}")
	public void deleteNode(@PathVariable String nodeId) throws Exception {
		log.debug("REST request to delete Node : {}", nodeId);

		Set<GraphNode> nodeList = new HashSet<>();
		Set<GraphEdge> edgeList = new HashSet<>();

		feedChildren(nodeId, nodeList, edgeList);

		for (GraphEdge graphEdge : edgeList) {

			childrenService.deleteByNextAction(Long.parseLong(graphEdge.getTarget()));
		}

		for (GraphNode graphNode : nodeList) {

			nodeService.delete(Long.parseLong(graphNode.getId()));
		}

	}

	private void feedChildren(String nodeId, Set<GraphNode> nodeList, Set<GraphEdge> edgeList) {
		GraphNode graphNode = new GraphNode();
		Optional<Node> isNode = nodeService.findOne(Long.parseLong(nodeId));
		if (isNode.isPresent()) {
			Node node = isNode.get();
			graphNode.setId(nodeId);
			if(node.getLabel()!=null)
			{
				graphNode.setLabel(node.getLabel().getAction()+" "+node.getLabel().getRole());	
			}
			
			graphNode.setType(node.getNodeType());
			graphNode.setTreeId(""+node.getTreeId());

			nodeList.add(graphNode);
			
			if("CHILD".equals(node.getNodeType())) {
					if(node.getNextAction() !=null && !"".equals(node.getNextAction())) {
						GraphEdge graphEdge = new GraphEdge();
						graphEdge.setSource(nodeId);
						if(node.getLabel()!=null)
						{
							graphNode.setLabel(node.getLabel().getAction()+" "+node.getLabel().getRole()+ " "+node.getLabel().getLabel());	
						}
						
						graphEdge.setTarget("" + node.getNextAction());
						edgeList.add(graphEdge);
						feedChildren("" + node.getNextAction(), nodeList, edgeList);
					}else {
						for (Children children : node.getChildren()) {
							GraphEdge graphEdge1 = new GraphEdge();
			
							graphEdge1.setSource(nodeId);
			
							if (children.getLabel() != null) {
								graphEdge1.setLabel(children.getLabel().getKey() + " " + children.getLabel().getCondition() + " "
										+ children.getLabel().getValue());
							}
			
							graphEdge1.setTarget("" + children.getNextAction());
							edgeList.add(graphEdge1);
							feedChildren("" + children.getNextAction(), nodeList, edgeList);
						}
					}
				
			}else {
				for (Children children : node.getChildren()) {
					GraphEdge graphEdge = new GraphEdge();
	
					graphEdge.setSource(nodeId);
	
					if (children.getLabel() != null) {
						graphEdge.setLabel(children.getLabel().getKey() + " " + children.getLabel().getCondition() + " "
								+ children.getLabel().getValue());
					}
	
					graphEdge.setTarget("" + children.getNextAction());
					edgeList.add(graphEdge);
					feedChildren("" + children.getNextAction(), nodeList, edgeList);
	
				}
			  }
		}
	}
}
