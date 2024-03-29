package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.exception.DuplicateNodeException;
import com.example.demo.model.Composite;
import com.example.demo.model.Leaf;
import com.example.demo.model.Node;
import com.example.demo.repository.NodeRepository;

@SpringBootTest
public class NodeServiceTest {

	@MockBean
	private NodeRepository nodeRepository;
	@Autowired
	private NodeService nodeService;

	@Test
	public void nodeTest() {
		Leaf h1 = new Leaf("holding1", 10);
		Leaf h2 = new Leaf("holding2", 20);
		Leaf h3 = new Leaf("holding3", 30);
		this.nodeService.saveNode(h1);
		this.nodeService.saveNode(h2);
		this.nodeService.saveNode(h3);

		Composite f1 = new Composite("fund1");
		this.nodeService.saveNode(f1);
		this.nodeService.linkNode(f1, h1);
		this.nodeService.linkNode(f1, h2);

		Composite f2 = new Composite("fund2");
		this.nodeService.saveNode(f2);
		this.nodeService.linkNode(f2, h1);
		this.nodeService.linkNode(f2, h3);

		Composite invest1 = new Composite("investor1");
		this.nodeService.saveNode(invest1);
		this.nodeService.linkNode(invest1, f1);
		this.nodeService.linkNode(invest1, f2);

		Composite invest2 = new Composite("investor2");
		this.nodeService.saveNode(invest2);
		this.nodeService.linkNode(invest2, f1);

		when(this.nodeRepository.findByName("investor1")).thenReturn(Optional.of(invest1));
		when(this.nodeRepository.findByName("investor2")).thenReturn(Optional.of(invest2));
		assertEquals(7000, this.nodeService.findNode("investor1").get().getValue());
		assertEquals(3000, this.nodeService.findNode("investor2").get().getValue());
	}

	@Test
	public void fundValueWithExclusionListTest() {
		Leaf h1 = new Leaf("holding1", 10);
		Leaf h2 = new Leaf("holding2", 20);
		Leaf h3 = new Leaf("holding3", 30);
		this.nodeService.saveNode(h1);
		this.nodeService.saveNode(h2);
		this.nodeService.saveNode(h3);

		Composite f1 = new Composite("fund1");
		this.nodeService.saveNode(f1);
		this.nodeService.linkNode(f1, h1);
		this.nodeService.linkNode(f1, h2);
		this.nodeService.linkNode(f1, h3);

		List<Node> exclusionList = new ArrayList<>();
		exclusionList.add(h1);
		exclusionList.add(h2);
		when(this.nodeRepository.findByName("fund1")).thenReturn(Optional.of(f1));
		assertEquals(3000, this.nodeService.findNode("fund1").get().getValue(exclusionList));

	}

	@Test
	public void shouldThrowDuplicateNodeException() {
		Leaf h1 = new Leaf("holding1", 10);
		Leaf h2 = new Leaf("holding2", 20);
		Leaf h3 = new Leaf("holding2", 30);
		this.nodeService.saveNode(h1);
		this.nodeService.saveNode(h2);

		when(this.nodeRepository.save(h3))
				.thenThrow(new DataIntegrityViolationException(""));
		try {
			this.nodeService.saveNode(h3);
		} catch (DuplicateNodeException e) {
			assertEquals("Node already exists wth the name : holding2", e.getMessage());
		}

	}

}
