import classes.CellData;
import classes.SparseTable;
import classes.CoursesList.CourseNode;
import classes.StudentsList.StudentNode;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrationSystemGUI extends Application {

    private SparseTable registrationSystem;
    private BorderPane mainPageContent;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        registrationSystem = new SparseTable();
        for (int i = 220; i <= 320; i++) {
            registrationSystem.addNewStudent(i, "Student" + i);
        }
        for (int i = 1001; i <= 1101; i++) {
            registrationSystem.addNewCourse(i, "Course" + i);
        }
        registrationSystem.enrollStudentInCourse(220, 1001);

        primaryStage.setTitle("Registration System");

        mainPageContent = createMainPageContent();

        Scene scene = new Scene(mainPageContent, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the main page content.
     *
     * @return The main page content.
     */
    private BorderPane createMainPageContent() {
        BorderPane borderPane = new BorderPane();

        TabPane tabPane = new TabPane();
        Tab addStudentTab = createAddStudentTab();
        Tab addCourseTab = createAddCourseTab();
        Tab showDataTab = createShowDataTab();
        Tab enrollStudentTab = createEnrollStudentTab();
        Tab showCoursesByStudentTab = createShowCoursesByStudentTab();
        Tab showStudentsByCourseTab = createShowStudentsByCourseTab();
        Tab deleteStudentTab = createDeleteStudentTab();
        Tab deleteCourseTab = createDeleteCourseTab();
        Tab removeStudentTab = createRemoveStudentTab();
        tabPane.getTabs().addAll(addStudentTab, addCourseTab, showDataTab, enrollStudentTab, showCoursesByStudentTab,
                showStudentsByCourseTab, deleteStudentTab, deleteCourseTab, removeStudentTab);

        borderPane.setCenter(tabPane);

        return borderPane;
    }
    
    // Create a tab for adding students
    private Tab createAddStudentTab() {
        Tab tab = new Tab("Add Student");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");
        TextField studentNameField = new TextField();
        studentNameField.setPromptText("Student Name");

        Button addStudentButton = new Button("Add Student");
        addStudentButton.setOnAction(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText().trim().trim());
                String studentName = studentNameField.getText().trim().trim();
                StudentNode newStudent = registrationSystem.addNewStudent(studentId, studentName);
                if (newStudent != null) {
                    showAlert("Succfully", "New Student Added Succfully.");
                } else {
                    showAlert("Error Occured while creating new student");

                }
                clearFields(studentIdField, studentNameField);
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for Student ID");
            }
        });

        vBox.getChildren().addAll(studentIdField, studentNameField, addStudentButton);
        tab.setContent(vBox);
        return tab;
    }

    // Create a tab for adding courses
    private Tab createAddCourseTab() {
        Tab tab = new Tab("Add Course");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Course ID");
        TextField courseNameField = new TextField();
        courseNameField.setPromptText("Course Name");

        Button addCourseButton = new Button("Add Course");
        addCourseButton.setOnAction(e -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText().trim().trim());
                String courseName = courseNameField.getText().trim().trim();
                CourseNode newCourse = registrationSystem.addNewCourse(courseId, courseName);
                if (newCourse != null) {
                    showAlert("Succfully", "New Course Added Succfully.");
                } else {
                    showAlert("Error occurd While Add New Course");
                }
                clearFields(courseIdField, courseNameField);
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for Course ID");
            }
        });

        vBox.getChildren().addAll(courseIdField, courseNameField, addCourseButton);
        tab.setContent(vBox);
        return tab;
    }

    // Create a tab for showing data
    private Tab createShowDataTab() {
        Tab tab = new Tab("Show Data");

        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(20));
        hBox.setAlignment(Pos.CENTER);

        Button showStudentsButton = new Button("Show Students");
        showStudentsButton.setPrefSize(150, 50);
        showStudentsButton.setOnAction(e -> {
            boolean thereIsStudentsToShow = registrationSystem.displayStudents();
            if (thereIsStudentsToShow == false) {
                showAlert("no students to show");
            }
        });

        Button showCoursesButton = new Button("Show Courses");
        showCoursesButton.setPrefSize(150, 50); // Set button size
        showCoursesButton.setOnAction(e -> {
            boolean thereIsCoursesToShow = registrationSystem.displayCourses();
            if (thereIsCoursesToShow == false) {
                showAlert("no students to show");
            }
        });

        hBox.getChildren().addAll(showStudentsButton, showCoursesButton);
        tab.setContent(hBox);
        return tab;
    }

    // Create a tab for enrolling students in courses
    private Tab createEnrollStudentTab() {
        Tab tab = new Tab("Enroll Student");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        TextField enrollStudentIdField = new TextField();
        enrollStudentIdField.setPromptText("Student ID");
        TextField enrollCourseIdField = new TextField();
        enrollCourseIdField.setPromptText("Course ID");

        Button enrollStudentButton = new Button("Enroll Student");
        enrollStudentButton.setOnAction(e -> {
            try {
                int studentId = Integer.parseInt(enrollStudentIdField.getText().trim().trim());
                int courseId = Integer.parseInt(enrollCourseIdField.getText().trim().trim());
                CellData newCellData = registrationSystem.enrollStudentInCourse(studentId, courseId);
                if (newCellData != null) {
                    showAlert("Succfully", "Student: " + studentId + " Added Sucffuly to course: " + courseId);
                } else {
                    showAlert("Error Occured While Add This student To course");
                }
                clearFields(enrollStudentIdField, enrollCourseIdField);
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for Student ID or Course ID");
            }
        });

        vBox.getChildren().addAll(enrollStudentIdField, enrollCourseIdField, enrollStudentButton);
        tab.setContent(vBox);
        return tab;
    }

    // Create a tab for showing courses enrolled by one student
    private Tab createShowCoursesByStudentTab() {
        Tab tab = new Tab("Show Courses by Student");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");

        Button showCoursesButton = new Button("Show Courses");
        showCoursesButton.setOnAction(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText().trim().trim());
                boolean dataToShow = registrationSystem.displayCoursesOfStudent(studentId);
                if (!dataToShow) {
                    showAlert("There is no students With this Id");
                }
                clearFields(studentIdField);
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for Student ID");
            }
        });

        vBox.getChildren().addAll(studentIdField, showCoursesButton);
        tab.setContent(vBox);
        return tab;
    }

    // Create a tab for showing students enrolled in one course
    private Tab createShowStudentsByCourseTab() {
        Tab tab = new Tab("Show Students by Course");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Course ID");

        Button showStudentsButton = new Button("Show Students");
        showStudentsButton.setOnAction(e -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText().trim().trim());
                boolean dataToShow = registrationSystem.displayStudentsOfCourse(courseId);
                if (!dataToShow) {
                    showAlert("There is no students With this Id");
                }
                clearFields(courseIdField);
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for Course ID");
            }
        });

        vBox.getChildren().addAll(courseIdField, showStudentsButton);
        tab.setContent(vBox);
        return tab;
    }

    // Create a tab for deleting a student
    private Tab createDeleteStudentTab() {
        Tab tab = new Tab("Delete Student");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");

        Button deleteStudentButton = new Button("Delete Student");
        deleteStudentButton.setOnAction(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText().trim());
                StudentNode deletedStudent = registrationSystem.deleteStudentById(studentId);
                if (deletedStudent != null) {
                    showAlert("Succefully", "Student: " + deletedStudent.getStudentName() + " Deleted Succeffully");
                } else {
                    showAlert("Error Occured while Deleteing this student");
                }
                clearFields(studentIdField);
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for Student ID");
            }
        });

        vBox.getChildren().addAll(studentIdField, deleteStudentButton);
        tab.setContent(vBox);
        return tab;
    }

    // Create a tab for deleting a course
    private Tab createDeleteCourseTab() {
        Tab tab = new Tab("Delete Course");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Course ID");

        Button deleteCourseButton = new Button("Delete Course");
        deleteCourseButton.setOnAction(e -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText().trim());
                CourseNode deletedCourse = registrationSystem.deleteCourseById(courseId);
                if (deletedCourse != null) {
                    showAlert("Success", "Course: " + deletedCourse.getCourseName() + " deleted successfully");
                } else {
                    showAlert("Error occured while deleting course");
                }
                clearFields(courseIdField);
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for Course ID");
            }
        });

        vBox.getChildren().addAll(courseIdField, deleteCourseButton);
        tab.setContent(vBox);
        return tab;
    }

    private Tab createRemoveStudentTab() {
        Tab tab = new Tab("Remove Student from Course");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        TextField removeStudentIdField = new TextField();
        removeStudentIdField.setPromptText("Student ID");
        TextField removeCourseIdField = new TextField();
        removeCourseIdField.setPromptText("Course ID");

        Button removeStudentButton = new Button("Remove Student from Course");
        removeStudentButton.setOnAction(e -> {
            try {
                int studentId = Integer.parseInt(removeStudentIdField.getText().trim());
                int courseId = Integer.parseInt(removeCourseIdField.getText().trim());
                boolean removed = registrationSystem.unrollStudent(studentId, courseId);
                if (removed) {
                    showAlert("Student removed from the course.");
                    clearFields(removeStudentIdField, removeCourseIdField);
                } else {
                    showAlert("Error occurred while removing student from the course.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid input");
            }
        });

        vBox.getChildren().addAll(removeStudentIdField, removeCourseIdField, removeStudentButton);
        tab.setContent(vBox);
        return tab;
    }

    // Show an alert with the given message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear the text fields
    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

}
