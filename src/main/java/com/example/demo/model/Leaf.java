package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("leaf")
public class Leaf extends Node {

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

	public Leaf() {
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getValue() {
		return this.value;
	}

}
