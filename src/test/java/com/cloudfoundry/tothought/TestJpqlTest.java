package com.cloudfoundry.tothought;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.cloudfoundry.tothought.entities.Post;
import com.cloudfoundry.tothought.entities.PostPart;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:META-INF/application-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class TestJpqlTest {

	@PersistenceContext
	EntityManager em;
	
	@Test
	public void test() {
		TypedQuery<Post> query= em.createQuery("select  p from Post p" , Post.class);
		List<Post> posts = query.getResultList();
	
		for(Post post: posts){
			System.out.println(post.getTitle());
		}
	}
	
	@Test
	public void compositeTest(){
		TypedQuery<PostPart> query= em.createNamedQuery("select p.postPart from Post p", PostPart.class );
				List<PostPart> list = query.getResultList();
				for(PostPart part: list){
					System.out.println(part.getBody());
				}
	}
	
	@Test
	public void filterTest(){
		System.out.println("Please provide name:");
		Scanner scanner= new Scanner(System.in);
		String name= scanner.nextLine();
		
		TypedQuery<Post> query= em.createQuery("select p from Post where p.stamp.author like '%" +name
				+ "%'", Post.class  );
		
		List<Post> posts = query.getResultList();

		for(Post post: posts){
			System.out.println(post.getTitle());
		}
		
	}

}
