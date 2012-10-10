package com.gistlabs.mechanize.json.element;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.gistlabs.mechanize.json.Node;
import com.gistlabs.mechanize.json.nodeImpl.ObjectNodeImpl;

public class MixedChildrenElementsTest extends TestElementBaseClass {
	ObjectNodeImpl element = new ObjectNodeImpl(parseJson("{ \"a\" : 2, \"b\" : { \"a\" : \"x\", \"c\" : 4 }, \"c\" : [ { \"a\" : 1 }, { \"b\" : 2 } ] }"));
	
	@Test
	public void testPrimitiveChild() {		
		Node nested = element.getChild("a");
		assertNotNull(nested);
		
		Collection<String> attributes = element.getAttributes();
		assertEquals(1, attributes.size());
		assertTrue(attributes.contains("a"));
	}
	
	@Test
	public void testElementChild() {
		Node nested = element.getChild("b");
		assertNotNull(nested);
		assertTrue(nested instanceof ObjectNodeImpl);
		assertEquals("b", nested.getName());
		assertEquals(element, nested.getParent());
		assertEquals("x", nested.getAttribute("a"));
	}
	
	@Test
	public void testChildren() {
		List<Node> children = element.getChildren();
		assertEquals(3, children.size());
		
		Collection<String> names = new ArrayList<String>();
		names.add("b");
		names.add("c");
		
		assertTrue(names.contains(children.get(0).getName()));
		assertTrue(names.contains(children.get(1).getName()));
		assertTrue(names.contains(children.get(2).getName()));
	}

}