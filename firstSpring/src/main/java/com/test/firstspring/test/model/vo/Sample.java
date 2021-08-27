package com.test.firstspring.test.model.vo;

//command object : input name == vo field
public class Sample implements java.io.Serializable{
	private static final long serialVersionUID = 9995L;
	
	private String name;
	private int age;
	
	public Sample() {}

	public Sample(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Sample [name=" + name + ", age=" + age + "]";
	}

	
	
}
