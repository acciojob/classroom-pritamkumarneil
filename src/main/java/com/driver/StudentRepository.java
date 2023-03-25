package com.driver;

import org.springframework.stereotype.Repository;
import springfox.documentation.service.AllowableRangeValues;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

@Repository
public class StudentRepository {
    //Student dataBase
    HashMap<String,Student> studentDb=new HashMap<>();
    //Teacher DataBase
    HashMap<String,Teacher> teacherDb=new HashMap<>();
    // teacher-Student Pair DataBase
    HashMap<String, HashSet<String>> studentTeacherDb=new HashMap<>();

    public void addStudent(Student student){
        String key=student.getName();
        studentDb.put(key,student);
    }
    public void addTeacher(Teacher teacher){
        String key=teacher.getName();
        teacherDb.put(key,teacher);
    }
    public void addStudentTeacherPair(String student,String teacher){
       if(studentTeacherDb.containsKey(teacher)){
           HashSet<String> set=studentTeacherDb.get(teacher);
           if(!set.contains(student)){
               set.add(student);
           }
       }else{
           HashSet<String> set=new HashSet<>();
           set.add(student);
           studentTeacherDb.put(teacher,set);
       }
    }
    public Student getStudentByName(String name){
        return studentDb.get(name);
    }
    public Teacher getTeacherByName(String name){
        return teacherDb.get(name);
    }
    public List<String> getStudentsByTeacherName(String name){
//        System.out.println(studentTeacherDb);
        if(studentTeacherDb.containsKey(name))
        return new ArrayList<>(studentTeacherDb.get(name));
        else return null;
    }
    public List<String> getAllStudents(){
        return new ArrayList<>(studentDb.keySet());
    }
    public void deleteTeacherByName(String name){
        HashSet<String> set=studentTeacherDb.get(name);
        studentTeacherDb.remove(name);
        teacherDb.remove(name);
        // if student also needed to remove from the list from student db then this block is needed
        for(String student: set){
            studentDb.remove(student);
        }
    }
    public void deleteAllTeacher(){
        // accessing all teacher from the teacher DataBase
        for(String teacher: teacherDb.keySet()){
            // removed teacher from teacherDataBase
            teacherDb.remove(teacher);
            // getting all students list by the teacher
            HashSet<String> set=studentTeacherDb.get(teacher);
            studentTeacherDb.remove(teacher);// removed student Teacher Pair;
            for(String student: set){
                // removing all students from student database taught by the teacher;
                if(studentDb.containsKey(student)) {
                    studentDb.remove(student);
                }
            }

        }
    }
}
