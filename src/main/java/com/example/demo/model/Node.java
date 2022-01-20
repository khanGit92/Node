package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "NODE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "NODE_TYPE", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode
public abstract class Node {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "node_id", nullable = false)
	private int id;
	@Column(unique = true)
	private String name;
	private int value;

	public String getName() {
		return name;
	}

	public abstract int getValue();

	public abstract int getValue(List<Node> exclusionList);

}
