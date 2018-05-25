package cz.service;

import cz.domain.Student;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/students")
public class StudentManager {
    private static Logger logger = Logger.getLogger(StudentManager.class);
    private static int index = 1;
    private static Map<Integer, Student> studentList = new HashMap<>();
    
    public StudentManager() {
        if (studentList.size() == 0) {
            studentList.put(index, new Student(index++, "Frank", "CS"));
            studentList.put(index, new Student(index++, "Jersey", "Math"));
        }
    }
    
    @GET
    @Path("{studentid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student getMetadata(@PathParam("studentid") int studentid) {
        if (studentList.containsKey(studentid)) {
//            String s = JSONObject.fromObject(studentList.get(studentid)).toString();
//            System.out.println(s);
//            return s;
//            System.out.println(1);
            return studentList.get(studentid);
        } else {
//            return null;
            return new Student(0, "Nil", "Nil");
        }
    }
    
    @GET
    @Path("list")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getAllStudents() {
        List<Student> students = new ArrayList<Student>(studentList.values());
        String s = JSONArray.fromObject(students).toString();
        return s;
    }

//    @GET
//    @Path("list")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<Student> getAllStudents() {
//        List<Student> students = new ArrayList<Student>(studentList.values());
//        return students;
//    }
    
    @POST
    @Path("add")
    @Produces("text/plain")
    public String addStudent(@FormParam("name") String name,
                             @FormParam("dept") String dept) {
        studentList.put(index, new Student(index++, name, dept));
        return String.valueOf(index - 1);
    }
    
    @DELETE
    @Path("delete/{studentid}")
    @Produces("text/plain")
    public String removeStudent(@PathParam("studentid") int studentid) {
        logger.info("Receiving quest for deleting student: " + studentid);
        
        Student removed = studentList.remove(studentid);
        if (removed == null) return "failed!";
        else return "true";
    }
    
    @PUT
    @Path("put")
    @Produces("text/plain")
    public String putStudent(@QueryParam("studentid") int studentid,
                             @QueryParam("name") String name,
                             @QueryParam("dept") String dept
    ) {
        logger.info("Receiving quest for putting student: " + studentid);
        if (!studentList.containsKey(studentid))
            return "failed!";
        else
            studentList.put(studentid, new Student(studentid, name, dept));
        
        return String.valueOf(studentid);
    }
}