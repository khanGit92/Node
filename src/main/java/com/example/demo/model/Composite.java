package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("composite")
public class Composite extends Node {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "composite_id", updatable = false, nullable = false)
	private int id;
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "NODE_JOIN", joinColumns = { @JoinColumn(name = "composite_id") }, inverseJoinColumns = {
			@JoinColumn(name = "leaf_id") })
	private List<Node> nodes = new ArrayList<>();

	public Composite(String name) {
		this.name = name;
	}

	public Composite() {
	}

	public void addNode(Node node) {
		this.nodes.add(node);
	}

	public List<Node> getNodes() {
		return nodes;
	}

	@Override
	public int getValue() {
		return this.nodes.stream().mapToInt(Node::getValue).sum();
	}

	public int getValue(List<Node> node) {
		List<String> exclusionList = node.stream().map(Node::getName).collect(Collectors.toList());
		return this.nodes.stream().filter(n -> !exclusionList.contains(n.getName())).mapToInt(Node::getValue).sum();
	}

	@Override
	public String getName() {
		return this.name;
	}

}
