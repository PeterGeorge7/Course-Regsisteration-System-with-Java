package classes;

import classes.CoursesList.CourseNode;
import classes.StudentsList.StudentNode;

public class CellData {
    private StudentNode student;
    private CourseNode course;
    private CellData nextRowCellData;
    private CellData nextColumnCellData;

    public CellData(StudentNode student, CourseNode course) {
        this.student = student;
        this.course = course;
    }

    public void setCourse(CourseNode course) {
        this.course = course;
    }

    public void setNextColumnCellData(CellData nextColumnCellData) {
        this.nextColumnCellData = nextColumnCellData;
    }

    public void setNextRowCellData(CellData nextRowCellData) {
        this.nextRowCellData = nextRowCellData;
    }

    public void setStudentId(StudentNode student) {
        this.student = student;
    }

    public CourseNode getCourse() {
        return course;
    }

    public CellData getNextColumnCellData() {
        return nextColumnCellData;
    }

    public CellData getNextRowCellData() {
        return nextRowCellData;
    }

    public StudentNode getStudent() {
        return student;
    }
}