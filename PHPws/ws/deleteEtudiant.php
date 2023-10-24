<?php
include_once '../racine.php';
include_once RACINE . '/service/EtudiantService.php';

// Check if the student ID is provided as a query parameter
if (isset($_GET['id'])) {
    $studentId = $_GET['id'];

    // Create an instance of the EtudiantService
    $etudiantService = new EtudiantService();

    $student = $etudiantService->findById($studentId);
    // Use the delete method to delete the student by ID
    $etudiantService->delete($student);

    // Return a success message or any appropriate response
    echo "Student with ID $studentId has been deleted successfully.";
} else {
    // Handle the case when the ID is not provided in the request
    echo "Student ID is missing in the request.";
}
