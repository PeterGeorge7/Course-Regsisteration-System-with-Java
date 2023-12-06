package classes.helpers;

import classes.Exceptions.IdExistsInListException;
import classes.Exceptions.IdNotFoundInListException;
import classes.Exceptions.InvalidInputDataException;

public class ExceptionsHandler {

    public static void handleStudentNotEnrolledError(int studentId, int courseId) {
        try {
            throw new IdNotFoundInListException(
                    "This Student Id: " + studentId + "isnt enrolled in this course: " + courseId);
        } catch (IdNotFoundInListException e) {
            System.out.println("This Student Id: " + studentId + " isnt enrolled in this course: " + courseId);
        }

    }

    public static void handleIdExists(String message) {
        try {
            throw new IdExistsInListException(message);
        } catch (IdExistsInListException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void handleIdNotFound(String message) {
        try {
            throw new IdNotFoundInListException(message);
        } catch (IdNotFoundInListException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles invalid input by throwing an InvalidInputData exception with the
     * provided message.
     *
     * @param message The message describing the reason for invalid input.
     */
    public static void handleInvalidInput(String message) {
        try {
            throw new InvalidInputDataException(message);
        } catch (InvalidInputDataException e) {
            System.err.println(e.getMessage());
        }
    }

}