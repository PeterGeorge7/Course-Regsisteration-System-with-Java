package classes;

/**
 * Represents a list of students and provides methods to manipulate the list.
 */
public class StudentsList {
    // The first student in the list
    private StudentNode firstStudentInList;

    public StudentNode getFirstStudentInList() {
        return firstStudentInList;
    }

    /**
     * Represents a node in the student list.
     */
    public class StudentNode {
        // Student details
        private int studentId;
        private String studentName;
        private StudentNode nextStudent;
        private StudentNode prevStudent;
        private CellData firsCellDataCourses;

        // Constructor for creating a new student node
        StudentNode(int studentId, String studentName) {
            this.studentId = studentId;
            this.studentName = studentName;
        }

        // gettes and setters to access private data

        public void setNextStudent(StudentNode nexStudentNode) {
            this.nextStudent = nexStudentNode;
        }

        public void setFirsCellDataCourses(CellData firsCellDataCourses) {
            this.firsCellDataCourses = firsCellDataCourses;
        }

        public void setPrevStudent(StudentNode prevStudentNode) {
            this.prevStudent = prevStudentNode;
        }

        public int getStudentId() {
            return studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public StudentNode getNextStudent() {
            return nextStudent;
        }

        public CellData getFirsCellDataCourses() {
            return firsCellDataCourses;
        }

        public StudentNode getPrevStudent() {
            return prevStudent;
        }
    }

    // Constructor for creating a new StudentsList
    public StudentsList() {
        this.firstStudentInList = null;
    }

    /**
     * Adds a new student to the list.
     *
     * @param studentId   The ID of the student.
     * @param studentName The name of the student.
     * @return The newly added student node or null if the ID already exists.
     */
    public StudentNode addNewStudent(int studentId, String studnetName) {

        StudentNode newStudent = new StudentNode(studentId, studnetName);
        if (isStudentListEmpty()) {
            this.firstStudentInList = newStudent;
            // System.out.println("new student Added Succefully");
            return newStudent;
        }

        if (isStudentIdExists(studentId)) {
            return null;
        }

        newStudent.setNextStudent(this.firstStudentInList);
        this.firstStudentInList.setPrevStudent(newStudent);
        this.firstStudentInList = newStudent;
        // System.out.println("new student Added Succefully");
        return newStudent;

    }

    /**
     * Gets the student node with the given ID.
     *
     * @param studentId The ID of the student.
     * @return The student node with the given ID or null if not found.
     */
    public StudentNode getStudentWithId(int studentId) {
        if (!isStudentIdExists(studentId)) {
            return null;
        }

        StudentNode current = this.firstStudentInList;
        StudentNode studentWithid = null;
        while (current != null && current.getStudentId() != studentId) {
            current = current.getNextStudent();
        }
        if (current.getStudentId() == studentId) {
            studentWithid = current;
        }
        return studentWithid;
    }

    /**
     * Deletes a student with the given ID from the list.
     *
     * @param studentId The ID of the student to be deleted.
     * @return The deleted student node or null if the student was not found.
     */
    public StudentNode deleteStudentWithId(int studentId) {

        StudentNode studentWithId = getStudentWithId(studentId);
        if (studentWithId == null) {
            return null;
        }

        StudentNode prevNode = studentWithId.getPrevStudent();
        StudentNode nextNode = studentWithId.getNextStudent();
        if (prevNode != null) {
            prevNode.setNextStudent(nextNode);
        } else {
            // If there is no previous node, it means the student to be deleted is the first
            // in the list
            firstStudentInList = nextNode;
        }

        if (nextNode != null) {
            nextNode.setPrevStudent(prevNode);
        }
        // System.out.println("Student Deleted Succfully");
        return studentWithId;
    }

    /**
     * Checks if the student list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    public boolean isStudentListEmpty() {
        return (this.firstStudentInList == null);
    }

    /**
     * Checks if a student with the given ID exists in the list.
     *
     * @param studentId The ID of the student.
     * @return True if a student with the ID exists, false otherwise.
     */
    public boolean isStudentIdExists(int studentId) {
        if (isStudentListEmpty()) {
            return false;
        }

        StudentNode studentWithSameId = this.firstStudentInList;
        for (; studentWithSameId.getStudentId() != studentId
                && studentWithSameId.getNextStudent() != null; studentWithSameId = studentWithSameId
                        .getNextStudent())
            ;
        return (studentWithSameId.getStudentId() == studentId);

    }

    /**
     * Displays the list of students in a formatted table.
     */
    public boolean displayStudentList() {
        StudentNode current = this.firstStudentInList;
        if (current == null) {
            return false;
        }

        // Display top border
        System.out.println("+------------+----------------------+");
        // Display table header
        System.out.printf("| %-10s | %-20s |%n", "StudentID", "StudentName");
        // Display header and data separator
        System.out.println("+------------+----------------------+");

        // Display each student's information in a formatted manner
        while (current != null) {
            System.out.printf("| %-10s | %-20s |%n", current.getStudentId(), current.getStudentName());
            current = current.getNextStudent();
        }

        // Display bottom border
        System.out.println("+------------+----------------------+");
        return true;
    }

}
