package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Node;
@Repository
public interface NodeRepository extends JpaRepository<Node, Integer>{
	Optional<Node> findByName(String name);
}
