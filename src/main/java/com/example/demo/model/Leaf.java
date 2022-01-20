package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("leaf")
@NoArgsConstructor
public class Leaf extends Node {
	private final int holdingCount = 100;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "leaf_id", updatable = false, nullable = false)
	private int id;
	
	private String name;
	@Column(nullable = false)
	private int value;

	public Leaf(String name, int value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getValue() {
		return this.value*holdingCount;
	}

	@Override
	public int getValue(List<Node> exclusionList) {
		return getValue();
	}

}
