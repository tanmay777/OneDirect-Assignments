package com.company;

import com.oracle.tools.packager.Log;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Student> students = new ArrayList<Student>();

        students = loadStudents();

        int response;
        while (true) {
            System.out.println();
            System.out.println("1. Add User details.");
            System.out.println("2. Display User details.");
            System.out.println("3. Delete User details");
            System.out.println("4. Save User details.");
            System.out.println("5. Exit");
            response = sc.nextInt();
            switch (response) {
                case 1:
                    Student student = addUserDetails(students);
                    students.add(student);
                    students.sort(Comparator.comparing(Student::mGetFullName));
                    break;
                case 2:
                    displayUserDetails(students);
                    break;
                case 3:
                    students = deleteUserDetails(students);
                    break;
                case 4:
                    saveUserDetails(students);
                    break;
                case 5:
                    endProgram(students);
                    //System.exit(0) is used in above function
                    break;
                default:
                    System.out.println("Invalid Input. Please Enter Again");
            }
        }
    }

    private static Student addUserDetails(List<Student> students) {
        Scanner sc = new Scanner(System.in);

        Student student = new Student();

        while (true) {
            System.out.println("Enter Full Name");
            String fullName = sc.nextLine();
            if (!fullName.isEmpty()) {
                student.mSetFullName(fullName);
                break;
            }
            System.out.println("Please Enter Valid Full Name");
        }

        while (true) {
            System.out.println("Enter Age");
            int age = sc.nextInt();
            sc.nextLine();
            //nextInt() does not consume EOL (end-of-line) character(s), so you do need nextLine() to clear the input buffer. Else
            //in next loop the first nextLine() won't work
            if (age > 0) {
                student.mSetAge(age);
                break;
            }
            System.out.println("Please Enter Valid Age");
        }

        while (true) {
            System.out.println("Enter Address");
            String address = sc.nextLine();
            if (!address.isEmpty()) {
                student.mSetAddress(address);
                break;
            }
            System.out.println("Please Enter Valid Address");
        }

        boolean rollNoFlag = true;
        while (rollNoFlag) {
            rollNoFlag = false;
            System.out.println("Enter Roll no.");
            int rollNo = sc.nextInt();
            sc.nextLine();
            //nextInt() does not consume EOL (end-of-line) character(s), so you do need nextLine() to clear the input buffer. Else
            //in next loop the first nextLine() won't work
            if (rollNo > 0) {
                for (int i = 0; i < students.size(); i++)
                    if (students.get(i).mGetRollNo() == rollNo) {
                        System.out.println("This roll no. already exists. Please enter another one.");
                        rollNoFlag = true;
                        break;
                    }
            } else {
                System.out.println("Roll no should be greater than 0");
                rollNoFlag = true;
            }
            if (!rollNoFlag) student.mSetRollNo(rollNo);
        }

        while (true) {
            //Input to be entered as A B C D or ABCDE
            System.out.println("Enter Courses.");
            String courses = sc.nextLine();
            int char_count = 0;
            for (int i = 0; i < courses.length(); i++) {
                if ((courses.charAt(i) < 'A' && courses.charAt(i) != ' ') || (courses.charAt(i) > 'F' && courses.charAt(i) != ' ')) {
                    System.out.println("Valid Courses are A B C D E F");
                    break;
                } else if (courses.charAt(i) == ' ')
                    ;
                else
                    char_count++;
            }
            if (char_count < 4 || char_count > 6)
                System.out.println("Courses to be chosen should be more than 3 and less than 6");
            else {
                student.mSetCourses(courses);
                break;
            }
            //TODO: Add Course Repetition Check case
        }
        return student;
    }

    private static void displayUserDetails(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No Data Available");
            return;
        }
        Scanner sc = new Scanner(System.in);
        int response;
        char asc;
        while (true) {
            System.out.println("Sort the result on the basis of \n1. Name \n2. Roll No \n3. Age \n4. Address");
            response = sc.nextInt();
            sc.nextLine();
            if (response > 0 && response < 5) {
                break;
            }
            System.out.println("Please Enter Valid option");
        }

        while (true) {
            System.out.println("Result should be ascending (y/n)");
            asc = sc.nextLine().charAt(0);
            if (asc != 'y' && asc != 'n') {
                System.out.println("Valid input is only y and n");
                System.out.println();
            } else
                break;
        }

        switch (response) {
            case 1:
                students.sort(Comparator.comparing(Student::mGetFullName));
                break;
            case 2:
                students.sort(Comparator.comparing(Student::mGetRollNo));
                break;
            case 3:
                students.sort(Comparator.comparing(Student::mGetAge));
                break;
            case 4:
                students.sort(Comparator.comparing(Student::mGetAddress));
                break;
        }

        if (asc == 'n') {
            Collections.reverse(students);
        }
        //we don't need else case as it will be asc by default


        System.out.println("----------------------------------------------------------");
        System.out.println("Full Name \t Roll Number \t Age \t Address \t Courses");
        System.out.println("----------------------------------------------------------");
        for (int i = 0; i < students.size(); i++) {
            System.out.print(students.get(i).mGetFullName() + "\t");
            System.out.print(students.get(i).mGetRollNo() + "\t");
            System.out.print(students.get(i).mGetAge() + "\t");
            System.out.print(students.get(i).mGetAddress() + "\t");
            System.out.println(students.get(i).mGetCourses() + "\t");
            System.out.println();
        }
        //TODO: Improve the display of content.
    }


    private static List<Student> deleteUserDetails(List<Student> students) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the student's roll no. whose record has to be deleted");
        int rollNo = sc.nextInt();
        boolean flag = true;
        for (Iterator<Student> iterator = students.listIterator(); iterator.hasNext(); ) {
            Student student = iterator.next();
            if (student.mGetRollNo() == rollNo) {
                iterator.remove();
                System.out.println("Student data deleted");
                flag = false;
            }
        }
        if (flag) {
            System.out.println("No Student with this record exists");
        }
        return students;
    }

    private static void saveUserDetails(List<Student> students) {
        try {
            File studentDataFile = new File("student_info.txt");
            studentDataFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream fileOutputStream = new FileOutputStream(studentDataFile, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (int i = 0; i < students.size(); i++) {
                objectOutputStream.writeObject(students.get(i));
            }
            System.out.println("Data Saved");
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.debug("File Not Found");
        } catch (IOException e) {
            Log.debug("Error initializing stream");
        }
    }

    private static void endProgram(List<Student> students) {
        Scanner sc = new Scanner(System.in);
        char response;
        while (true) {
            System.out.println("Do you want to enter details of any other item (y/n)");
            response = sc.nextLine().charAt(0);
            if (response != 'y' && response != 'n') {
                System.out.println("Valid input is only y and n");
                System.out.println();
            } else
                break;
        }

        if (response == 'y') {
            try {
                File studentDataFile = new File("student_info.txt");
                studentDataFile.createNewFile(); // if file already exists will do nothing
                FileOutputStream fileOutputStream = new FileOutputStream(studentDataFile);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                for (int i = 0; i < students.size(); i++) {
                    objectOutputStream.writeObject(students.get(i));
                }
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                Log.debug("File Not Found");
            } catch (IOException e) {
                Log.debug("Error initializing stream");
            }
            System.exit(0);
        } else {
            //Case when n is pressed
            System.exit(0);
        }


    }

    private static List<Student> loadStudents() {
        List<Student> students = new ArrayList<Student>();
        try {
            File studentDataFile = new File("student_info.txt");
            studentDataFile.createNewFile(); // if file already exists will do nothing
            FileInputStream fileInputStream = new FileInputStream(studentDataFile);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            while (true) {
                try {
                    Student student = (Student) objectInputStream.readObject();
                    students.add(student);
                } catch (EOFException e) {
                    objectInputStream.close();
                    fileInputStream.close();
                    return students;
                } catch (ClassNotFoundException e) {
                    Log.debug("Class not found Exception");
                }
            }
        } catch (FileNotFoundException e) {
            Log.debug("File Not Found");
        } catch (IOException e) {
            Log.debug("Error initializing stream");
        }

        return students;
    }
}

//TODO: Confirm whether its a good practice to create Scanner again and again for every new local block.
