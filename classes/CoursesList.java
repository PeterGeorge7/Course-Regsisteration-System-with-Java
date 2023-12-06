package classes;

public class CoursesList {
    private CourseNode firstCourseInList;

    public class CourseNode {
        private int courseId;
        private String courseName;
        private CourseNode prevCourse;
        private CourseNode nextCourse;
        private CellData firstCellDataStudents;

        CourseNode(int courseId, String courseName) {
            this.courseId = courseId;
            this.courseName = courseName;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public void setNextCourse(CourseNode nextCourse) {
            this.nextCourse = nextCourse;
        }

        public void setPrevCourse(CourseNode prevCourse) {
            this.prevCourse = prevCourse;
        }

        public void setFirstCellDataStudents(CellData firstCellDataStudents) {
            this.firstCellDataStudents = firstCellDataStudents;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public CellData getFirstCellDataStudents() {
            return firstCellDataStudents;
        }

        public CourseNode getNextCourse() {
            return nextCourse;
        }

        public CourseNode getPrevCourse() {
            return prevCourse;
        }
    }

    public CoursesList() {
        this.firstCourseInList = null;
    }

    /**
     * Adds a new course to the list.
     *
     * @param courseId   The ID of the course.
     * @param courseName The name of the course.
     * @return The newly added course node or null if the ID already exists.
     */
    public CourseNode addNewCourse(int courseId, String courseName) {
        CourseNode newCourse = new CourseNode(courseId, courseName);
        if (isCourseListEmpty()) {
            this.firstCourseInList = newCourse;
            // System.out.println("New Course Added Succfully");
            return newCourse;
        }

        if (isCourseIdExists(courseId)) {
            return null;
        }

        this.firstCourseInList.setPrevCourse(newCourse);
        newCourse.setNextCourse(this.firstCourseInList);
        this.firstCourseInList = newCourse;
        // System.out.println("New Course Added Succfully");
        return newCourse;

    }

    /**
     * Gets the course node with the given ID.
     *
     * @param courseId The ID of the course.
     * @return The course node with the given ID or null if not found.
     */
    public CourseNode getCourseWithId(int courseId) {
        if (isCourseListEmpty()) {
            return null;
        }

        if (!isCourseIdExists(courseId)) {
            return null;
        }

        CourseNode currentCourse = this.firstCourseInList;

        while (currentCourse != null && currentCourse.getCourseId() != courseId) {
            currentCourse = currentCourse.getNextCourse();
        }
        if (currentCourse == null) {
            return null;
        }
        return currentCourse;

    }

    /**
     * Deletes a course with the given ID from the list.
     *
     * @param courseId The ID of the course to be deleted.
     * @return The deleted course node or null if the course was not found.
     */
    public CourseNode deleteCourseWithId(int courseId) {
        CourseNode courseWithId = getCourseWithId(courseId);

        if (courseWithId == null) {
            return null;
        }

        CourseNode prevNode = courseWithId.getPrevCourse();
        CourseNode nextNode = courseWithId.getNextCourse();

        if (prevNode != null) {
            prevNode.setNextCourse(nextNode);
        } else {
            // If there is no previous node, it means the course to be deleted is the first
            // in the list
            this.firstCourseInList = nextNode;
        }

        if (nextNode != null) {
            nextNode.setPrevCourse(prevNode);
        }
        // System.out.println("Course Has deleted Succfullly");
        return courseWithId;

    }

    /**
     * Checks if the course list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    public boolean isCourseListEmpty() {
        return (this.firstCourseInList == null);
    }

    /**
     * Checks if a course with the given ID exists in the list.
     *
     * @param courseId The ID of the course.
     * @return True if a course with the ID exists, false otherwise.
     */
    public boolean isCourseIdExists(int courseId) {
        if (isCourseListEmpty()) {
            return false;
        } else {
            CourseNode courseWithSameId = this.firstCourseInList;
            for (; courseWithSameId.getCourseId() != courseId
                    && courseWithSameId.getNextCourse() != null; courseWithSameId = courseWithSameId
                            .getNextCourse())
                ;
            return (courseWithSameId.getCourseId() == courseId);
        }
    }

    /**
     * Displays the list of courses in a formatted table.
     */
    public boolean displayCourseList() {
        CourseNode current = this.firstCourseInList;
        if (current == null) {
            return false;
        }
        // Display top border
        System.out.println("+------------+----------------------+");
        // Display table header
        System.out.printf("| %-10s | %-20s |%n", "ID", "Name");
        // Display header and data separator
        System.out.println("+------------+----------------------+");

        // Display each course's information in a formatted manner
        while (current != null) {
            System.out.printf("| %-10s | %-20s |%n", current.getCourseId(), current.getCourseName());
            current = current.getNextCourse();
        }

        // Display bottom border
        System.out.println("+------------+----------------------+");
        return true;
    }

}