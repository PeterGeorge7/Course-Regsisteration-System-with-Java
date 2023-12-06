package classes;

import classes.CoursesList.CourseNode;
import classes.StudentsList.StudentNode;
import classes.helpers.ExceptionsHandler;

/**
 * Represents a sparse table for managing students and courses.
 */
public class SparseTable {
    private StudentsList studentsList;
    private CoursesList coursesList;

    /**
     * Constructor for creating a new SparseTable.
     */
    public SparseTable() {
        this.studentsList = new StudentsList();
        this.coursesList = new CoursesList();
    }

    // ! All Methods Handle Students ..
    /**
     * Adds a new student to the sparse table.
     *
     * @param studentId   The ID of the student.
     * @param studentName The name of the student.
     * @return The newly added student node or null if the ID already exists.
     */
    public StudentsList.StudentNode addNewStudent(int studentId, String studentName) {
        if (!validInput(studentId, studentName)) {
            return null;
        }

        StudentNode newStudent = studentsList.addNewStudent(studentId, studentName);
        if (newStudent == null) {
            ExceptionsHandler.handleIdExists("Student Id: " + studentId + " Already Exists");
            return null;
        }

        return newStudent;
    }

    /**
     * Deletes a student with the given ID from the sparse table.
     *
     * @param studentId The ID of the student to be deleted.
     * @return The deleted student node or null if the student was not found.
     */
    public StudentsList.StudentNode deleteStudentById(int studentId) {
        if (!validInput(studentId)) {
            return null;
        }

        StudentNode student = getStudentById(studentId);
        if (student == null) {
            return null;
        }

        CellData currentCell = student.getFirsCellDataCourses();
        int courseId;
        while (currentCell != null) {
            courseId = currentCell.getCourse().getCourseId();
            unrollStudent(studentId, courseId);
            currentCell = currentCell.getNextColumnCellData();
        }

        StudentNode deletedStudent = studentsList.deleteStudentWithId(studentId);
        if (deletedStudent == null) {
            ExceptionsHandler.handleIdNotFound("Student Id: " + studentId + " Not Found, Can't Delete it");
            return null;
        }
        return deletedStudent;
    }

    /**
     * Gets the student node with the given ID from the sparse table.
     *
     * @param studentId The ID of the student.
     * @return The student node with the given ID or null if not found.
     */
    public StudentsList.StudentNode getStudentById(int studentId) {
        if (!validInput(studentId)) {
            return null;
        }

        StudentNode returendStudnet = studentsList.getStudentWithId(studentId);
        if (returendStudnet == null) {
            ExceptionsHandler.handleIdNotFound("Student Id: " + studentId + " Not Found, Can't Reach it");
            return null;
        }
        return returendStudnet;
    }

    /**
     * Displays the list of students in the sparse table.
     */
    public boolean displayStudents() {
        return studentsList.displayStudentList();
    }

    // ! All Methods Handle Course ..
    /**
     * Adds a new course to the sparse table.
     *
     * @param courseId   The ID of the course.
     * @param courseName The name of the course.
     * @return The newly added course node or null if the ID already exists.
     */
    public CoursesList.CourseNode addNewCourse(int courseId, String courseName) {
        if (!validInput(courseId, courseName)) {
            return null;
        }

        CourseNode newCourse = coursesList.addNewCourse(courseId, courseName);
        if (newCourse == null) {
            ExceptionsHandler.handleIdExists("Course Id: " + courseId + " Already Exists");
            return null;
        }
        return newCourse;
    }

    /**
     * Gets the course node with the given ID from the sparse table.
     *
     * @param courseId The ID of the course.
     * @return The course node with the given ID or null if not found.
     */
    public CoursesList.CourseNode getCourseById(int courseId) {
        if (!validInput(courseId)) {
            return null;
        }

        CourseNode returnedCourse = coursesList.getCourseWithId(courseId);
        if (returnedCourse == null) {
            ExceptionsHandler.handleIdNotFound("course Id:" + courseId + " Not Found, Can't Reach it");
            returnedCourse = null;
        }

        return returnedCourse;
    }

    /**
     * Deletes a course with the given ID from the sparse table.
     *
     * @param courseId The ID of the course to be deleted.
     * @return The deleted course node or null if the course was not found.
     */
    public CoursesList.CourseNode deleteCourseById(int courseId) {
        if (!validInput(courseId)) {
            return null;
        }

        CourseNode course = getCourseById(courseId);
        if (course == null) {
            return null;
        }

        CellData currentCell = course.getFirstCellDataStudents();
        int studentId;
        while (currentCell != null) {
            studentId = currentCell.getStudent().getStudentId();
            unrollStudent(studentId, courseId);
            currentCell = currentCell.getNextRowCellData();
        }

        CourseNode deletedCourse = coursesList.deleteCourseWithId(courseId);
        if (deletedCourse == null) {
            ExceptionsHandler.handleIdNotFound("Course Id: " + courseId + " Can't be deleted");
        }
        return deletedCourse;
    }

    /**
     * Displays the list of courses in the sparse table.
     */
    public boolean displayCourses() {
        return coursesList.displayCourseList();
    }

    // !! mutual between delete student and delete course

    /**
     * Adds a student to a course and creates a cell in the sparse table.
     *
     * @param studentId The ID of the student.
     * @param courseId  The ID of the course.
     * @return The created cell data or null if the operation failed.
     */
    public CellData enrollStudentInCourse(int studentId, int courseId) {
        StudentNode student = getStudentById(studentId);
        CourseNode course = getCourseById(courseId);

        if (student == null) {
            ExceptionsHandler
                    .handleIdNotFound("STUDENT_NOT_FOUND");
            return null;
        }

        if (course == null) {
            ExceptionsHandler
                    .handleIdNotFound("COURSE_NOT_FOUND");
            return null;
        }

        if (courseAlreadyHaveStudent(courseId, studentId)) {
            ExceptionsHandler
                    .handleIdExists("Student Id: " + studentId + " Already Enrolled in that course: " + courseId);

            return null;
        }

        CellData cellData = new CellData(student, course);

        connectCellData(student, course, cellData);

        return cellData;
    }

    private void connectCellData(StudentNode student, CourseNode course, CellData cellData) {
        cellData.setNextRowCellData(course.getFirstCellDataStudents());
        cellData.setNextColumnCellData(student.getFirsCellDataCourses());
        student.setFirsCellDataCourses(cellData);
        course.setFirstCellDataStudents(cellData);
    }

    /**
     * unroll Student From Courses
     * 
     * @param studentId The ID of the student.
     * @param courseId  The ID of the course.
     * @return true if it safely removed and false if it not removed
     */
    public boolean unrollStudent(int studentId, int courseId) {
        if (!validInput(studentId) || !validInput(courseId)) {
            return false;
        }

        CourseNode course = getCourseById(courseId);
        if (course == null || !courseAlreadyHaveStudent(courseId, studentId)) {
            ExceptionsHandler.handleStudentNotEnrolledError(studentId, courseId);
            return false;
        }

        CellData firstCellData = course.getFirstCellDataStudents();
        if (firstCellData.getStudent().getStudentId() == studentId) {
            course.setFirstCellDataStudents(firstCellData.getNextRowCellData());
        } else {
            CellData prevCellData = findPrevCellDataForStudent(firstCellData, studentId);
            if (prevCellData.getNextRowCellData() != null) {
                prevCellData.setNextRowCellData(prevCellData.getNextRowCellData().getNextRowCellData());
            }
        }

        removeCourseFromStudent(courseId, studentId);
        return true;
    }

    /**
     * Find the CellData for a specific student in a course.
     * usage at delete cell this gets me the previous because its an SLL
     * 
     * @param startCellData The starting CellData in the course.
     * @param studentId     The ID of the student.
     * @return The CellData for the student or null if not found.
     */
    private CellData findPrevCellDataForStudent(CellData startCellData, int studentId) {
        CellData currentCellData = startCellData;
        while (currentCellData.getNextRowCellData() != null
                && currentCellData.getNextRowCellData().getStudent().getStudentId() != studentId) {
            currentCellData = currentCellData.getNextRowCellData();
        }
        return currentCellData;
    }

    /**
     * Remove Course from a specfic Student
     * 
     * @param courseId  The ID of the course.
     * @param studentId The ID of the student.
     * @return true if it safely removed and false if it not removed
     */
    private boolean removeCourseFromStudent(int courseId, int studentId) {
        if (!validInput(studentId) || !validInput(courseId)) {
            return false;
        }

        StudentNode student = getStudentById(studentId);
        if (student == null || !studentAlreadyHaveCourse(studentId, courseId)) {
            ExceptionsHandler.handleStudentNotEnrolledError(studentId, courseId);
            return false;
        }

        CellData firstCellData = student.getFirsCellDataCourses();
        if (firstCellData.getCourse().getCourseId() == courseId) {
            student.setFirsCellDataCourses(firstCellData.getNextColumnCellData());
        } else {
            CellData prevCellData = findPrevCellDataForCourse(firstCellData, courseId);
            if (prevCellData.getNextColumnCellData() != null) {
                prevCellData.setNextColumnCellData(prevCellData.getNextColumnCellData().getNextColumnCellData());
            }
        }
        return true;
    }

    /**
     * Find the CellData for a specific student in a course.
     *
     * @param startCellData The starting CellData in the course.
     * @param studentId     The ID of the student.
     * @return The CellData for the student or null if not found.
     */
    private CellData findPrevCellDataForCourse(CellData startCellData, int courseId) {
        CellData currentCellData = startCellData;
        while (currentCellData.getNextColumnCellData() != null
                && currentCellData.getNextColumnCellData().getCourse().getCourseId() != courseId) {
            currentCellData = currentCellData.getNextColumnCellData();
        }
        return currentCellData;
    }

    /**
     * Checks if a course already has a specific student enrolled.
     *
     * @param courseId  The ID of the course.
     * @param studentId The ID of the student.
     * @return True if the student is already enrolled, false otherwise.
     */
    public boolean courseAlreadyHaveStudent(int courseId, int studentId) {
        CourseNode course = getCourseById(courseId);
        CellData studentsToCheckForExists = course.getFirstCellDataStudents();

        while (studentsToCheckForExists != null
                && studentsToCheckForExists.getStudent().getStudentId() != studentId) {
            studentsToCheckForExists = studentsToCheckForExists.getNextRowCellData();
        }

        return (studentsToCheckForExists != null && studentsToCheckForExists.getStudent().getStudentId() == studentId);
    }

    /**
     * Checks if a student already has a specific course enrolled.
     *
     * @param studentId The ID of the student.
     * @param courseId  The ID of the course.
     * @return True if the student is already enrolled, false otherwise.
     */
    public boolean studentAlreadyHaveCourse(int studentId, int courseId) {
        StudentNode student = getStudentById(studentId);
        CellData coursesToCheckForExists = student.getFirsCellDataCourses();

        while (coursesToCheckForExists != null
                && coursesToCheckForExists.getCourse().getCourseId() != courseId) {
            coursesToCheckForExists = coursesToCheckForExists.getNextColumnCellData();
        }

        return (coursesToCheckForExists != null && coursesToCheckForExists.getCourse().getCourseId() == courseId);
    }

    /**
     * Displays the students enrolled in a specific course.
     *
     * @param courseId The ID of the course.
     */
    public boolean displayStudentsOfCourse(int courseId) {
        CourseNode course = getCourseById(courseId);

        if (course == null) {
            return false;
        }

        CellData currentCell = course.getFirstCellDataStudents();

        System.out.println("+------------+----------------------+");
        // Display table header
        System.out.printf("| %-10s | %-20s |%n", "StudentID", "StudentName");
        System.out.println("+------------+----------------------+");
        System.out.println("Students that Enrolled in " + course.getCourseName() + ":");

        StudentNode student;

        while (currentCell != null) {
            student = currentCell.getStudent();
            System.out.printf("| %-10s | %-20s |%n", student.getStudentId(), student.getStudentName());
            currentCell = currentCell.getNextRowCellData();
        }

        System.out.println("+------------+----------------------+");
        return true;
    }

    /**
     * Displays the courses enrolled by a specific student.
     *
     * @param studentId The ID of the student.
     */
    public boolean displayCoursesOfStudent(int studentId) {
        StudentNode student = getStudentById(studentId);

        if (student == null) {
            return false;
        }

        CellData currentCell = student.getFirsCellDataCourses();

        System.out.println("+------------+----------------------+");
        System.out.printf("| %-10s | %-20s |%n", "CourseID", "CourseName");
        System.out.println("+------------+----------------------+");

        System.out.println("Courses that Enrolled by " + student.getStudentName() + " :");

        CourseNode course;

        while (currentCell != null) {
            course = currentCell.getCourse();
            System.out.printf("| %-10s | %-20s |%n", course.getCourseId(), course.getCourseName());
            currentCell = currentCell.getNextColumnCellData();
        }

        System.out.println("+------------+----------------------+");
        return true;
    }

    /**
     * Checks if the given integer ID is valid.
     *
     * @param id The ID to be validated.
     * @return true if the ID is valid, false otherwise.
     */
    private boolean validInput(int id) {
        if (id <= 0) {
            ExceptionsHandler.handleInvalidInput("Invalid ID , Must be more than 0");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the given string name is valid.
     *
     * @param name The name to be validated.
     * @return true if the name is valid, false otherwise.
     */
    private boolean validInput(int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            ExceptionsHandler.handleInvalidInput("Invalid Name , Must be non-empty and not null");
            return false;
        }

        return validInput(id);
    }
}