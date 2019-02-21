//package com.sanwin.task;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.sanwin.task.mapper.TaskMapper;
//import com.sanwin.task.model.Task;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TaskApplicationTests {
//	
//	@Autowired
//    private TaskMapper taskMapper;
//
//	/*
//	 * @Test public void contextLoads() { }
//	 */
//	
//	@Test
//    public void findAllTask() {
//        List<Task> task = taskMapper.findAllTask();
//        assertNotNull(task);
//        assertTrue(!task.isEmpty());
//    }
//    @Test
//    public void findTaskById() {
//        Task task = taskMapper.getTaskById(1L);
//        assertNotNull(task);
//    }
//    @Test
//    public void createTask() {
//        Task task = new Task();
//        task.setName("TODO");
//        task.setStatus(0);
//        taskMapper.insertTask(task);
//        Task newTask = taskMapper.getTaskById(task.getId());
//        assertEquals("TODO", task.getName());
//        assertEquals(new Integer(0), newTask.getStatus());
//    }
//
//}
