package br.edu.ibmec.bigdatacloud.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ibmec.bigdatacloud.blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
}
