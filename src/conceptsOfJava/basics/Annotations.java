package conceptsOfJava.basics;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
@interface Test {

	//should ignore this test?
	public boolean enabled() default true;

}
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
@interface TesterInfo {

	String[] tags() default "";

	String createdBy() default "ANubhav";

	String lastModified() default "03/01/2014";

}
@TesterInfo(
		createdBy = "mkyong.com",
		tags = {"sales","test" }
	)
class TestExample {

	@Test
	void testA() {
	 
	}
}

public class Annotations{
	public static void main(String args[]){
		
		Class<TestExample>  obj= TestExample.class;
		
		Annotation annotation = obj.getAnnotation(TesterInfo.class);
		
		TesterInfo testInfo=(TesterInfo)annotation;
		
		System.out.println(testInfo.tags().length);
		
		
		for(Method method :  obj.getDeclaredMethods()){
			annotation = method.getAnnotation(Test.class);
			Test test=(Test)annotation;
			
			System.out.println(test.enabled());
		}
		
		
	}
}