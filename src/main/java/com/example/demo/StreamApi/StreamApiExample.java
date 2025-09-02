package com.example.demo.StreamApi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StreamApiExample implements CommandLineRunner  {
	private final PasswordEncoder passwordEncoder; 
	
	  @Override
	public void run(String... args) {
//		  it will not do anything untill u provide a terminal operatorin like here filtering not done untill u do count
//		  when u want to pass a predicate like condition and check then use filter...
		  
//	  1.filter
	List<String> list = Arrays.asList("krisha", "krish","mitul","Kanha","bhumi" , "krisha");
    long res = list.stream()
			.filter(x -> x.startsWith("k"))
			.count();
    System.out.println("Element Who starts with k in List: "+res);
    
//    when u want to do something like pass somthing and do somthing like pass function then u can use map
    
//    2.map
     Stream<String> streams = list.stream().map(x -> x.toUpperCase());
     streams.forEach(x-> System.out.println(x));
     
//     one line 
     System.out.println("Uppercase list : ");
     list.stream()
     .map(x -> x.toUpperCase())
     .forEach(System.out::println);
     
//     we have foeEachOrderd which is use full when we have parallal stream consepts to return in same order as we added
     
//     sorted
     System.out.println("Sorted list : ");
     list.stream().sorted().forEach(System.out::println);
     
     System.out.println("Desending order list : ");
     list.stream()
     .sorted((a, b) -> b.compareTo(a)) // descending order
     .forEach(System.out::println);

   
     System.out.println("Distict count : " +  list.stream()
    	     .distinct().count());
     
     System.out.println("Comes First 2 : ");
     list.stream()
     .limit(2)  // take first 2 elements
     .forEach(System.out::println);
     
     System.out.println("Skip First 2 : ");
     list.stream()
     .skip(2)  // skip first 2 elements
     .forEach(System.out::println);

  // Handle streams of collections, lists, or arrays where each element is itself a collection;
//     flatten nested structures (e.g., lists within lists) so they can be processed as a single sequence of elements; 
//     transform and flatten elements at the same time.
     List<List<String>> listOfLists = Arrays.asList(
    		    Arrays.asList("krisha", "krish"),
    		    Arrays.asList("mitul", "bhumi"),
    		    Arrays.asList("john", "jane")
    		);
     System.out.println("Flatmap Working: ");
    		listOfLists.stream()
    		    .flatMap(List::stream) // flatten
    		    .skip(2)               // skip first 2 elements
    		    .limit(3)              // take next 3 elements
    		    .forEach(System.out::println);
    		
//    		peek is for do some action in between like it will not trasfer element like it will not modify an element ..
//    		diffrence between map and peek is ...map can modifie element and peek not 
//    		peek() does not change the original elements. It’s just for observing.
//    		Requires a terminal operation to actually execute; streams are lazy.
    		   List<String> peekResult = list.stream()
    		            .peek(name -> System.out.println("Peek: " + name)) // just observing
    		            .collect(Collectors.toList());
    		        System.out.println("Peek Result: " + peekResult);
    		        
    		        
//    		        Treminal operation
//    		        1.collect
//    		        Converts a stream into a collection (like List, Set) or a single value.
//                  Commonly used with Collectors.
    		     
    		        List<String> upperNames = list.stream()
    		            .map(String::toUpperCase)
    		            .collect(Collectors.toList());

    		        System.out.println(upperNames); 
//      2.foreach and foreachOrderd..as dicussced...
//    	3.reduce when u want to comboine result in single result like sum , avg...
    		        List<Integer> nums = List.of(1,2,5,3,4,8);
    		        System.out.println("sum of int list "+ nums.stream().reduce(Integer::sum));
    		        
//    		        count() for Counting...
//    		        this all are return boolean if true or false
    		        boolean any = list.stream().anyMatch(name -> name.startsWith("A")); 
    		        boolean all = list.stream().allMatch(name -> name.length() > 2); 
    		        boolean none = list.stream().noneMatch(name -> name.endsWith("z")); 

    		        System.out.println(any + ", " + all + ", " + none);
 System.out.println(list.stream().findAny());
 System.out.println(list.stream().findFirst());
 
// as last two  are short circuit method as soon as they find result they stop proccesing like ….no need to process further
//predicate means u want something based on condition use filter
//when  u want pass function like give something and u want something then use map
//when u want one value then use reduce
 
 String[] arr = list.stream().toArray(String[]::new);
 System.out.println(Arrays.toString(arr)); 
 
// Stateful and stateless==
//		 stateless means it do not know about the other veriable in steam like map map don’t know about other like in 1,2,3,4 if map in 3 then it do not know about other like 1, 2, 3 so it is called stateless
//		 and another is statefull like distict , sorted they have to know about others also so it is cal ed statefull

// patrallal stream 
 // A type of stream that enables parallel processing of elements
 // Allowing multiple threads to process parts of the stream simultaneously
 // This can significantly improve performance for large data sets
 // Workload is distributed across multiple threads
// we can do same thing in less time
 
// we can not parallal stream when we have depended opration to othe like pervius and all so we can use that their
 
// Collector
// 1. toset, tolist , tomap, to spesific collection
 Set<String> uniqueNames = list.stream()
         .collect(Collectors.toSet());
System.out.println(uniqueNames); 

LinkedList<String> linkedList = list.stream()
.collect(Collectors.toCollection(LinkedList::new));

System.out.println(linkedList); 
//3. joping with , or something
String joinedString = list.stream().map(x-> x.toUpperCase()).collect(Collectors.joining(","));
System.out.println(joinedString);


// summarizingInt , summarizintDouble , summarizingLong and like this for opration..
List<Integer> numbers = List.of(5, 10, 15, 20);

IntSummaryStatistics stats = numbers.stream()
                                   .collect(Collectors.summarizingInt(Integer::intValue));

System.out.println(stats);


System.out.println("Sum: " + stats.getSum());       
System.out.println("Average: " + stats.getAverage()); 
System.out.println("Min: " + stats.getMin());       
System.out.println("Max: " + stats.getMax());       
System.out.println("Count: " + stats.getCount());  

//ALSO WE CAN FIND SEPRATELY LIKE THIS
double avg = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
System.out.println(list.stream().collect(Collectors.groupingBy(String::length)));
System.out.println(list.stream().collect(Collectors.groupingBy(String::length,Collectors.counting())));
System.out.println(list.stream().collect(Collectors.groupingBy(String::length,Collectors.joining(","))));

    		        
//Grouping by length into TreeMap (sorted keys) + counting
TreeMap<Integer, Long> treeMap = list.stream()
     .collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));
System.out.println(treeMap);

//	  for group(condition whose based u want to group , 
//	  which type of map u want at end like (tree map , hashmap)
//	  after group u want to do anything with that all one by one group ,
//	  like this…
//	  threee param pass method

//	  partition : group based on condition
System.out.println(list.stream()
		.collect(Collectors.partitioningBy(word -> word.length() > 5)));
// this also have param method as group 
// Like same wise we have a primitive stream ...so as of now we have non primitive data typr stream...
// primitive datatype give stream in this type..
int[] num = {1,2,6,7};
IntStream intStream = Arrays.stream(num);
//IntStream is a interface so when we have primitve datatype output comes in this like INtStream , LongStream , DoubleSTream..like this instead Of Stream<INteger> and all…..
//.boxed () use for convert it into wrapper class…so we can do streaming Function on that and all
List<Integer> list1 = intStream.boxed().collect(Collectors.toList());

System.out.println(list1); // [1, 2, 3, 4, 5]


//Min by natural order (alphabetical)

//Max by natural order (alphabetical)
System.out.println(list.stream().max(Comparator.naturalOrder()));
System.out.println(list.stream().min(Comparator.naturalOrder()));
// we can use comparator if u want to custom ordering logic


	  }
}
