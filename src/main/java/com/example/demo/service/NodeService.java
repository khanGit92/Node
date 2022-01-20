package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Composite;
import com.example.demo.model.Node;
import com.example.demo.repository.NodeRepository;
import com.example.demo.exception.*;
@Service
public class NodeService {
	
	@Autowired
	private NodeRepository nodeRepository;
	
	public void saveNode(Node node) throws DuplicateNodeException {
		try {
		nodeRepository.save(node);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateNodeException("Node already exists wth the name : "+ node.getName());
		}
	}
	public Optional<Node> findNode(String name) {
		return nodeRepository.findByName(name);
	}
	
	public void linkNode(Composite source, Node target) {
		source.addNode(target);
		nodeRepository.save(source);
	}


}
